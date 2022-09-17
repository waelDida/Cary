package com.projects.mycompany.cary.caregiver.deleteaccount

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.data.repositories.inboxDataRepository.InboxDataRepository
import com.projects.mycompany.cary.data.repositories.reviewsDataRepository.ReviewsDataRepository

@Suppress("UNCHECKED_CAST")
class DeleteGiverVMFactory(private val giverDataRepository: GiverDataRepository,
                           private val inboxDataRepository: InboxDataRepository,
                           private val reviewsDataRepository: ReviewsDataRepository,
                           private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DeleteGiverDataVM::class.java))
            return DeleteGiverDataVM(giverDataRepository,inboxDataRepository,reviewsDataRepository,application) as T
        throw IllegalArgumentException("Can't create the viewModel")
    }
}