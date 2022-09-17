package com.projects.mycompany.cary.data.sources.reviewsDataSource


import com.google.firebase.firestore.DocumentSnapshot
import com.projects.mycompany.cary.api.*
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.models.Review
import com.projects.mycompany.cary.test.*
import com.projects.mycompany.cary.utils.convertToReviewsList

class ReviewsDataSourceImp:
    ReviewsDataSource {
    override suspend fun getAllReviews(): Resource<MutableList<Review>> {
        val list = mutableListOf(
            review1,
            review2,
            review3,
            review4,
            review5,
            review6,
            review7
        )
        //val list = Collections.emptyList<Review>()
        //val list: MutableList<Review>? = null
        return when {
            list.isEmpty() -> Resource.Empty(list)
            list.isNotEmpty() -> Resource.Success(list)
            else -> Resource.Failure(Throwable("Error Occurred"))
        }

    }

    override suspend fun createNewReview(review: Review) {
        createReview(review)
    }

    override suspend fun getReviews(id: String): Resource<MutableList<Review>> {
        val documentSnapshot = getReviewsFromFireStore(id)
        val reviewsList = convertToReviewsList(documentSnapshot).first
        return if(reviewsList.isEmpty()) Resource.Empty(reviewsList) else Resource.Success(reviewsList)
    }

    override suspend fun getReviews(
        reviewedId: String,
        reviewerId: String
    ): Pair<MutableList<Review>, String> {
        val documentSnapshot = getReviewsFromFireStore(reviewedId,reviewerId)
        return convertToReviewsList(documentSnapshot)
    }

    override suspend fun getCurrentSeekerReviews(id: String): List<DocumentSnapshot> {
        return getCurrentReviewsFromFireStore(id)
    }

    override suspend fun getCurrentGiverReviews(id: String): List<DocumentSnapshot> {
        return getReviewsFromFireStore(id)
    }

    override suspend fun<T> updateReview(id: String, field: String, value: T) {
        updateReviewInFireStore(id,field,value)
    }

    override suspend fun deleteReviews(id: String) {
        deleteReviewsFromFireStore(id)
    }
}