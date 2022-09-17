package com.projects.mycompany.cary.caregiver.filter

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class GiverFilterVMFactory(val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GiverFilterViewModel::class.java))
            return GiverFilterViewModel(app) as T
        throw IllegalArgumentException("Can't create the viewModel")
    }
}