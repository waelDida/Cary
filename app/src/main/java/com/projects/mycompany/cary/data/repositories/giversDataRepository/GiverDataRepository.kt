package com.projects.mycompany.cary.data.repositories.giversDataRepository

import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.GiverFilter

interface GiverDataRepository {

    suspend fun getRemoteGiverById(id: String): CareGiver
    suspend fun getGivers(): Resource<MutableList<CareGiver>>
    suspend fun getGivers(filter: GiverFilter): Resource<MutableList<CareGiver>>
    suspend fun getGivers(id: String): Resource<MutableList<CareGiver>>
    suspend fun getFilteredGivers(filter: GiverFilter): Resource<MutableList<CareGiver>>
    suspend fun getFilteredGivers(field1: String, field2: String, value1: String, value2: String, filter: GiverFilter): Resource<MutableList<CareGiver>>
    suspend fun getFilteredGivers(field: String, value: String,filter: GiverFilter): Resource<MutableList<CareGiver>>
    suspend fun<T> updateGiverInFireStore(id: String, field: String, value: T)
    suspend fun updateChatList(id: String, value: String)
    fun updateTokenList(id: String, value: String): Task<Void>
    suspend fun deleteGiver(id: String)
    suspend fun clearNoReadMessages(id: String, value: String)

    // Local Api
     fun getLocalGiverById(id: String): LiveData<CareGiver>
    suspend fun getSyncGiver(id: String): CareGiver
    suspend fun addNewGiverIntoRoom(careGiver: CareGiver)
    suspend fun updateGiverInRoom(careGiver: CareGiver)
    suspend fun deleteGiverFromRoom(id: String)


}