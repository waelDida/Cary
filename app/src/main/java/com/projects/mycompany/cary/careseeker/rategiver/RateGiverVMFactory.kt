package com.projects.mycompany.cary.careseeker.rategiver

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.reviewsDataRepository.ReviewsDataRepository
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class RateGiverVMFactory(private val reviewedId: String, private val seekerRepository: SeekerDataRepository, private val reviewRepository: ReviewsDataRepository )
    : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RateGiverViewModel::class.java))
            return RateGiverViewModel(reviewedId,seekerRepository,reviewRepository) as T
        throw IllegalArgumentException("Can't create the viewModel")
    }
}