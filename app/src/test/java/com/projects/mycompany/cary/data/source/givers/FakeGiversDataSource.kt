package com.projects.mycompany.cary.data.source.givers

import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.projects.mycompany.cary.data.sources.giversDataSource.GiverDataSource
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.GiverFilter
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.test.giver1
import com.projects.mycompany.cary.test.giver4
import com.projects.mycompany.cary.test.giver5
import com.projects.mycompany.cary.utils.filterCareGivers
import com.projects.mycompany.cary.utils.sortCareGivers


class FakeGiversDataSource(private val list: MutableList<CareGiver>? = mutableListOf()): GiverDataSource {

    private var localList = mutableListOf(giver1,giver4, giver5)
    // Remote
    override suspend fun getGivers0(filter: GiverFilter): MutableList<CareGiver> {
        return sortCareGivers(filterCareGivers(list!!,filter))
    }

    override suspend fun updateChatList(id: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun updateTokenList(id: String, value: String): Task<Void> {
        TODO("Not yet implemented")
    }

    override suspend fun getGivers(filter: GiverFilter): MutableList<CareGiver> {
        return sortCareGivers(filterCareGivers(list!!,filter))
    }

    override suspend fun getGivers(
        field1: String,
        field2: String,
        value1: String,
        value2: String,
        filter: GiverFilter
    ): MutableList<CareGiver> {
        TODO("Not yet implemented")
    }

    override suspend fun getGivers(
        field: String,
        value: String,
        filter: GiverFilter
    ): MutableList<CareGiver> {
        TODO("Not yet implemented")
    }

    override suspend fun getGivers(id: String): MutableList<CareGiver> {
        TODO("Not yet implemented")
    }

    override suspend fun <T> updateGiverInFireStore(id: String, field: String, value: T) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGiver(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun clearNoReadMessagesList(id: String, value: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getGiverById(id: String): CareGiver {
        return list?.filter { it.uid == id }!![0]
    }

    override suspend fun getGivers(): Resource<MutableList<CareGiver>> {
        TODO("Not yet implemented")
    }

    // Local
    override  fun getLocalGiverById(id: String): LiveData<CareGiver> {
        TODO("Not yet implemented")
    }

    override suspend fun getSyncGiver(id: String): CareGiver {
        TODO("Not yet implemented")
    }

    override suspend fun addNewGiverIntoRoom(careGiver: CareGiver) {
        localList.add(careGiver)
    }

    override suspend fun updateGiverInRoom(careGiver: CareGiver) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGiverFromRoom(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllGiversFromRoom(): MutableList<CareGiver> {
        return localList
    }

    override suspend fun deleteAllCareGivers() {
        localList.clear()
    }
}