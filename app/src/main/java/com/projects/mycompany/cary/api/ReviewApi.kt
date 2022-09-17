package com.projects.mycompany.cary.api


import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.projects.mycompany.cary.models.Review
import com.projects.mycompany.cary.utils.REMOTE_REVIEWED_ID
import com.projects.mycompany.cary.utils.REMOTE_REVIEWER_ID
import com.projects.mycompany.cary.utils.REVIEWS
import kotlinx.coroutines.tasks.await

private fun getCollectionReference(): CollectionReference {
    return FirebaseFirestore.getInstance().collection(REVIEWS)
}

suspend fun createReview(review: Review){
    getCollectionReference().document().set(review).await()
}

suspend fun getReviewsFromFireStore(reviewedId: String, reviewerId: String): List<DocumentSnapshot>{
    return getCollectionReference()
        .whereEqualTo(REMOTE_REVIEWED_ID,reviewedId)
        .whereEqualTo(REMOTE_REVIEWER_ID, reviewerId).get().await().documents
}

suspend fun getReviewsFromFireStore(id: String): List<DocumentSnapshot>{
    val querySnapshot =  getCollectionReference().whereEqualTo(REMOTE_REVIEWED_ID,id).get().await()
    return querySnapshot.documents
}

suspend fun getCurrentReviewsFromFireStore(id: String): List<DocumentSnapshot>{
    val querySnapshot = getCollectionReference().whereEqualTo(REMOTE_REVIEWER_ID,id).get().await()
    return querySnapshot.documents
}

suspend fun<T> updateReviewInFireStore(id: String, field: String, value: T){
    getCollectionReference().document(id).update(field,value).await()
}

suspend fun deleteReviewsFromFireStore(id: String){
    getCollectionReference().document(id).delete().await()
}