package com.projects.mycompany.cary.caregiver.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.models.CareGiver
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ProfileGiverVMFactory(private val giverDataRepository: GiverDataRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProfileGiverViewModel::class.java))
            return ProfileGiverViewModel(giverDataRepository) as T
        throw IllegalArgumentException("Can't create the viewModel")
    }
}