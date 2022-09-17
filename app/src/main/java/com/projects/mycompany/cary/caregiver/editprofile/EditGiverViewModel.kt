package com.projects.mycompany.cary.caregiver.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.utils.getCurrentUser

class EditGiverViewModel(val repository: GiverDataRepository): ViewModel() {

    val careGiver = repository.getLocalGiverById(getCurrentUser()!!.uid)

    private val _uploadImage = MutableLiveData<Boolean>()
    val uploadImage: LiveData<Boolean>
        get() = _uploadImage

    private val _basicInfo = MutableLiveData<Boolean>()
    val basicInfo: LiveData<Boolean>
        get() = _basicInfo

    private val _contactInfo = MutableLiveData<Boolean>()
    val contactInfo: LiveData<Boolean>
        get() = _contactInfo

    private val _job = MutableLiveData<Boolean>()
    val job: LiveData<Boolean>
        get() = _job

    private val _about = MutableLiveData<Boolean>()
    val about: LiveData<Boolean>
        get() = _about


    fun uploadImageClick(){
        _uploadImage.value = true
    }

    fun uploadImageClicked(){
        _uploadImage.value = false
    }

    fun basicInfoClick(){
        _basicInfo.value = true
    }

    fun basicInfoClicked(){
        _basicInfo.value = false
    }

    fun contactInfoClick(){
        _contactInfo.value = true
    }

    fun contactInfoClicked(){
        _contactInfo.value = false
    }

    fun jobClick(){
        _job.value = true
    }

    fun jobClicked(){
        _job.value = false
    }

    fun aboutClick(){
        _about.value = true
    }

    fun aboutClicked(){
        _about.value = false
    }

    fun isImgUrlNotEmpty() = careGiver.value!!.imgUrl.isNotEmpty()

    fun getImgUrl() = careGiver.value!!.imgUrl



}