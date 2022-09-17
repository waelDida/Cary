package com.projects.mycompany.cary.data.sources.reviewsDataSource

import com.google.firebase.firestore.DocumentSnapshot
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.models.Review

interface ReviewsDataSource {
    suspend fun getAllReviews(): Resource<MutableList<Review>>

    suspend fun createNewReview(review: Review)
    suspend fun getReviews(id: String): Resource<MutableList<Review>>
    suspend fun getReviews(reviewedId: String, reviewerId: String): Pair<MutableList<Review>,String>
    suspend fun getCurrentSeekerReviews(id: String): List<DocumentSnapshot>
    suspend fun getCurrentGiverReviews(id: String): List<DocumentSnapshot>
    suspend fun<T> updateReview(id: String, field: String, value: T)
    suspend fun deleteReviews(id: String)

}