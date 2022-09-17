package com.projects.mycompany.cary.careseeker.deleteaccount

import android.app.Application
import androidx.lifecycle.*
import com.projects.mycompany.cary.data.repositories.inboxDataRepository.InboxDataRepository
import com.projects.mycompany.cary.data.repositories.reviewsDataRepository.ReviewsDataRepository
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import com.projects.mycompany.cary.preference.AppPreferences
import com.projects.mycompany.cary.utils.getCurrentUser
import com.projects.mycompany.cary.utils.getDetailedStorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeleteSeekerDataVM(private val seekerRepository: SeekerDataRepository,
                         private val inboxDataRepository: InboxDataRepository,
                         private val reviewsDataRepository: ReviewsDataRepository,
                         application: Application): AndroidViewModel(application) {

    private val appPref = AppPreferences(application)

    private val _deleteCompleted = MutableLiveData<Boolean>()
    val deleteCompleted : LiveData<Boolean>
        get() = _deleteCompleted

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val currentCareSeeker = seekerRepository.getSyncSeeker(getCurrentUser()!!.uid)
            deleteCurrentCareSeekerData()
            deleteChats()
            deleteReviews()
            withContext(Dispatchers.Main){
                appPref.initAppPreference()

                if(currentCareSeeker.imgUrl.isNotEmpty())
                    getDetailedStorageReference(currentCareSeeker.imgUrl).delete()

                getCurrentUser()!!.delete().addOnCompleteListener {
                    if(it.isSuccessful)
                        _deleteCompleted.value = true
                }
            }
        }
    }

    private suspend fun deleteCurrentCareSeekerData(){
        seekerRepository.deleteSeekerFromFireStore(getCurrentUser()!!.uid)
        seekerRepository.deleteSeekerFromRoom(getCurrentUser()!!.uid)
    }

    private suspend fun deleteChats(){
        val documentSnapshot = inboxDataRepository.getCurrentChats(getCurrentUser()!!.uid)
        documentSnapshot.forEach {
            inboxDataRepository.deleteCurrentChats(it.id)
        }
    }

    private suspend fun deleteReviews(){
        val documentSnapshot = reviewsDataRepository.getCurrentSeekerReviews(getCurrentUser()!!.uid)
        documentSnapshot.forEach {
            reviewsDataRepository.deleteReviews(it.id)
        }
    }
}