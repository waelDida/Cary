package com.projects.mycompany.cary.careseeker.search


import android.app.Application
import androidx.lifecycle.*
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.careseeker.preference.SeekerFilterPref
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.models.GiverFilter
import com.projects.mycompany.cary.utils.*
import kotlinx.coroutines.*

class SearchSeekerViewModel(private val seekerRepo: SeekerDataRepository,
                            private val repository: GiverDataRepository,
                            application: Application): AndroidViewModel(application) {



    private lateinit var data : Resource<MutableList<CareGiver>>
    private val filterPref = SeekerFilterPref(application)
    private lateinit var filter: GiverFilter

    private var gender: String
    private var jobType: String

    private val test = MutableLiveData<CareSeeker>()

    init {

        //getTheSubscriptionState()

        gender = when{
            filterPref.isMaleChecked() && !filterPref.isFemaleChecked() -> MALE
            filterPref.isFemaleChecked() && !filterPref.isMaleChecked() -> FEMALE
            else -> BOTH_GENDERS
        }
        jobType = when{
            filterPref.isFullTimeChecked() && !filterPref.isPartTimeChecked() -> FULL_TIME
            filterPref.isPartTimeChecked() && !filterPref.isFullTimeChecked() -> PART_TIME
            else -> BOTH_JOB_TYPES
        }
    }

    val fetchData = liveData(Dispatchers.IO) {
            val currentCareSeeker = seekerRepo.getSyncSeeker(getCurrentUser()!!.uid)
            withContext(Dispatchers.Main){
                test.value = currentCareSeeker
                filter = GiverFilter(gender,filterPref.getExperience()!!,filterPref.getMaxDistance(),
                    filterPref.getMinAge(),filterPref.getMaxAge(),jobType, test.value!!.latitude,
                    test.value!!.longitude, test.value!!.careCategory)
        }
        emit(Resource.Loading())
        try{
             data = when{

                filter.gender != BOTH_GENDERS && filter.experience != YRS_EXP_ALL && filter.jobType != BOTH_JOB_TYPES
                -> repository.getFilteredGivers(filter)

                filter.gender != BOTH_GENDERS && filter.experience != YRS_EXP_ALL && filter.jobType == BOTH_JOB_TYPES
                -> repository.getFilteredGivers(REMOTE_GENDER, REMOTE_EXP,filter.gender,filter.experience,filter)

                filter.gender != BOTH_GENDERS && filter.experience == YRS_EXP_ALL && filter.jobType != BOTH_JOB_TYPES
                -> repository.getFilteredGivers(REMOTE_GENDER, REMOTE_JOB,filter.gender,filter.jobType,filter)

                filter.gender == BOTH_GENDERS && filter.experience != YRS_EXP_ALL && filter.jobType != BOTH_JOB_TYPES
                -> repository.getFilteredGivers(REMOTE_EXP, REMOTE_JOB,filter.experience,filter.jobType,filter)

                filter.gender!= BOTH_GENDERS -> repository.getFilteredGivers(REMOTE_GENDER,filter.gender,filter)

                filter.experience!= YRS_EXP_ALL -> repository.getFilteredGivers(REMOTE_EXP,filter.experience,filter)

                filter.jobType!= BOTH_JOB_TYPES -> repository.getFilteredGivers(REMOTE_JOB,filter.jobType,filter)

                else -> repository.getGivers(filter)
            }
            emit(data)
        }
        catch (e: Exception){emit(Resource.Failure(e.cause!!))}
 }

}