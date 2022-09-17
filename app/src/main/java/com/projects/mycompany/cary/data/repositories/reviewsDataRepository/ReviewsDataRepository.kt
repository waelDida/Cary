package com.projects.mycompany.cary.data.repositories.reviewsDataRepository

import com.google.firebase.firestore.DocumentSnapshot
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.models.Review

interface ReviewsDataRepository {
    suspend fun getReviews() : Resource<MutableList<Review>>

    suspend fun createNewReview(review: Review)
    suspend fun getReviewsFromFireStore(id: String): Resource<MutableList<Review>>
    suspend fun getReviewsFromFireStore(reviewedId: String, reviewerId: String): Pair<MutableList<Review>,String>
    suspend fun getCurrentSeekerReviews(id: String): List<DocumentSnapshot>
    suspend fun getCurrentGiverReviews(id: String): List<DocumentSnapshot>
    suspend fun<T> updateReview(id: String, field: String, value: T)
    suspend fun deleteReviews(id: String)

}