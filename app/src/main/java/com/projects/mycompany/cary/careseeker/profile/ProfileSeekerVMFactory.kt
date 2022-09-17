package com.projects.mycompany.cary.careseeker.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ProfileSeekerVMFactory(private val seekerDataRepository: SeekerDataRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProfileSeekerVM::class.java))
            return ProfileSeekerVM(seekerDataRepository) as T
        throw IllegalArgumentException("Can't create the viewModel")
    }
}