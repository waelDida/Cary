package com.projects.mycompany.cary.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.data.repositories.reviewsDataRepository.ReviewsDataRepository
import com.projects.mycompany.cary.models.Review
import com.projects.mycompany.cary.utils.getCurrentUser
import kotlinx.coroutines.Dispatchers


class GiverReviewsViewModel(id: String, repository: ReviewsDataRepository): ViewModel() {

    private lateinit var data: Resource<MutableList<Review>>

    val fetchData = liveData(Dispatchers.IO) {
        try {
            data = repository.getReviewsFromFireStore(id)
            emit(data)
        }
        catch (e: Exception){emit(Resource.Failure(e.cause!!))}
    }

}