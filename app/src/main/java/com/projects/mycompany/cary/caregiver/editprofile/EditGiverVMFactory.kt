package com.projects.mycompany.cary.caregiver.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class EditGiverVMFactory(private val giverDataRepository: GiverDataRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EditGiverViewModel::class.java))
            return EditGiverViewModel(giverDataRepository) as T
        throw IllegalArgumentException("Can't create the viewModel")
    }
}