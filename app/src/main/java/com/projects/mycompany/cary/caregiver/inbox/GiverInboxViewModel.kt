package com.projects.mycompany.cary.caregiver.inbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.utils.getCurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class GiverInboxViewModel(private val seekerRepo: SeekerDataRepository, private val giverRepo: GiverDataRepository): ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val currentGiver = giverRepo.getRemoteGiverById(getCurrentUser()!!.uid)
            currentGiver.notReadMessages.forEach {
                giverRepo.clearNoReadMessages(getCurrentUser()!!.uid,it)
            }
        }
    }

    val fetchData = liveData(Dispatchers.IO) {
        try{
            val data = seekerRepo.getSeekers(getCurrentUser()!!.uid)
            emit(data)
        }catch (e: Exception){emit(Resource.Failure(e.cause!!))}
    }
}