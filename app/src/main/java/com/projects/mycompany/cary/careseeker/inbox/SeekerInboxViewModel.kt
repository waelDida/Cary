package com.projects.mycompany.cary.careseeker.inbox

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

class SeekerInboxViewModel(private val giverRepo: GiverDataRepository, private val seekerRepo: SeekerDataRepository):ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val currentSeeker = seekerRepo.getRemoteSeekerById(getCurrentUser()!!.uid)
            currentSeeker.notReadMessages.forEach {
                seekerRepo.clearNoReadMessagesList(getCurrentUser()!!.uid,it)
            }
        }
    }

    val fetchData = liveData(Dispatchers.IO) {
        try{
            val data = giverRepo.getGivers(getCurrentUser()!!.uid)
            emit(data)
        } catch(e: Exception){emit(Resource.Failure(e.cause!!))}

    }
}