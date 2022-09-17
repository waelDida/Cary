package com.projects.mycompany.cary.careseeker.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import com.projects.mycompany.cary.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditSeekerViewModel(private val repository: SeekerDataRepository): ViewModel() {


    private val currentId = getCurrentUser()!!.uid
    val careSeeker = repository.getLocalSeekerById(currentId)

    private val _firstName = MutableLiveData<String>()
    val firstName : LiveData<String>
        get() = _firstName

    private val _lastName = MutableLiveData<String>()
    val lastName : LiveData<String>
        get() = _lastName

    private val _address = MutableLiveData<String>()
    val seekerAddress : LiveData<String>
        get() = _address

    private val _phoneNumber = MutableLiveData<String>()
    val seekerPhoneNumber : LiveData<String>
        get() = _phoneNumber


    private val _saveClick = MutableLiveData<Boolean>()
    val saveClick : LiveData<Boolean>
        get() = _saveClick


    private val _uploadImage = MutableLiveData<Boolean>()
    val uploadImage: LiveData<Boolean>
        get() = _uploadImage

    private val _validInfo = MutableLiveData<String>()
    val validInfo : LiveData<String>
        get() = _validInfo


    fun uploadImageClick(){
        _uploadImage.value = true
    }

    fun uploadImageClicked(){
        _uploadImage.value = false
    }

    fun setInfo(firstName: String, lastName: String,address: String, phoneNumber: String){
        _firstName.value = firstName.trim()
        _lastName.value = lastName.trim()
        _address.value = address.trim()
        _phoneNumber.value = phoneNumber.trim()
    }

    fun checkAndSave(){
        when{
            _address.value!!.isEmpty() && _phoneNumber.value!!.isEmpty()
                    && _firstName.value!!.isEmpty() && _lastName.value!!.isEmpty() -> _validInfo.value = MISSING_INFO
            _firstName.value!!.isEmpty() -> _validInfo.value = MISSING_FIRST_NAME
            _lastName.value!!.isEmpty() -> _validInfo.value = MISSING_LAST_NAME
            _address.value!!.isEmpty() -> _validInfo.value = MISSING_ADDRESS
            _phoneNumber.value!!.isEmpty() -> _validInfo.value = MISSING_PHONE_NUMBER
            else -> {
                _validInfo.value = VALID_INFO
                updateFields()
            }
        }
    }

    fun onSaveClick(){
        _saveClick.value = true
    }

    fun onSaveClicked(){
        _saveClick.value = false
    }

    private fun updateFields(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSeekerInFireStore(currentId, REMOTE_FIRST_NAME, _firstName.value)
            repository.updateSeekerInFireStore(currentId, REMOTE_LAST_NAME, _lastName.value)
            repository.updateSeekerInFireStore(currentId, REMOTE_ADDRESS, _address.value)
            repository.updateSeekerInFireStore(currentId, REMOTE_PHONE, _phoneNumber.value)

            careSeeker.value!!.firstName = _firstName.value!!
            careSeeker.value!!.lastName = _lastName.value!!
            careSeeker.value!!.phone = _phoneNumber.value!!
            careSeeker.value!!.address = _address.value!!

            repository.updateSeekerInRoom(careSeeker.value!!)
        }
    }
}