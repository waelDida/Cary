package com.projects.mycompany.cary.data.sources.inboxDataSource

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.projects.mycompany.cary.api.*
import com.projects.mycompany.cary.models.Message


class InboxDataSourceImp: InboxDataSource {
    override suspend fun createNewChat(senderReceiverList: MutableList<String>) {
        createChat(senderReceiverList)
    }

    override suspend fun createNewMessage(id: String, message: Message): DocumentReference {
        return createMessageForChat(id,message)
    }

    override suspend fun getCurrentChats(id: String): List<DocumentSnapshot> {
        return getChats(id)
    }

    override fun getAllMessages(id: String): Query {
        return getAllMessagesForChat(id)
    }

    override suspend fun deleteCurrentChats(id: String) {
        deleteChat(id)
    }
}