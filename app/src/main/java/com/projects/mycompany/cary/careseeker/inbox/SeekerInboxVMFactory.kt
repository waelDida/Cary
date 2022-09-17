package com.projects.mycompany.cary.careseeker.inbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class SeekerInboxVMFactory(private val giverRepo: GiverDataRepository, private val seekerRepo: SeekerDataRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SeekerInboxViewModel::class.java))
            return SeekerInboxViewModel(giverRepo,seekerRepo) as T
        throw IllegalArgumentException("Can't create the viewModel")
    }
}