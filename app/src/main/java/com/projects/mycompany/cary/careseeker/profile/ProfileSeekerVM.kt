package com.projects.mycompany.cary.careseeker.profile


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import com.projects.mycompany.cary.utils.getCurrentUser

class ProfileSeekerVM(seekerDataRepository: SeekerDataRepository) : ViewModel() {

    val currentSeeker = seekerDataRepository.getLocalSeekerById(getCurrentUser()!!.uid)

    private val _jobDetailsClick = MutableLiveData<Boolean>()
    val jobDetailsClick: LiveData<Boolean>
        get() = _jobDetailsClick


    fun onJobDetailsClick(){
        _jobDetailsClick.value = true
    }

    fun onJobDetailsClicked(){
        _jobDetailsClick.value = false
    }
}