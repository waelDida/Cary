package com.projects.mycompany.cary.careseeker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.api.getCareSeekerFromFireStore
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.utils.getCurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainSeekerActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_seeker)

        // Find navController and bottom navigation
        navController = findNavController(R.id.nav_host_fragment)
        bottomNav = findViewById(R.id.bottom_nav)

        // setup Bottom navigation menu
        bottomNav.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, nd: NavDestination, _ ->
            if (nd.id == R.id.giverDetails || nd.id == R.id.editSeekerProfile || nd.id == R.id.seekerFilter
                || nd.id == R.id.seekerSettings || nd.id == R.id.aboutGiverDetails
                || nd.id == R.id.giverReviews || nd.id == R.id.rateGiverFragment || nd.id == R.id.jobDetails
                || nd.id == R.id.seekerChat || nd.id == R.id.deleteCareSeekerData) {
                bottomNav.visibility = View.GONE
            } else {
                bottomNav.visibility = View.VISIBLE
            }
        }

        // setup action bar navigation
        supportActionBar!!.elevation = 0.0f
        val appBarConfiguration = AppBarConfiguration.Builder(
                R.id.searchSeeker,R.id.seekerInbox,R.id.profileSeeker).build()
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration)

        addBadge()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun addBadge() {
        GlobalScope.launch(Dispatchers.IO) {
            val currentCareSeekerSnapshot = getCareSeekerFromFireStore(getCurrentUser()!!.uid)
            val currentCareSeeker = currentCareSeekerSnapshot.toObject(CareSeeker::class.java)!!
            withContext(Dispatchers.Main){
                if(currentCareSeeker.notReadMessages.size != 0){
                    val badge: BadgeDrawable = bottomNav.getOrCreateBadge(
                        R.id.seekerInbox)
                    badge.number = currentCareSeeker.notReadMessages.size
                    badge.isVisible = true
                }
            }
        }

    }
}
