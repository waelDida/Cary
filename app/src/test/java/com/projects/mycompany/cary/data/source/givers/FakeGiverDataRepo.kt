package com.projects.mycompany.cary.data.source.givers

import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.GiverFilter
import com.projects.mycompany.cary.resources.Resource

class FakeGiverDataRepo: GiverDataRepository {

    var givers : LinkedHashMap<String,CareGiver>  = LinkedHashMap()

    fun addGivers(vararg careGivers: CareGiver){
        for(careGiver in careGivers)
            givers[careGiver.uid] = careGiver
    }

    // Remote
    override suspend fun getRemoteGiverById(id: String): CareGiver {
        TODO("Not yet implemented")
    }

    override suspend fun getGivers(): Resource<MutableList<CareGiver>> {
        TODO("Not yet implemented")
    }

    override suspend fun getGivers(filter: GiverFilter): Resource<MutableList<CareGiver>> {
        TODO("Not yet implemented")
    }

    override suspend fun getGivers(id: String): Resource<MutableList<CareGiver>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFilteredGivers(filter: GiverFilter): Resource<MutableList<CareGiver>> {
        val list = givers as MutableList<CareGiver>
        return Resource.Success(list)
    }

    override suspend fun getFilteredGivers(
        field1: String,
        field2: String,
        value1: String,
        value2: String,
        filter: GiverFilter
    ): Resource<MutableList<CareGiver>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFilteredGivers(
        field: String,
        value: String,
        filter: GiverFilter
    ): Resource<MutableList<CareGiver>> {
        TODO("Not yet implemented")
    }

    override suspend fun <T> updateGiverInFireStore(id: String, field: String, value: T) {
        TODO("Not yet implemented")
    }

    override suspend fun updateChatList(id: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun updateTokenList(id: String, value: String): Task<Void> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGiver(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun clearNoReadMessages(id: String, value: String) {
        TODO("Not yet implemented")
    }


    // Local
    override fun getLocalGiverById(id: String): LiveData<CareGiver> {
        TODO("Not yet implemented")
    }

    override suspend fun getSyncGiver(id: String): CareGiver {
        TODO("Not yet implemented")
    }

    override suspend fun addNewGiverIntoRoom(careGiver: CareGiver) {
        TODO("Not yet implemented")
    }

    override suspend fun updateGiverInRoom(careGiver: CareGiver) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGiverFromRoom(id: String) {
        TODO("Not yet implemented")
    }
}