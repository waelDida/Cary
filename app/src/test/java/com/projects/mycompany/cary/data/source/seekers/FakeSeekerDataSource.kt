package com.projects.mycompany.cary.data.source.seekers

import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.projects.mycompany.cary.data.sources.seekerDataSource.SeekerDataSource
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.models.SeekerFilter
import com.projects.mycompany.cary.utils.filterCareSeekers
import com.projects.mycompany.cary.utils.sortCareSeekers


class FakeSeekerDataSource(var list: MutableList<CareSeeker>? = mutableListOf()): SeekerDataSource {

    private var localList = mutableListOf<CareSeeker>()

    // Remote
    override suspend fun addNewSeekerToFireStore(careSeeker: CareSeeker) {
        list?.add(careSeeker)
    }

    override suspend fun getSeekerByIdFromFireStore(id: String): CareSeeker {
        return list?.filter { it.uid == id }!![0]
    }

    override suspend fun deleteSeekerFromFireStore(id: String) {
        val x = list?.filter { it.uid == id }!![0]
        list?.remove(x)
    }

    override suspend fun getSeekers(): MutableList<CareSeeker> {
        TODO("Not yet implemented")
    }

    override suspend fun getSeekers(filter: SeekerFilter): MutableList<CareSeeker> {
        return sortCareSeekers(filterCareSeekers(list!!,filter))
    }

    override suspend fun getSeekers(id: String): MutableList<CareSeeker> {
        TODO("Not yet implemented")
    }

    override suspend fun <T> updateSeekerInFireStore(id: String, field: String, value: T) {
        TODO("Not yet implemented")
    }

    override suspend fun updateChatList(id: String, value: String) {
        TODO("Not yet implemented")
    }

    override fun updateTokenList(id: String, value: String): Task<Void> {
        TODO("Not yet implemented")
    }

    override suspend fun clearNoReadMessagesList(id: String, value: String) {
        TODO("Not yet implemented")
    }

    // Local
    override fun getLocalSeekerById(id: String): LiveData<CareSeeker> {
        TODO("Not yet implemented")
    }

    override suspend fun getSyncSeeker(id: String): CareSeeker {
        TODO("Not yet implemented")
    }

    override suspend fun addNewSeekerIntoRoom(careSeeker: CareSeeker) {
        localList.add(careSeeker)
    }

    override suspend fun updateSeekerInRoom(careSeeker: CareSeeker) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSeekerFromRoom(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getSeekersFromRoom(): MutableList<CareSeeker> {
        return localList
    }

    override suspend fun deleteAllSeekers() {
        localList.clear()
    }
}