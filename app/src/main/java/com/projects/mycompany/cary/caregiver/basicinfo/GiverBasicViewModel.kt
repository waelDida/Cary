package com.projects.mycompany.cary.caregiver.basicinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GiverBasicViewModel(private val repository: GiverDataRepository): ViewModel() {

    val currentCareGiver = repository.getLocalGiverById(getCurrentUser()!!.uid)
    
    private val _firstName = MutableLiveData<String>()
    private val _lastName = MutableLiveData<String>()
    private val _birthday = MutableLiveData<String>()


    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String>
        get() = _gender

    private val _saveBasicInfo = MutableLiveData<Boolean>()
    val saveBasicInfo: LiveData<Boolean>
        get() = _saveBasicInfo

    private val _validInfo = MutableLiveData<String>()
    val validInfo: LiveData<String>
        get() = _validInfo

    private val _onBirthdayClick = MutableLiveData<Boolean>()
    val onBirthdayClick: LiveData<Boolean>
        get() = _onBirthdayClick


    fun setInfo(firstName: String, lastName: String, birthday: String, gender: String){
        _firstName.value = firstName.trim()
        _lastName.value = lastName.trim()
        _birthday.value = birthday
        _gender.value = gender
    }

    fun checkAndValidate(){
        if(_firstName.value!!.isEmpty() || _lastName.value!!.isEmpty() || _birthday.value!!.isEmpty())
            _validInfo.value = MISSING_INFO
        else if(calculateAge(getDateFromString(_birthday.value!!)) <= 0)
            _validInfo.value = INVALID_AGE
        else if(calculateAge(getDateFromString(_birthday.value!!)) < 18)
            _validInfo.value = UNDER_18
        else{
            _validInfo.value = VALID_INFO
            updateFields()
        }

    }

    fun onBirthdayClick(){
        _onBirthdayClick.value = true
    }

    fun onBirthdayClicked(){
        _onBirthdayClick.value = false
    }

    fun onSaveClick(){
        _saveBasicInfo.value = true
    }

    fun onSaveClicked(){
        _saveBasicInfo.value = false
    }

    private fun updateFields(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateGiverInFireStore(getCurrentUser()!!.uid, REMOTE_FIRST_NAME, _firstName.value)
            repository.updateGiverInFireStore(getCurrentUser()!!.uid, REMOTE_LAST_NAME, _lastName.value)
            repository.updateGiverInFireStore(getCurrentUser()!!.uid, REMOTE_BIRTH_DAY, _birthday.value)
            repository.updateGiverInFireStore(getCurrentUser()!!.uid, REMOTE_GENDER, _gender.value)

            currentCareGiver.value!!.firstName = _firstName.value!!
            currentCareGiver.value!!.lastName = _lastName.value!!
            currentCareGiver.value!!.birthdate = _birthday.value!!
            currentCareGiver.value!!.gender = _gender.value!!

            repository.updateGiverInRoom(currentCareGiver.value!!)
        }
    }
}