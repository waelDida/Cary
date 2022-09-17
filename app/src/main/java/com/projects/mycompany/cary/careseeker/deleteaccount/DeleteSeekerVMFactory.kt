package com.projects.mycompany.cary.careseeker.deleteaccount

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.inboxDataRepository.InboxDataRepository
import com.projects.mycompany.cary.data.repositories.reviewsDataRepository.ReviewsDataRepository
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class DeleteSeekerVMFactory(private val seekerDataRepository: SeekerDataRepository,
                            private val inboxDataRepository: InboxDataRepository,
                            private val reviewsDataRepository: ReviewsDataRepository,
                            private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DeleteSeekerDataVM::class.java))
            return DeleteSeekerDataVM(seekerDataRepository,inboxDataRepository,reviewsDataRepository,application) as T
        throw IllegalArgumentException("Can't create the viewModel")
    }
}