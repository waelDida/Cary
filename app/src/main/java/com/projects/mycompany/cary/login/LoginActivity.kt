package com.projects.mycompany.cary.login

import android.Manifest
import android.content.Intent
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.utils.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class LoginActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var navController: NavController
    private lateinit var loginViewModel: LoginViewModel

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.elevation =0.0f
        title=""

        navController = findNavController(R.id.nav_host_fragment)

        NavigationUI.setupActionBarWithNavController(this,navController)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        if(Build.VERSION.SDK_INT >= 23)
            requestPermissions()
        else
            getLastLocation()

        displayMessageAfterUserCreation()
        displayMessageAfterAccountDelete()

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }

    private fun displayMessageAfterUserCreation(){
        val layout = findViewById<ConstraintLayout>(R.id.login_activity_layout)
        when(intent.getIntExtra(INDICE_AFTER_CREATION,0)){
            1 -> displayLongMessage(layout,getString(R.string.registration_message))
            2 -> displayMessage(layout,getString(R.string.failed_to_send_email))
            3 -> displayMessage(layout,getString(R.string.failed_to_sign_in))
        }
    }

    private fun displayMessageAfterAccountDelete(){
        val x = intent.getIntExtra(DELETE_ACCOUNT_TAG,0)
        val layout = findViewById<ConstraintLayout>(R.id.login_activity_layout)
        if(x == DELETE_ACCOUNT_VAL)
            Snackbar.make(layout,getString(R.string.account_deleted),Snackbar.LENGTH_SHORT).show()
    }

    // Get the last location
    private fun getLastLocation(){

        val builder = LocationSettingsRequest.Builder()
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            if(it.locationSettingsStates.isLocationUsable){
                try {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                        loginViewModel.setLocation(location?.latitude,location?.longitude)
                    }
                }
                catch (exception: SecurityException ){
                    Toast.makeText(this,"error $exception", Toast.LENGTH_SHORT).show()}
            }
            else
                showLocationNotEnabledDialog()
        }
    }

    private fun showLocationNotEnabledDialog(){
        AlertDialog.Builder(this)
            .setTitle("Activate location")
            .setMessage("The app will not work correctly unless you enable the location")
            .setPositiveButton("ok"
            ) { _, _ -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
            .setNegativeButton("Cancel"
            ) { dialog, _ -> dialog.dismiss() }
            .setCancelable(false)
            .create().show()
    }

    private fun requestPermissions(){
        if(hasLocationPermission(this))
            return
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.need_permission_text),
                MY_PERMISSIONS_REQUEST_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        else{
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.need_permission_text),
                MY_PERMISSIONS_REQUEST_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            AppSettingsDialog.Builder(this).build().show()
        }
        else
            requestPermissions()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
            getLastLocation()
    }

}
