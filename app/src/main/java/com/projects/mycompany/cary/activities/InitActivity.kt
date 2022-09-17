package com.projects.mycompany.cary.activities


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.IntentSender
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.api.*
import com.projects.mycompany.cary.caregiver.MainGiverActivity
import com.projects.mycompany.cary.careseeker.MainSeekerActivity
import com.projects.mycompany.cary.databinding.ActivityInitBinding
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.preference.AppPreferences
import com.projects.mycompany.cary.room.AppDataBase
import com.projects.mycompany.cary.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class InitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInitBinding
    private val mFireBase = FirebaseFirestore.getInstance()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lastLocation : Location? = null
    private var locationRequest: LocationRequest? = null
    private lateinit var locationCallback: LocationCallback
    private var mLatitude = 0.0
    private var mLongitude = 0.0
    private val currentUserId = getCurrentUser()!!.uid
    private lateinit var pref: AppPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_init)

        pref = AppPreferences(application)

        // Get the NotificationManager
        val notificationManager = ContextCompat
            .getSystemService(application,NotificationManager::class.java) as NotificationManager

        // Create a channel for messages
        /*createNotificationChannel(
            getString(R.string.msg_notification_channel_id),
            getString(R.string.msg_notification_channel_name),
            notificationManager)*/

        // Create a channel for fcm
        createNotificationChannel(
            getString(R.string.fcm_notification_channel_id),
            getString(R.string.fcm_notification_channel_name),
            notificationManager
        )


        // Get the  fusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Location request
        createLocationRequest()

        // Listen to the location update
        locationCallback = object: LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for(location in locationResult.locations){
                    if(location.latitude != 0.0 && location.longitude != 0.0){
                        mLatitude = location.latitude
                        mLongitude = location.longitude
                    }
                    else{
                        mLatitude = lastLocation!!.latitude
                        mLongitude = lastLocation!!.longitude
                    }
                }
                if(isNetworkAvailable(this@InitActivity))
                    init(mLatitude,mLongitude)
                else
                    startActivity(Intent(this@InitActivity,NoInternetActivity::class.java))
            }
        }
    }

    private fun init(latitude: Double, longitude: Double){
        when {
            pref.getCurrentUserType() == isCareGiver -> {
                GlobalScope.launch(Dispatchers.IO) {
                    updateCareGiverInFireStore(currentUserId, REMOTE_LATITUDE, latitude)
                    updateCareGiverInFireStore(currentUserId, REMOTE_LONGITUDE, longitude)
                    insertCareGiverLocally()
                    withContext(Dispatchers.Main){
                        startActivity(Intent(this@InitActivity,MainGiverActivity::class.java))
                        finish()
                    }
                }
            }
            pref.getCurrentUserType() == isCareSeeker -> {
                GlobalScope.launch(Dispatchers.IO) {
                    updateCareSeekerInFireStore(currentUserId, REMOTE_LATITUDE, latitude)
                    updateCareSeekerInFireStore(currentUserId, REMOTE_LONGITUDE, longitude)
                    insertCareSeekerLocally()
                    withContext(Dispatchers.Main){
                        startActivity(Intent(this@InitActivity,MainSeekerActivity::class.java))
                        finish()
                    }
                }

            }
            else -> {
                mFireBase.collection(GIVERS).document(currentUserId)
                    .get().addOnCompleteListener{ task1->
                        if(task1.result!!.exists()){
                            pref.setCurrentUser(isCareGiver)
                            updateMessageToken(isCareGiver)
                            GlobalScope.launch(Dispatchers.IO) {
                                if(!pref.isEmailVerified()){
                                    updateCareGiverInFireStore(currentUserId, REMOTE_EMAIL_VERIFIED,true)
                                    pref.setEmailVerifiedToTrue()
                                }
                                updateCareGiverInFireStore(currentUserId, REMOTE_LATITUDE, latitude)
                                updateCareGiverInFireStore(currentUserId, REMOTE_LONGITUDE, longitude)
                                insertCareGiverLocally()
                                withContext(Dispatchers.Main){
                                    val intent = Intent(this@InitActivity,MainGiverActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        } else {
                            mFireBase.collection(SEEKERS).document(currentUserId)
                                .get().addOnCompleteListener {task2 ->
                                    if(task2.result!!.exists()){
                                        pref.setCurrentUser(isCareSeeker)
                                        updateMessageToken(isCareSeeker)
                                        GlobalScope.launch(Dispatchers.IO) {
                                            if(!pref.isEmailVerified()){
                                                updateCareSeekerInFireStore(currentUserId, REMOTE_EMAIL_VERIFIED, true)
                                                pref.setEmailVerifiedToTrue()
                                            }
                                            updateCareSeekerInFireStore(currentUserId, REMOTE_LATITUDE, latitude)
                                            updateCareSeekerInFireStore(currentUserId, REMOTE_LONGITUDE, longitude)
                                            insertCareSeekerLocally()
                                            withContext(Dispatchers.Main){
                                                startActivity(Intent(this@InitActivity,MainSeekerActivity::class.java))
                                                finish()
                                            }
                                        }

                                    }
                                }
                        }
                    }.addOnFailureListener {e->
                        Toast.makeText(this@InitActivity,e.localizedMessage,Toast.LENGTH_SHORT).show()
                    }
            }
        }

    }

    private suspend fun insertCareGiverLocally(){
        val currentCareGiverSnapshot = getCurrentGiverFromFireStore(currentUserId)
        val currentCareGiver = currentCareGiverSnapshot!!.toObject(CareGiver::class.java)!!
        AppDataBase.getInstance(application).giverDao.insert(currentCareGiver)
    }

    private suspend fun insertCareSeekerLocally(){
        val currentCareSeekerSnapShot = getCareSeekerFromFireStore(currentUserId)
        val currentCareSeeker = currentCareSeekerSnapShot.toObject(CareSeeker::class.java)!!
        AppDataBase.getInstance(application).seekerDao.insert(currentCareSeeker)
    }

    override fun onStart() {
        super.onStart()
        getLastLocation()
    }

    override fun onResume() {
        super.onResume()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            startLocationUpdates()
        }
        task.addOnFailureListener {exception ->
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(this@InitActivity,
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CHECK_SETTINGS){
            if(resultCode == RESULT_OK)
                startLocationUpdates()
        }
    }

    override fun onStop() {
        super.onStop()
        stopLocationUpdates()
    }


    // Get the last location
    private fun getLastLocation(){
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                lastLocation = location
            }
        }
        catch (exception: SecurityException ){
            Toast.makeText(this,"error $exception", Toast.LENGTH_SHORT).show()}
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private fun startLocationUpdates() {
        try{
            fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper())
        } catch(exception: SecurityException){Toast.makeText(this,"error $exception", Toast.LENGTH_SHORT).show()}
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    // Create a location channel
    private fun createNotificationChannel(channelId: String, channelName: String,notificationManager: NotificationManager){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    // Update CareGiver Message token
    private fun updateMessageToken(userType: String){
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            if(!it.isSuccessful){
                Toast.makeText(this,"can't get the token",Toast.LENGTH_SHORT).show()
            }
            else{
                val token = it.result?.token
                if(userType == isCareGiver)
                    updateCareGiverMessageToken(getCurrentUser()!!.uid,token!!)
                else if(userType == isCareSeeker)
                    updateCareSeekerMessageToken(getCurrentUser()!!.uid,token!!)
            }
        }
    }
}
