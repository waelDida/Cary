package com.projects.mycompany.cary.api


import com.google.firebase.firestore.*
import com.projects.mycompany.cary.models.Chat
import com.projects.mycompany.cary.models.Message
import com.projects.mycompany.cary.utils.*
import kotlinx.coroutines.tasks.await
import java.util.*

private fun getCollectionReference(): CollectionReference{
    return FirebaseFirestore.getInstance().collection(CHATS)
}

suspend fun createChat(senderReceiverList: MutableList<String>){
     getCollectionReference().document().set(Chat(senderReceiverList)).await()
}

suspend fun createMessageForChat(id: String, message: Message): DocumentReference{
    return getCollectionReference().document(id).collection(MESSAGES).add(message).await()
}

suspend fun getChats(id: String): List<DocumentSnapshot>{
    return getCollectionReference().whereArrayContains(REMOTE_SENDER_RECEIVER_LIST,id).get().await().documents
}

fun getAllMessagesForChat(roomId: String): Query{
    return getCollectionReference()
        .document(roomId)
        .collection(MESSAGES)
        .orderBy(REMOTE_DATE_CREATION)
        .limit(50)
}

suspend fun deleteChat(roomId: String){
    getCollectionReference().document(roomId).delete().await()
}

