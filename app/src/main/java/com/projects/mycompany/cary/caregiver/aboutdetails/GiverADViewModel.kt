package com.projects.mycompany.cary.caregiver.aboutdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GiverADViewModel(about: String): ViewModel() {

    private val _aboutText = MutableLiveData<String>()
    val aboutText: LiveData<String>
        get() = _aboutText

    init {
        _aboutText.value = about
    }
}