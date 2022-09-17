package com.projects.mycompany.cary.caregiver.deleteaccount

import android.app.Application
import androidx.lifecycle.*
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.data.repositories.inboxDataRepository.InboxDataRepository
import com.projects.mycompany.cary.data.repositories.reviewsDataRepository.ReviewsDataRepository
import com.projects.mycompany.cary.preference.AppPreferences
import com.projects.mycompany.cary.utils.getCurrentUser
import com.projects.mycompany.cary.utils.getDetailedStorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeleteGiverDataVM(private val giverDataRepository: GiverDataRepository,
                        private val inboxDataRepository: InboxDataRepository,
                        private val reviewsDataRepository: ReviewsDataRepository,
                        application: Application): AndroidViewModel(application) {

    private val appPref = AppPreferences(application)

    private val _deleteCompleted = MutableLiveData<Boolean>()
    val deleteCompleted : LiveData<Boolean>
        get() = _deleteCompleted

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val currentGiver = giverDataRepository.getSyncGiver(getCurrentUser()!!.uid)
            deleteCurrentGiverData()
            deleteChats()
            deleteReviews()
            withContext(Dispatchers.Main){
                appPref.initAppPreference()

                if(currentGiver.imgUrl.isNotEmpty())
                    getDetailedStorageReference(currentGiver.imgUrl).delete()

                getCurrentUser()!!.delete().addOnCompleteListener {
                    if(it.isSuccessful)
                        _deleteCompleted.value = true
                }
            }
        }

    }

    private suspend fun deleteCurrentGiverData(){
        giverDataRepository.deleteGiver(getCurrentUser()!!.uid)
        giverDataRepository.deleteGiverFromRoom(getCurrentUser()!!.uid)
    }

    private suspend fun deleteChats(){
        val idsList = mutableListOf<String>()
        val documentSnapshot = inboxDataRepository.getCurrentChats(getCurrentUser()!!.uid)
        documentSnapshot.forEach {
            idsList.add(it.id)
        }
        idsList.forEach {
            inboxDataRepository.deleteCurrentChats(it)
        }
    }

    private suspend fun deleteReviews(){
        val idsList = mutableListOf<String>()
        val documentSnapshot = reviewsDataRepository.getCurrentGiverReviews(getCurrentUser()!!.uid)
        documentSnapshot.forEach {
            idsList.add(it.id)
        }
        idsList.forEach {
            reviewsDataRepository.deleteReviews(it)
        }

    }
}