package com.projects.mycompany.cary.data.sources.giversDataSource


import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import com.google.android.gms.tasks.Task
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.GiverFilter


interface GiverDataSource {
    // Remote
    suspend fun getGiverById(id: String): CareGiver
    suspend fun getGivers(): Resource<MutableList<CareGiver>>

    suspend fun getGivers0(filter: GiverFilter): MutableList<CareGiver>
    suspend fun getGivers(filter: GiverFilter): MutableList<CareGiver>
    suspend fun getGivers(field1: String, field2: String, value1: String, value2: String,filter: GiverFilter): MutableList<CareGiver>
    suspend fun getGivers(field: String, value: String,filter: GiverFilter): MutableList<CareGiver>
    suspend fun getGivers(id: String): MutableList<CareGiver>
    suspend fun updateChatList(id: String, value: String)
    fun updateTokenList(id: String, value: String): Task<Void>
    suspend fun<T> updateGiverInFireStore(id: String, field: String, value: T)
    suspend fun deleteGiver(id: String)
    suspend fun clearNoReadMessagesList(id: String, value: String)

    // Local
    fun getLocalGiverById(id: String): LiveData<CareGiver>
    suspend fun getSyncGiver(id: String): CareGiver
    suspend fun addNewGiverIntoRoom(careGiver: CareGiver)
    suspend fun updateGiverInRoom(careGiver: CareGiver)
    suspend fun deleteGiverFromRoom(id: String)
    suspend fun getAllGiversFromRoom(): MutableList<CareGiver>
    suspend fun deleteAllCareGivers()
}