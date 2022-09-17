package com.projects.mycompany.cary.caregiver.seekerDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.data.repositories.inboxDataRepository.InboxDataRepository
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import com.projects.mycompany.cary.models.CareSeeker
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class SeekerDetailVMFactory(private val inboxRepo: InboxDataRepository,
                            private val giverRepo: GiverDataRepository,
                            val careSeeker: CareSeeker): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SeekerDetailVM::class.java))
            return SeekerDetailVM(inboxRepo,giverRepo,careSeeker) as T
        throw IllegalArgumentException("Can't create the viewModel")
    }
}