package com.projects.mycompany.cary.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projects.mycompany.cary.utils.*


class LoginViewModel: ViewModel() {

    // ********************
    private val _firstName = MutableLiveData<String>()
    val firstName: LiveData<String>
        get() = _firstName

    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String>
        get() = _lastName

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    //**********************************

    private val _latitude = MutableLiveData<Double?>()
    val latitude: LiveData<Double?>
        get() = _latitude

    private val _longitude = MutableLiveData<Double?>()
    val longitude: LiveData<Double?>
        get() = _longitude

    // *********************
    private val _userType = MutableLiveData<String>()
    val userType: LiveData<String>
        get() = _userType

    private val _careCategory = MutableLiveData<String>()
    val careCategory: LiveData<String>
        get() = _careCategory

    //*******************************

    // Sign up first step (Primary information)
    fun setFirstInfos(firstName: String, lastName: String, email: String, password: String){
        _firstName.value = firstName
        _lastName.value = lastName
        _email.value = email
        _password.value = password
    }

    fun setLocation(latitude: Double?, longitude: Double?){
        _latitude.value = latitude
        _longitude.value = longitude
    }

    // Sign up second step (user type)
    fun selectCareGiver(){
        _userType.value = isCareGiver
    }
    fun selectCareSeeker(){
        _userType.value = isCareSeeker
    }

    //  Care categories
    fun childCareClick(){
        _careCategory.value = CHILD_CARE
    }

    fun petCareClick(){
        _careCategory.value = PET_CARE
    }

    fun housekeepingClick(){
        _careCategory.value = HOUSE_KEEPING
    }

    fun seniorCareClick(){
        _careCategory.value = SENIOR_CARE
    }

    fun specialNeedsClick(){
        _careCategory.value = SPECIAL_NEEDS
    }

    fun tutorClick(){
        _careCategory.value = TUTOR
    }
}