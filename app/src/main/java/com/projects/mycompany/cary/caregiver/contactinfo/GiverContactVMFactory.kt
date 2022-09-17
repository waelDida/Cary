package com.projects.mycompany.cary.caregiver.contactinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class GiverContactVMFactory(private val repository: GiverDataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GiverContactViewModel::class.java))
            return GiverContactViewModel(repository) as T
        throw IllegalArgumentException("Can't create the viewModel")
    }
}