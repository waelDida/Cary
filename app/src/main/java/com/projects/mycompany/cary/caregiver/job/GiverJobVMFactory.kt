package com.projects.mycompany.cary.caregiver.job

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class GiverJobVMFactory(private val repository: GiverDataRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GiverJobViewModel::class.java))
            return GiverJobViewModel(repository) as T
        throw IllegalArgumentException("Can't create the viewModel")
    }
}