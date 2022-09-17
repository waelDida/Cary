package com.projects.mycompany.cary.caregiver.search

import android.app.Application
import androidx.lifecycle.*
import com.projects.mycompany.cary.caregiver.preference.GiverFilterPref
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.models.SeekerFilter
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception


class SearchGiverViewModel(private val giverRepo: GiverDataRepository,
                           private val repository: SeekerDataRepository,
                           application: Application): AndroidViewModel(application) {


    private lateinit var data : Resource<MutableList<CareSeeker>>
    private val filterPref = GiverFilterPref(application)
    private lateinit var filter: SeekerFilter

    private val mCurrentCareGiver = MutableLiveData<CareGiver>()


    val fetchData = liveData(Dispatchers.IO){
        val currentCareGiver = giverRepo.getSyncGiver(getCurrentUser()!!.uid)
        withContext(Dispatchers.Main){
            mCurrentCareGiver.value = currentCareGiver
            filter = SeekerFilter(filterPref.getMaxDistance(), mCurrentCareGiver.value!!.latitude,
                mCurrentCareGiver.value!!.longitude, mCurrentCareGiver.value!!.careCategory)
        }
        emit(Resource.Loading())
        try{
            data = repository.getSeekers(filter)
            emit(data)
        }catch (e: Exception){emit(Resource.Failure(e.cause!!))}
    }
}