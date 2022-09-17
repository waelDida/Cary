package com.projects.mycompany.cary.data.repositories.seekerDataRepository

import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.models.SeekerFilter
import com.projects.mycompany.cary.resources.Resource

interface SeekerDataRepository {

    // Remote Api
    suspend fun addNewSeekerIntoFireStore(careSeeker: CareSeeker)
    suspend fun getRemoteSeekerById(id: String): CareSeeker
    suspend fun deleteSeekerFromFireStore(id: String)
    suspend fun getSeekers(): Resource<MutableList<CareSeeker>>
    suspend fun getSeekers(filter: SeekerFilter): Resource<MutableList<CareSeeker>>
    suspend fun getSeekers(id: String): Resource<MutableList<CareSeeker>>
    suspend fun<T> updateSeekerInFireStore(id: String, field: String, value:T)
    suspend fun updateChatList(id: String, value: String)
    fun updateTokenList(id: String, value: String): Task<Void>
    suspend fun clearNoReadMessagesList(id: String,value:String)


    // Local Api
    fun getLocalSeekerById(id: String): LiveData<CareSeeker>
    suspend fun getSyncSeeker(id: String): CareSeeker
    suspend fun addNewSeekerIntoRoom(careSeeker: CareSeeker)
    suspend fun updateSeekerInRoom(careSeeker: CareSeeker)
    suspend fun deleteSeekerFromRoom(id: String)
}