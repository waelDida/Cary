package com.projects.mycompany.cary.caregiver.inbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class GiverInboxVMFactory(private val seekerRepo: SeekerDataRepository, private val giverRepo: GiverDataRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GiverInboxViewModel::class.java))
            return GiverInboxViewModel(seekerRepo,giverRepo) as T
        throw IllegalArgumentException("Can't create the viewModel")
    }
}