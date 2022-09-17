package com.projects.mycompany.cary.data.source.seekers

import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.models.SeekerFilter
import com.projects.mycompany.cary.resources.Resource

class FakeSeekerDataRepo: SeekerDataRepository {

    var seekers : LinkedHashMap<String,CareSeeker>  = LinkedHashMap()

    fun addSeekers(vararg careSeekers: CareSeeker){
        for(careSeeker in careSeekers)
            seekers[careSeeker.uid] = careSeeker
    }

    // Remote
    override suspend fun addNewSeekerIntoFireStore(careSeeker: CareSeeker) {
        seekers[careSeeker.uid] = CareSeeker()
    }

    override suspend fun getRemoteSeekerById(id: String): CareSeeker {
        return seekers[id]!!
    }

    override suspend fun deleteSeekerFromFireStore(id: String) {
        seekers.remove(id)
    }

    override suspend fun getSeekers(): Resource<MutableList<CareSeeker>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSeekers(filter: SeekerFilter): Resource<MutableList<CareSeeker>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSeekers(id: String): Resource<MutableList<CareSeeker>> {
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

    //Local
    override fun getLocalSeekerById(id: String): LiveData<CareSeeker> {
        TODO("Not yet implemented")
    }

    override suspend fun getSyncSeeker(id: String): CareSeeker {
        TODO("Not yet implemented")
    }

    override suspend fun addNewSeekerIntoRoom(careSeeker: CareSeeker) {
        TODO("Not yet implemented")
    }

    override suspend fun updateSeekerInRoom(careSeeker: CareSeeker) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSeekerFromRoom(id: String) {
        TODO("Not yet implemented")
    }
}