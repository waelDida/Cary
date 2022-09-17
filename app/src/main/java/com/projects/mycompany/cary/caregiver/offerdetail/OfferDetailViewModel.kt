package com.projects.mycompany.cary.caregiver.offerdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projects.mycompany.cary.models.CareSeeker

class OfferDetailViewModel(val careSeeker: CareSeeker): ViewModel() {

    private val _mCareSeeker = MutableLiveData<CareSeeker>()
    val mCareSeeker: LiveData<CareSeeker>
        get() = _mCareSeeker

    private val _showProfile = MutableLiveData<CareSeeker?>()
    val showProfile: LiveData<CareSeeker?>
        get() = _showProfile

    init {
        _mCareSeeker.value = careSeeker
    }

    fun onShowProfileClick(){
        _showProfile.value = _mCareSeeker.value
    }

    fun onShowProfileClicked(){
        _showProfile.value = null
    }

}