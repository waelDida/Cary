package com.projects.mycompany.cary.caregiver.contactinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GiverContactViewModel(private val repository: GiverDataRepository): ViewModel() {

    val currentCareGiver = repository.getLocalGiverById(getCurrentUser()!!.uid)

    private val _saveClick = MutableLiveData<Boolean>()
    val saveClick: LiveData<Boolean>
        get() = _saveClick

    private val _address = MutableLiveData<String>()
    val address: LiveData<String>
        get() = _address

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String>
        get() = _phoneNumber

    private val _validInfo = MutableLiveData<String>()
    val validInfo: LiveData<String>
        get() = _validInfo


    fun setInfo(address: String, phoneNumber:String){
        _address.value = address.trim()
        _phoneNumber.value = phoneNumber.trim()
    }

    fun checkThenValidate(){
        when{
            _address.value!!.isEmpty() && _phoneNumber.value!!.isEmpty() -> _validInfo.value = MISSING_INFO
            _address.value!!.isEmpty() -> _validInfo.value = MISSING_ADDRESS
            _phoneNumber.value!!.isEmpty() -> _validInfo.value = MISSING_PHONE_NUMBER
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
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateGiverInFireStore(getCurrentUser()!!.uid, REMOTE_ADDRESS,_address.value)
            repository.updateGiverInFireStore(getCurrentUser()!!.uid, REMOTE_PHONE,_phoneNumber.value)

            currentCareGiver.value!!.address = _address.value!!
            currentCareGiver.value!!.phone = _phoneNumber.value!!

            repository.updateGiverInRoom(currentCareGiver.value!!)
        }
    }


}