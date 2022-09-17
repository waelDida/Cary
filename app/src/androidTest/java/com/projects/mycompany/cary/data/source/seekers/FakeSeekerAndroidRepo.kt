package com.projects.mycompany.cary.data.source.seekers

import androidx.lifecycle.LiveData
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.models.SeekerFilter
import com.projects.mycompany.cary.resources.Resource

class FakeSeekerAndroidRepo: SeekerDataRepository {

    var seekers : LinkedHashMap<String, CareSeeker>  = LinkedHashMap()

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

    override suspend fun getSeekers(filter: SeekerFilter): Resource<MutableList<CareSeeker>> {
        TODO("Not yet implemented")
    }

    //Local
    override fun getLocalSeekerById(id: String): LiveData<CareSeeker> {
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