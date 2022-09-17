package com.projects.mycompany.cary.data.sources.seekerDataSource

import android.content.Context
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.projects.mycompany.cary.api.*
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.models.SeekerFilter
import com.projects.mycompany.cary.room.AppDataBase
import com.projects.mycompany.cary.test.seeker1
import com.projects.mycompany.cary.test.seeker2
import com.projects.mycompany.cary.test.seeker4
import com.projects.mycompany.cary.utils.convertToCareSeekerList
import com.projects.mycompany.cary.utils.filterCareSeekers


class SeekerDataSourceImp(val context: Context): SeekerDataSource {

    //Remote
    override suspend fun addNewSeekerToFireStore(careSeeker: CareSeeker) {
        TODO("Not yet implemented")
    }

    override suspend fun getSeekerByIdFromFireStore(id: String): CareSeeker {
        val documentSnapshot = getCareSeekerFromFireStore(id)
        val careSeeker = documentSnapshot.toObject(CareSeeker::class.java)
        return careSeeker!!
    }

    override suspend fun deleteSeekerFromFireStore(id: String) {
        deleteCareSeekerFromFireStore(id)
    }

    // For Test purpose
    override suspend fun getSeekers(): MutableList<CareSeeker> {
        return mutableListOf(seeker1, seeker2, seeker4)
    }

    override suspend fun getSeekers(filter: SeekerFilter): MutableList<CareSeeker> {
        val docList = getCareSeekers(filter)
        val list = convertToCareSeekerList(docList)
        return filterCareSeekers(list,filter)
    }

    override suspend fun getSeekers(id: String): MutableList<CareSeeker> {
        val docList = getCareSeekers(id)
        return convertToCareSeekerList(docList)
    }

    override suspend fun <T> updateSeekerInFireStore(id: String, field: String, value: T) {
        updateCareSeekerInFireStore(id,field,value)
    }

    override suspend fun updateChatList(id: String, value: String) {
        updateCareSeekerChatList(id,value)
    }

    override fun updateTokenList(id: String, value: String): Task<Void> {
        return updateCareSeekerMessageToken(id,value)
    }

    override suspend fun clearNoReadMessagesList(id: String, value: String) {
        clearCareSeekerNoReadMessages(id,value)
    }

    // Local
    override fun getLocalSeekerById(id: String): LiveData<CareSeeker> {
        return AppDataBase.getInstance(context).seekerDao.getSeeker(id)
    }

    override suspend fun getSyncSeeker(id: String): CareSeeker {
        return AppDataBase.getInstance(context).seekerDao.getSyncSeeker(id)
    }

    override suspend fun addNewSeekerIntoRoom(careSeeker: CareSeeker) {
        AppDataBase.getInstance(context).seekerDao.insert(careSeeker)
    }

    override suspend fun updateSeekerInRoom(careSeeker: CareSeeker) {
        AppDataBase.getInstance(context).seekerDao.update(careSeeker)
    }

    override suspend fun deleteSeekerFromRoom(id: String) {
        AppDataBase.getInstance(context).seekerDao.delete(id)
    }

    override suspend fun getSeekersFromRoom(): MutableList<CareSeeker> {
        return AppDataBase.getInstance(context).seekerDao.getSeekers().toMutableList()
    }

    override suspend fun deleteAllSeekers() {
        AppDataBase.getInstance(context).seekerDao.deleteAllCareSeekers()
    }
}