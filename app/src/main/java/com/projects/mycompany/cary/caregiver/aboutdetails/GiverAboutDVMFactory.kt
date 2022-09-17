package com.projects.mycompany.cary.caregiver.aboutdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class GiverAboutDVMFactory(val about: String):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GiverADViewModel::class.java))
            return GiverADViewModel(about) as T
        throw IllegalArgumentException("Can't create the viewModel")
    }
}