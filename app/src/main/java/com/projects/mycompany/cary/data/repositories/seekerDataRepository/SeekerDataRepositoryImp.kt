package com.projects.mycompany.cary.data.repositories.seekerDataRepository


import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.projects.mycompany.cary.data.sources.seekerDataSource.SeekerDataSource
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.models.SeekerFilter
import com.projects.mycompany.cary.resources.Resource

class SeekerDataRepositoryImp(private val seekerDataSource: SeekerDataSource)
    : SeekerDataRepository{

    // Remote
    override suspend fun addNewSeekerIntoFireStore(careSeeker: CareSeeker) {
       return seekerDataSource.addNewSeekerToFireStore(careSeeker)
    }

    override suspend fun getRemoteSeekerById(id: String): CareSeeker {
        return seekerDataSource.getSeekerByIdFromFireStore(id)
    }

    override suspend fun deleteSeekerFromFireStore(id: String) {
        seekerDataSource.deleteSeekerFromFireStore(id)
    }

    override suspend fun getSeekers(): Resource<MutableList<CareSeeker>> {
        val x = seekerDataSource.getSeekers()
        val y = updateAndGetSeekersInRoom(x)
        return if(y.isEmpty()) Resource.Empty(y) else Resource.Success(y)
    }

    override suspend fun getSeekers(filter: SeekerFilter): Resource<MutableList<CareSeeker>> {
        val x = seekerDataSource.getSeekers(filter)
        val y = updateAndGetSeekersInRoom(x)
        return if(y.isEmpty()) Resource.Empty(y) else Resource.Success(y)
    }

    override suspend fun getSeekers(id: String): Resource<MutableList<CareSeeker>> {
        val x = seekerDataSource.getSeekers(id)
        val y = updateAndGetSeekersInRoom(x)
        return if(y.isEmpty()) Resource.Empty(y) else Resource.Success(y)
    }

    override suspend fun <T> updateSeekerInFireStore(id: String, field: String, value: T) {
        seekerDataSource.updateSeekerInFireStore(id,field,value)
    }

    override suspend fun updateChatList(id: String, value: String) {
        seekerDataSource.updateChatList(id,value)
    }

    override fun updateTokenList(id: String, value: String): Task<Void> {
        return seekerDataSource.updateTokenList(id,value)
    }

    override suspend fun clearNoReadMessagesList(id: String, value: String) {
        seekerDataSource.clearNoReadMessagesList(id,value)
    }

    // Local
    override fun getLocalSeekerById(id: String): LiveData<CareSeeker> {
        return  seekerDataSource.getLocalSeekerById(id)
    }

    override suspend fun getSyncSeeker(id: String): CareSeeker {
        return seekerDataSource.getSyncSeeker(id)
    }

    override suspend fun addNewSeekerIntoRoom(careSeeker: CareSeeker) {
        seekerDataSource.addNewSeekerIntoRoom(careSeeker)
    }

    override suspend fun updateSeekerInRoom(careSeeker: CareSeeker) {
        seekerDataSource.updateSeekerInRoom(careSeeker)
    }

    override suspend fun deleteSeekerFromRoom(id: String) {
        seekerDataSource.deleteSeekerFromRoom(id)
    }


    private suspend fun updateAndGetSeekersInRoom(list: MutableList<CareSeeker>): MutableList<CareSeeker>{
        seekerDataSource.deleteAllSeekers()
        list.forEach {
            seekerDataSource.addNewSeekerIntoRoom(it)
        }
        return seekerDataSource.getSeekersFromRoom()
    }
}