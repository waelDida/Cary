package com.projects.mycompany.cary.utils

import com.google.firebase.firestore.DocumentSnapshot
import com.projects.mycompany.cary.models.Review

fun convertToReviewsList(docs: List<DocumentSnapshot>): Pair<MutableList<Review>,String> {
    val reviewsList = mutableListOf<Review>()
    var index = ""
    docs.forEach {
        val review = it.toObject(Review::class.java)
        index = it.id
        reviewsList.add(review!!)
    }
    return Pair(reviewsList,index)
}