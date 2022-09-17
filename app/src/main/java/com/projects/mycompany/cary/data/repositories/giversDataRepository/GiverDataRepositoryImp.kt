package com.projects.mycompany.cary.data.repositories.giversDataRepository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import com.google.android.gms.tasks.Task
import com.projects.mycompany.cary.data.sources.giversDataSource.GiverDataSource
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.GiverFilter


class GiverDataRepositoryImp (private val giverDataSource: GiverDataSource):
    GiverDataRepository {

    // Remote
    override suspend fun getRemoteGiverById(id: String): CareGiver {
        return giverDataSource.getGiverById(id)
    }

    override suspend fun getGivers(): Resource<MutableList<CareGiver>> {
        return giverDataSource.getGivers()
    }

    override suspend fun getGivers(filter: GiverFilter): Resource<MutableList<CareGiver>> {
        val x = giverDataSource.getGivers0(filter)
        val y = updateAndGetCaregiversInRoom(x)
        return if(y.isEmpty()) Resource.Empty(y) else Resource.Success(y)
    }

    override suspend fun getGivers(id: String): Resource<MutableList<CareGiver>> {
        val x = giverDataSource.getGivers(id)
        val y = updateAndGetCaregiversInRoom(x)
        return if(y.isEmpty()) Resource.Empty(y) else Resource.Success(y)
    }

    override suspend fun getFilteredGivers(filter: GiverFilter): Resource<MutableList<CareGiver>> {
        val x = giverDataSource.getGivers(filter)
        val y = updateAndGetCaregiversInRoom(x)
        return if(y.isEmpty()) Resource.Empty(y) else Resource.Success(y)
    }

    override suspend fun getFilteredGivers(
        field1: String,
        field2: String,
        value1: String,
        value2: String,
        filter: GiverFilter
    ): Resource<MutableList<CareGiver>> {
        val x = giverDataSource.getGivers(field1,field2,value1,value2,filter)
        val y = updateAndGetCaregiversInRoom(x)
        return if(y.isEmpty()) Resource.Empty(y) else Resource.Success(y)
    }

    override suspend fun getFilteredGivers(
        field: String,
        value: String,
        filter: GiverFilter
    ): Resource<MutableList<CareGiver>> {
        val x =  giverDataSource.getGivers(field, value, filter)
        val y = updateAndGetCaregiversInRoom(x)
        return if(y.isEmpty()) Resource.Empty(y) else Resource.Success(y)
    }

    override suspend fun<T> updateGiverInFireStore(id: String, field: String, value: T) {
        giverDataSource.updateGiverInFireStore(id,field,value)
    }

    override suspend fun updateChatList(id: String, value: String) {
        giverDataSource.updateChatList(id,value)
    }

    override fun updateTokenList(id: String, value: String): Task<Void> {
        return giverDataSource.updateTokenList(id,value)
    }

    override suspend fun deleteGiver(id: String) {
        giverDataSource.deleteGiver(id)
    }

    override suspend fun clearNoReadMessages(id: String, value: String) {
        giverDataSource.clearNoReadMessagesList(id, value)
    }

    //Local
    override  fun getLocalGiverById(id: String): LiveData<CareGiver> {
        return giverDataSource.getLocalGiverById(id)
    }

    override suspend fun getSyncGiver(id: String): CareGiver {
        return giverDataSource.getSyncGiver(id)
    }

    override suspend fun addNewGiverIntoRoom(careGiver: CareGiver) {
        giverDataSource.addNewGiverIntoRoom(careGiver)
    }

    override suspend fun updateGiverInRoom(careGiver: CareGiver) {
        return giverDataSource.updateGiverInRoom(careGiver)
    }

    override suspend fun deleteGiverFromRoom(id: String) {
        return giverDataSource.deleteGiverFromRoom(id)
    }

    private suspend fun updateAndGetCaregiversInRoom(list: MutableList<CareGiver>): MutableList<CareGiver> {
        giverDataSource.deleteAllCareGivers()
        list.forEach {
            giverDataSource.addNewGiverIntoRoom(it)
        }
        return giverDataSource.getAllGiversFromRoom()
    }
}