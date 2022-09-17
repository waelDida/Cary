package com.projects.mycompany.cary.careseeker.giverdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.data.repositories.inboxDataRepository.InboxDataRepository
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import com.projects.mycompany.cary.models.CareGiver
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class DetailsVMFactory(private val inboxRepo: InboxDataRepository,
                       private val seekerRepo: SeekerDataRepository,
                       private val careGiver: CareGiver): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetailsViewModel::class.java))
            return DetailsViewModel(inboxRepo, seekerRepo,careGiver) as T
        throw IllegalArgumentException("Unable to construct the viewModel")
    }
}