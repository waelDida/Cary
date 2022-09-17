package com.projects.mycompany.cary.data.sources.inboxDataSource

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.projects.mycompany.cary.models.Message


interface InboxDataSource {

    suspend fun createNewChat(senderReceiverList: MutableList<String>)
    suspend fun createNewMessage (id: String, message: Message): DocumentReference
    suspend fun getCurrentChats(id: String): List<DocumentSnapshot>
    fun getAllMessages(id: String): Query
    suspend fun deleteCurrentChats(id: String)
}