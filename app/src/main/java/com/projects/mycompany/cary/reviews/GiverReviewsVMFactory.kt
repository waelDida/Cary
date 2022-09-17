package com.projects.mycompany.cary.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.reviewsDataRepository.ReviewsDataRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class GiverReviewsVMFactory(private val id: String , private val reviewsDataRepository: ReviewsDataRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GiverReviewsViewModel::class.java))
            return GiverReviewsViewModel(id,reviewsDataRepository) as T
        throw IllegalArgumentException("Can't construct the viewModel")
    }
}