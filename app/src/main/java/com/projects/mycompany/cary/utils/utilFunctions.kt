package com.projects.mycompany.cary.utils

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.api.updateCareGiverPhotoInFireStore
import com.projects.mycompany.cary.api.updateCareSeekerPhotoInFireStore
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.room.AppDataBase
import de.hdodenhof.circleimageview.CircleImageView
import de.psdev.licensesdialog.LicensesDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.EasyPermissions
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

fun isValidEmail(email: String): Boolean{
    return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun showDatePickerDialog(context : Context, view: TextView){
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val datePickerDialog = DatePickerDialog(
        context,
        { _, y, m, dayOfMonth ->
            val date = String.format("%02d", m + 1) + "/" + String.format("%02d", dayOfMonth) + "/" + y
            view.text = date
        },year,month,day)
    datePickerDialog.show()

}

fun calculateAge(date: Date): Int{
    val birth = Calendar.getInstance()
    birth.time = date
    val toDay = Calendar.getInstance()
    var yearsDiff = toDay.get(Calendar.YEAR) - birth.get(Calendar.YEAR)
    if(toDay.get(Calendar.MONTH) < birth.get(Calendar.MONTH)){
        yearsDiff--
    } else{
        if (toDay.get(Calendar.MONTH) == birth.get(Calendar.MONTH)
            && toDay.get(Calendar.DAY_OF_MONTH) < birth
                .get(Calendar.DAY_OF_MONTH)) {
            yearsDiff--
        }
    }
    return yearsDiff
}

fun getDateFromString(stringDate: String):Date{
    val df = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return df.parse(stringDate)!!
}

fun displayMessage(view: View, message: String){
    Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show()
}

fun displayLongMessage(view: View, message: String){
    Snackbar.make(view,message,Snackbar.LENGTH_LONG).show()
}

fun showLicences(context: Context){
    LicensesDialog.Builder(context)
        .setNotices(R.raw.notices)
        .build()
        .show()
}


 fun uploadPhoto(activity: FragmentActivity?, imageUri: Uri?, currentUserType: String, imgView: CircleImageView, progress: ProgressBar,
 currentCareGiver: CareGiver ?, currentCareSeeker: CareSeeker ?){

     progress.visibility = View.VISIBLE

    val uuid = UUID.randomUUID().toString()
    val uid = FirebaseAuth.getInstance().currentUser!!.uid
    val imgRef = FirebaseStorage.getInstance().reference.child("$uid/$uuid")
    val bmp = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, imageUri)
    val baos = ByteArrayOutputStream()
    bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos)
    val data = baos.toByteArray()
    imgRef.putBytes(data).continueWithTask { task ->
        if (!task.isSuccessful) {
            task.exception?.let {
                throw it
            }
        }
        imgRef.downloadUrl
    }.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val downloadUri = task.result
            if(currentUserType == isCareGiver){
                updateCareGiverPhotoInFireStore(getCurrentUser()!!.uid,downloadUri.toString())

                // update careGiver photo locally
                GlobalScope.launch(Dispatchers.IO) {
                    currentCareGiver!!.imgUrl = downloadUri.toString()
                    AppDataBase.getInstance(activity).giverDao.update(currentCareGiver)
                }
            }
            else if(currentUserType == isCareSeeker){
                updateCareSeekerPhotoInFireStore(getCurrentUser()!!.uid,downloadUri.toString())

                //update careSeeker photo Locally
                GlobalScope.launch(Dispatchers.IO) {
                    currentCareSeeker!!.imgUrl = downloadUri.toString()
                    AppDataBase.getInstance(activity).seekerDao.update(currentCareSeeker)
                }
            }
            progress.visibility = View.INVISIBLE
            Glide.with(imgView.context).load(downloadUri).into(imgView)
        } else {
            // Handle failures
            // ...
            Toast.makeText(activity,"upload failed", Toast.LENGTH_LONG).show()
        }
    }
}

fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double{
    val latitude1 = Math.toRadians(lat1)
    val longitude1 = Math.toRadians(lon1)
    val latitude2 = Math.toRadians(lat2)
    val longitude2 = Math.toRadians(lon2)
    val earthRadius = 6371.01 //Kilometers
    return earthRadius * acos(
        sin(latitude1) * sin(latitude2)
            + cos(latitude1) * cos(latitude2) * cos(longitude1 - longitude2)
    )
}

@ExperimentalStdlibApi
fun formatName(firstName: String, lastName: String) = firstName.capitalize(Locale.getDefault())+"."+lastName.capitalize(Locale.getDefault()).first()

fun formatJobType(jobType: String) = if(jobType == FULL_TIME) "Full Time" else "Part Time"



fun timeAgo(s: Date): String{
    val timeAgoStr: String
    var timeMillis  = s.time
    if(timeMillis < 1000000000000L)
        timeMillis *= 1000

    val now = System.currentTimeMillis()
    val diff = now - timeMillis
    timeAgoStr = when {
        diff < MINUTE_MILLIS -> "just now"
        diff < 2 * MINUTE_MILLIS -> "a minute ago"
        diff < 50 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} minutes ago"
        diff < 90 * MINUTE_MILLIS -> "an hour ago"
        diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS} hours ago"
        diff < 48 * HOUR_MILLIS -> "yesterday"
        else -> return "${diff / DAY_MILLIS} days ago"
    }
    return timeAgoStr
}

fun isPasswordValid(password: String): Boolean{
    val rule  = "^[^\\s]{8,12}"
    val pattern = Pattern.compile(rule)
    val matcher = pattern.matcher(password)
    return matcher.matches()
}

fun isNetworkAvailable(activity: Activity): Boolean{
    val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo!=null && networkInfo.isConnected
}

fun hasLocationPermission(context: Context) =
    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
        EasyPermissions.hasPermissions(context,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)

    }else{
        EasyPermissions.hasPermissions(context,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    }










