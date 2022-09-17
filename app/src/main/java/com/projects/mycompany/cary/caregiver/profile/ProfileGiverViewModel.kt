package com.projects.mycompany.cary.caregiver.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.utils.getCurrentUser

class ProfileGiverViewModel(giverDataRepository: GiverDataRepository): ViewModel() {

    val mCareGiver = giverDataRepository.getLocalGiverById(getCurrentUser()!!.uid)

    private val _aboutClick = MutableLiveData<String?>()
    val aboutClick: LiveData<String?>
        get() = _aboutClick

    private val _rateAndReviewsClick = MutableLiveData<Boolean>()
    val rateAndReviewClick: LiveData<Boolean>
        get() = _rateAndReviewsClick

    private val _plusFeature = MutableLiveData<Boolean>()
    val plusFeature: LiveData<Boolean>
        get() = _plusFeature


    fun onAboutClick(){
        _aboutClick.value = mCareGiver.value!!.about
    }

    fun onAboutClicked(){
        _aboutClick.value = null
    }

    fun onRateAndReviewsClick(){
        _rateAndReviewsClick.value = true
    }

    fun onRateAndReviewsClicked(){
        _rateAndReviewsClick.value = false
    }

    fun onPlusFeatureClick(){
        _plusFeature.value = true
    }

    fun onPlusFeatureClicked(){
        _plusFeature.value = false
    }

}