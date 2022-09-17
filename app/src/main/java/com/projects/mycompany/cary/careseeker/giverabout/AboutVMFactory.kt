package com.projects.mycompany.cary.careseeker.giverabout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class AboutVMFactory(private val aboutCareGiver: String): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AboutGDViewModel::class.java))
            return AboutGDViewModel(aboutCareGiver) as T
        throw IllegalArgumentException("Enable to construct the viewModel")
    }
}