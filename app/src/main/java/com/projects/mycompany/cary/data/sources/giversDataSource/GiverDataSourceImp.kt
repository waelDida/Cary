package com.projects.mycompany.cary.data.sources.giversDataSource




import android.content.Context
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.projects.mycompany.cary.api.*
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.GiverFilter
import com.projects.mycompany.cary.room.AppDataBase
import com.projects.mycompany.cary.test.giver1
import com.projects.mycompany.cary.test.giver2
import com.projects.mycompany.cary.test.giver3
import com.projects.mycompany.cary.test.giver4
import com.projects.mycompany.cary.utils.*


class GiverDataSourceImp(val context: Context): GiverDataSource {

    override suspend fun getGiverById(id: String): CareGiver {
        val giverSnapshot = getCurrentGiverFromFireStore(id)
        return giverSnapshot!!.toObject(CareGiver::class.java)!!
    }

    // For testing purpose
    override suspend fun getGivers(): Resource<MutableList<CareGiver>> {
        val list = mutableListOf(giver1, giver2, giver3, giver4)
        return Resource.Success(list)
    }

    override suspend fun getGivers0(filter: GiverFilter): MutableList<CareGiver> {
        val docList = getGiversFromFireStore()
        val  list = convertToCareGiverList(docList)
        return filterCareGivers(list,filter)
    }

    override suspend fun updateChatList(id: String, value: String) {
        updateCareGiverChatList(id,value)
    }

    override fun updateTokenList(id: String, value: String): Task<Void> {
        return updateCareGiverMessageToken(id,value)
    }

    override suspend fun getGivers(filter: GiverFilter): MutableList<CareGiver> {
        val docList = getGiversFromFireStore(filter)
        val list = convertToCareGiverList(docList)
        return filterCareGivers(list,filter)
    }

    override suspend fun getGivers(
        field1: String,
        field2: String,
        value1: String,
        value2: String,
        filter: GiverFilter
    ): MutableList<CareGiver> {
        val docList = getGiversFromFireStore(field1,field2,value1,value2)
        val list = convertToCareGiverList(docList)
        return  filterCareGivers(list,filter)
    }

    override suspend fun getGivers(
        field: String,
        value: String,
        filter: GiverFilter
    ): MutableList<CareGiver> {
        val docList = getGiversFromFireStore(field,value)
        val list = convertToCareGiverList(docList)
        return  filterCareGivers(list,filter)
    }

    override suspend fun getGivers(id: String): MutableList<CareGiver> {
        val docList = getGiversFromFireStore(id)
        return convertToCareGiverList(docList)
    }

    override suspend fun<T> updateGiverInFireStore(id: String, field: String, value: T) {
        updateCareGiverInFireStore(id,field,value)
    }

    override suspend fun deleteGiver(id: String) {
        deleteGiverFromFireStore(id)
    }

    override suspend fun clearNoReadMessagesList(id: String, value: String) {
        clearCareGiverNoReadMessages(id,value)
    }


    // Local
    override fun getLocalGiverById(id: String): LiveData<CareGiver> {
        return AppDataBase.getInstance(context).giverDao.getGiver(id)
    }

    override suspend fun getSyncGiver(id: String): CareGiver {
        return AppDataBase.getInstance(context).giverDao.getSyncGiver(id)
    }

    override suspend fun addNewGiverIntoRoom(careGiver: CareGiver) {
        AppDataBase.getInstance(context).giverDao.insert(careGiver)
    }

    override suspend fun updateGiverInRoom(careGiver: CareGiver) {
        AppDataBase.getInstance(context).giverDao.update(careGiver)
    }

    override suspend fun deleteGiverFromRoom(id: String) {
        AppDataBase.getInstance(context).giverDao.delete(id)
    }

    override suspend fun getAllGiversFromRoom(): MutableList<CareGiver> {
        return AppDataBase.getInstance(context).giverDao.getGivers().toMutableList()
    }

    override suspend fun deleteAllCareGivers() {
        return AppDataBase.getInstance(context).giverDao.deleteAllCaregivers()
    }

}