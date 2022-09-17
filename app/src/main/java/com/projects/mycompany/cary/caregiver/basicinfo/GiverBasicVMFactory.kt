package com.projects.mycompany.cary.caregiver.basicinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class GiverBasicVMFactory(val repository: GiverDataRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GiverBasicViewModel::class.java))
            return GiverBasicViewModel(repository) as T
        throw IllegalArgumentException("Can't create the viwModel")
    }
}