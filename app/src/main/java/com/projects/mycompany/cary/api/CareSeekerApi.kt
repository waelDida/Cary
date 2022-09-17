package com.projects.mycompany.cary.api


import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.models.SeekerFilter
import com.projects.mycompany.cary.utils.*
import kotlinx.coroutines.tasks.await



private fun getCollectionReference(): CollectionReference {
    return FirebaseFirestore.getInstance().collection(SEEKERS)
}

// Create
fun createSeeker(careSeeker: CareSeeker): Task<Void>{
    return getCollectionReference().document(careSeeker.uid).set(careSeeker)
}


// Get
suspend fun getCareSeekerFromFireStore(id: String): DocumentSnapshot{
    return  getCollectionReference().document(id).get().await()
}

suspend fun getCareSeekers(filter: SeekerFilter): List<DocumentSnapshot>{
    val documentSnapshot = getCollectionReference()
        .whereEqualTo(REMOTE_SEARCHING_STATUS, true)
        .whereEqualTo(REMOTE_EMAIL_VERIFIED,true)
        .whereEqualTo(REMOTE_CARE_CATEGORY,filter.careCategory)
        .get().await()
    return documentSnapshot.documents
}

suspend fun getCareSeekers(id: String): List<DocumentSnapshot>{
    val documentSnapshot = getCollectionReference()
        .whereArrayContains(REMOTE_CHAT_LIST,id).get().await()
    return documentSnapshot.documents
}

// Update

suspend fun updateCareSeekerChatList(id: String, value: String){
    getCollectionReference().document(id).update(REMOTE_CHAT_LIST,FieldValue.arrayUnion(value)).await()
}

suspend fun clearCareSeekerNoReadMessages(id: String, value: String){
    getCollectionReference().document(id).update(REMOTE_NOT_READ_MESSAGES,FieldValue.arrayRemove(value)).await()
}

fun updateCareSeekerMessageToken(id: String, value: String): Task<Void>{
    return getCollectionReference().document(id).update(REMOTE_TOKEN_LIST,FieldValue.arrayUnion(value))
}

fun updateCareSeekerPhotoInFireStore(id: String, url: String): Task<Void>{
    return getCollectionReference().document(id).update(REMOTE_IMG_URL,url)
}

suspend fun<T> updateCareSeekerInFireStore(id: String, field:String, value: T){
    getCollectionReference().document(id).update(field,value).await()
}

// Delete

suspend fun deleteCareSeekerFromFireStore(id: String){
    getCollectionReference().document(id).delete().await()
}