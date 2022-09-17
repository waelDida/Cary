package com.projects.mycompany.cary.careseeker.filter

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class SeekerFVMFactory(private val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SeekerFilterViewModel::class.java))
            return SeekerFilterViewModel(app) as T
        throw IllegalArgumentException("Cant create the viewModel")
    }
}