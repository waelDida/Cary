package com.projects.mycompany.cary.login.giverInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projects.mycompany.cary.utils.*

class GiverInfoViewModel: ViewModel() {

    private val _validInfo = MutableLiveData<String>()
    val validInfo: LiveData<String>
        get() = _validInfo

    private val _birthDate = MutableLiveData<String>()
    val birthDate: LiveData<String>
        get() = _birthDate

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String>
        get() = _phoneNumber

    private val _address = MutableLiveData<String>()
    val address: LiveData<String>
        get() = _address

    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String>
        get() = _gender

    private val _price = MutableLiveData<String>()
    val price: LiveData<String>
        get() = _price

    private val _experience = MutableLiveData<String>()
    val experience: LiveData<String>
        get() = _experience

    private val _jobType = MutableLiveData<String>()
    val jobType: LiveData<String>
        get() = _jobType

    private val _availability = MutableLiveData<String>()
    val availability: LiveData<String>
        get() = _availability


    private val _birthDateClick = MutableLiveData<Boolean>()
    val birthDateClick: LiveData<Boolean>
        get() = _birthDateClick

    private val _onFinishClick = MutableLiveData<Boolean>()
    val onFinishClick: LiveData<Boolean>
        get() = _onFinishClick


    fun setLastInfo(birthDate: String, phoneNumber: String, address: String,
                     gender: String, price: String, experience: String, jobType: String, availability: String){
        _birthDate.value = birthDate
        _phoneNumber.value = phoneNumber.trim()
        _address.value = address.trim()
        _gender.value = gender
        _price.value = price.trim()
        _experience.value = experience
        _jobType.value = jobType
        _availability.value = availability
    }

    fun checkValidity(){
        if(_birthDate.value!!.isEmpty() || _phoneNumber.value!!.isEmpty()
                    || _address.value!!.isEmpty()||_price.value!!.isEmpty()|| _experience.value!!.isEmpty()
                    ||_jobType.value!!.isEmpty())
            _validInfo.value = MISSING_INFO
        else if(calculateAge(getDateFromString(_birthDate.value!!.toString())) <= 0)
            _validInfo.value = INVALID_AGE
        else if(calculateAge(getDateFromString(_birthDate.value!!.toString())) < 18)
            _validInfo.value = UNDER_18
        else
            _validInfo.value = VALID_INFO
    }

    fun onBirthDateClick(){
        _birthDateClick.value = true
    }

    fun onBirthDateClicked(){
        _birthDateClick.value = false
    }

    fun finishClick(){
        _onFinishClick.value = true
    }

    fun finishClicked(){
        _onFinishClick.value = false
    }
}