package com.projects.mycompany.cary.data.repositories.inboxDataRepository

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.projects.mycompany.cary.data.sources.inboxDataSource.InboxDataSource
import com.projects.mycompany.cary.models.Message


class InboxDataRepositoryImp(private val inboxDataSourceImp: InboxDataSource): InboxDataRepository {
    override suspend fun createNewChat(senderReceiverList: MutableList<String>) {
        inboxDataSourceImp.createNewChat(senderReceiverList)
    }

    override suspend fun createNewMessage(id: String, message: Message): DocumentReference {
        return inboxDataSourceImp.createNewMessage(id,message)
    }

    override suspend fun getCurrentChats(id: String): List<DocumentSnapshot> {
        return inboxDataSourceImp.getCurrentChats(id)
    }

    override fun getAllMessages(id: String): Query {
        return inboxDataSourceImp.getAllMessages(id)
    }

    override suspend fun deleteCurrentChats(id: String) {
        inboxDataSourceImp.deleteCurrentChats(id)
    }
}