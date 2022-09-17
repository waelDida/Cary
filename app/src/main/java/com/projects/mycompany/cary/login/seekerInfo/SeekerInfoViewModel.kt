package com.projects.mycompany.cary.login.seekerInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SeekerInfoViewModel: ViewModel() {

    private val _validity = MutableLiveData<Boolean>()
    val validity: LiveData<Boolean>
        get() = _validity

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String>
        get() = _phoneNumber

    private val _address = MutableLiveData<String>()
    val address: LiveData<String>
        get() = _address


    private val _finishClick = MutableLiveData<Boolean>()
    val finishClick: LiveData<Boolean>
        get() = _finishClick


    fun setInfo(phoneNumber: String, address: String){
        _phoneNumber.value = phoneNumber.trim()
        _address.value = address.trim()
    }

    fun checkValidity(){
        _validity.value = (_phoneNumber.value!!.isEmpty() || _address.value!!.isEmpty())

    }
    fun onFinishClick(){
        _finishClick.value = true
    }

    fun onFinishClicked(){
        _finishClick.value = false
    }
}