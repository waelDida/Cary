package com.projects.mycompany.cary.caregiver


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
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.api.getCurrentGiverFromFireStore
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.Review
import com.projects.mycompany.cary.utils.REMOTE_RATE
import com.projects.mycompany.cary.utils.getCurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainGiverActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private var sum = 0
    private var rate = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_giver)
        supportActionBar?.elevation =0.0f

        updateRate()

        navController = findNavController(R.id.nav_host_fragment)
        bottomNavigationView = findViewById(R.id.bottom_nav)


        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{_, nd : NavDestination,_ ->
            if(nd.id == R.id.offerDetail || nd.id == R.id.settings_item || nd.id == R.id.editGiverProfile
                || nd.id == R.id.aboutFragment || nd.id == R.id.giverBasicInfo || nd.id == R.id.giverJob
                || nd.id == R.id.giverContactInfo || nd.id == R.id.giverFilter || nd.id == R.id.giverAboutDetails
                || nd.id == R.id.currentReviews || nd.id == R.id.seekerDetail || nd.id == R.id.giverChat
                || nd.id == R.id.deleteCareGiverData )
                bottomNavigationView.visibility = View.GONE
            else
                bottomNavigationView.visibility = View.VISIBLE
        }

        val appBarConfiguration = AppBarConfiguration.Builder(
                R.id.searchGiver,R.id.giverInboxFragment,R.id.profileGiver).build()
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration)

        addBadge()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    private fun addBadge() {
        GlobalScope.launch(Dispatchers.IO) {
            val currentCareGiverSnapshot = getCurrentGiverFromFireStore(getCurrentUser()!!.uid)
            val currentCareGiver = currentCareGiverSnapshot!!.toObject(CareGiver::class.java)!!
            withContext(Dispatchers.Main){
                if(currentCareGiver.notReadMessages.size != 0){
                    val badge: BadgeDrawable = bottomNavigationView.getOrCreateBadge(
                        R.id.giverInboxFragment)
                    badge.number = currentCareGiver.notReadMessages.size
                    badge.isVisible = true
                }

            }
        }
    }

    private fun updateRate(){
        val toDoApp = (application as ToDoApplication)
        val reviewsDataRepository = toDoApp.reviewsDataRepository
        val giverDataRepository = toDoApp.giverDataRepository
        GlobalScope.launch(Dispatchers.IO) {
            val documentSnapshot = reviewsDataRepository.getCurrentGiverReviews(getCurrentUser()!!.uid)
            documentSnapshot.forEach {
               val review = it.toObject(Review::class.java)
                sum += review!!.reviewerRate
            }

            val currentGiver = giverDataRepository.getSyncGiver(getCurrentUser()!!.uid)
            if(sum != 0)
                rate = sum.div(documentSnapshot.size.toDouble())

            giverDataRepository.updateGiverInFireStore(getCurrentUser()!!.uid, REMOTE_RATE,rate)

            currentGiver.rating = rate
            giverDataRepository.updateGiverInRoom(currentGiver)

            }
        }
}
