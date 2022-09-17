package com.projects.mycompany.cary.caregiver.job

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GiverJobViewModel(private val repository: GiverDataRepository): ViewModel() {

    val currentCareGiver = repository.getLocalGiverById(getCurrentUser()!!.uid)

    private val _fullTimeChecked = MutableLiveData<Boolean>()
    private val _partTimeChecked = MutableLiveData<Boolean>()
    private val _availableChecked = MutableLiveData<Boolean>()
    private val _notAvailableChecked = MutableLiveData<Boolean>()

    private val _saveClick = MutableLiveData<Boolean>()
    val saveClick: LiveData<Boolean>
        get() = _saveClick

    private val _experience = MutableLiveData<String>()
    val experience: LiveData<String>
        get() = _experience

    private val _maxPrice = MutableLiveData<Int>()
    private val _minPrice = MutableLiveData<Int>()
    private val _jobType = MutableLiveData<String>()
    private val _availability = MutableLiveData<String>()

    private val _validInfo = MutableLiveData<String>()
    val validInfo: LiveData<String>
        get() = _validInfo

    init {
        _jobType.value = FULL_TIME
        _availability.value = YES
        _fullTimeChecked.value = _jobType.value == FULL_TIME
        _partTimeChecked.value = _jobType.value == PART_TIME
        _availableChecked.value = _availability.value == YES
        _notAvailableChecked.value = _availability.value == NO
    }


    fun setInfo(minPrice: String, maxPrice: String,jobType: String, availability: String){
        _minPrice.value = if(minPrice.isEmpty()) 0 else minPrice.toInt()
        _maxPrice.value = if(maxPrice.isEmpty()) 0 else maxPrice.toInt()
        _jobType.value = jobType
        _availability.value = availability
    }

    fun setExperience(experience: String){
        _experience.value = experience
    }

    fun validateAndSave(){
        when{
            _minPrice.value == 0 || _maxPrice.value == 0 -> _validInfo.value = MISSING_PRICES
            _minPrice.value!! >= _maxPrice.value!! -> _validInfo.value = INVALID_PRICE
            _jobType.value!!.isEmpty() -> _validInfo.value = MISSING_JOB_TYPE
            _availability.value!!.isEmpty() -> _validInfo.value = MISSING_AVAILABILITY
            else -> _validInfo.value = VALID_INFO
        }
    }

    fun onSaveClick(){
        _saveClick.value = true
        updateFields()
    }

    fun onSaveClicked(){
        _saveClick.value = false
    }

    private fun updateFields(){
        val price = "$${_minPrice.value}-${_maxPrice.value}/h"
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateGiverInFireStore(getCurrentUser()!!.uid, REMOTE_PRICE,price)
            repository.updateGiverInFireStore(getCurrentUser()!!.uid, REMOTE_EXP, _experience.value)
            repository.updateGiverInFireStore(getCurrentUser()!!.uid, REMOTE_JOB, _jobType.value)
            repository.updateGiverInFireStore(getCurrentUser()!!.uid, REMOTE_AVAILABILITY, _availability.value)

            currentCareGiver.value!!.price = price
            currentCareGiver.value!!.experience = _experience.value!!
            currentCareGiver.value!!.job = _jobType.value!!
            currentCareGiver.value!!.availability = _availability.value!!

            repository.updateGiverInRoom(currentCareGiver.value!!)
        }
    }


}