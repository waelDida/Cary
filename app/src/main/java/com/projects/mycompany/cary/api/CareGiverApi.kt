package com.projects.mycompany.cary.api

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.GiverFilter
import com.projects.mycompany.cary.utils.*
import kotlinx.coroutines.tasks.await
import java.lang.Exception


private fun getCollectionReference(): CollectionReference{
    return FirebaseFirestore.getInstance().collection(GIVERS)
}

fun createGiver(careGiver: CareGiver): Task<Void>{
    return getCollectionReference().document(careGiver.uid).set(careGiver)
}

fun updateCareGiverPhotoInFireStore(id: String, url: String): Task<Void>{
    return getCollectionReference().document(id).update(REMOTE_IMG_URL,url)
}
// Get Api
suspend fun getCurrentGiverFromFireStore(id: String): DocumentSnapshot?{
    return try{
        getCollectionReference().document(id).get().await()
    }catch (e: Exception){null}
}

suspend fun getGiversFromFireStore(): List<DocumentSnapshot> {
    val documentSnapshot = getCollectionReference()
        .whereEqualTo(REMOTE_AVAILABILITY,YES)
        .whereEqualTo(REMOTE_EMAIL_VERIFIED,true)
        .get().await()
    return documentSnapshot.documents
}

suspend fun getGiversFromFireStore(filter: GiverFilter): List<DocumentSnapshot>{
    val documentSnapshot =  getCollectionReference()
        .whereEqualTo(REMOTE_AVAILABILITY,YES)
        .whereEqualTo(REMOTE_EMAIL_VERIFIED,true)
        .whereEqualTo(REMOTE_GENDER,filter.gender)
        .whereEqualTo(REMOTE_EXP,filter.experience)
        .whereEqualTo(REMOTE_JOB,filter.jobType).get().await()
    return documentSnapshot.documents
}

suspend fun getGiversFromFireStore(field1: String, field2: String, value1: String, value2: String): List<DocumentSnapshot>{
    val documentSnapshot = getCollectionReference()
        .whereEqualTo(REMOTE_AVAILABILITY,YES)
        .whereEqualTo(REMOTE_EMAIL_VERIFIED,true)
        .whereEqualTo(field1, value1)
        .whereEqualTo(field2,value2).get().await()
    return documentSnapshot.documents
}

suspend fun getGiversFromFireStore(field: String, value: String): List<DocumentSnapshot>{
    val documentSnapshot =  getCollectionReference()
        .whereEqualTo(REMOTE_AVAILABILITY,YES)
        .whereEqualTo(REMOTE_EMAIL_VERIFIED,true)
        .whereEqualTo(field, value).get().await()
    return documentSnapshot.documents
}

suspend fun getGiversFromFireStore(id: String): List<DocumentSnapshot>{
    val documentSnapshot = getCollectionReference()
        .whereArrayContains(REMOTE_CHAT_LIST,id).get().await()
    return documentSnapshot.documents
}

suspend fun updateCareGiverChatList(id: String, value: String){
    getCollectionReference().document(id).update(REMOTE_CHAT_LIST,FieldValue.arrayUnion(value)).await()
}

fun updateCareGiverMessageToken(id: String, value: String): Task<Void>{
    return getCollectionReference().document(id).update(REMOTE_TOKEN_LIST,FieldValue.arrayUnion(value))
}

suspend fun clearCareGiverNoReadMessages(id: String, value: String){
    getCollectionReference().document(id).update(REMOTE_NOT_READ_MESSAGES,FieldValue.arrayRemove(value)).await()
}

// Update Api
suspend fun<T> updateCareGiverInFireStore(id: String, field: String, value: T){
    getCollectionReference().document(id).update(field,value).await()
}

// Delete Api
suspend fun deleteGiverFromFireStore(id: String){
    getCollectionReference().document(id).delete().await()
}