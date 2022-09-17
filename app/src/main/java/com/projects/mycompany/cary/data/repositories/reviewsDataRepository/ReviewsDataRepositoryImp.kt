package com.projects.mycompany.cary.data.repositories.reviewsDataRepository

import com.google.firebase.firestore.DocumentSnapshot
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.data.sources.reviewsDataSource.ReviewsDataSource
import com.projects.mycompany.cary.models.Review

class ReviewsDataRepositoryImp(private val reviewsDataSource: ReviewsDataSource):
    ReviewsDataRepository {
    override suspend fun getReviews(): Resource<MutableList<Review>> {
       return reviewsDataSource.getAllReviews()
    }

    override suspend fun createNewReview(review: Review) {
        reviewsDataSource.createNewReview(review)
    }

    override suspend fun getReviewsFromFireStore(id: String): Resource<MutableList<Review>> {
        return reviewsDataSource.getReviews(id)
    }

    override suspend fun getReviewsFromFireStore(
        reviewedId: String,
        reviewerId: String
    ): Pair<MutableList<Review>, String> {
        return reviewsDataSource.getReviews(reviewedId,reviewerId)
    }

    override suspend fun getCurrentSeekerReviews(id: String): List<DocumentSnapshot> {
        return reviewsDataSource.getCurrentSeekerReviews(id)
    }

    override suspend fun getCurrentGiverReviews(id: String): List<DocumentSnapshot> {
        return reviewsDataSource.getCurrentGiverReviews(id)
    }

    override suspend fun<T> updateReview(id: String, field: String, value: T) {
        reviewsDataSource.updateReview(id,field,value)
    }

    override suspend fun deleteReviews(id: String) {
        reviewsDataSource.deleteReviews(id)
    }
}