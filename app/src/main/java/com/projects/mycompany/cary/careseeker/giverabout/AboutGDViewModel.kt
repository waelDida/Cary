package com.projects.mycompany.cary.careseeker.giverabout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutGDViewModel(aboutCareGiver: String): ViewModel() {

    private val _aboutText = MutableLiveData<String>()
    val aboutText: LiveData<String>
        get() = _aboutText

    init {
        _aboutText.value = aboutCareGiver
    }
}