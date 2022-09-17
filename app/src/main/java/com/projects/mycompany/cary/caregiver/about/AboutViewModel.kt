package com.projects.mycompany.cary.caregiver.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.utils.MISSING_INFO
import com.projects.mycompany.cary.utils.REMOTE_ABOUT
import com.projects.mycompany.cary.utils.VALID_INFO
import com.projects.mycompany.cary.utils.getCurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AboutViewModel(private val repository: GiverDataRepository): ViewModel() {

    val currentCareGiver = repository.getLocalGiverById(getCurrentUser()!!.uid)

    private val _saveClick = MutableLiveData<Boolean>()
    val saveClick: LiveData<Boolean>
        get() = _saveClick


    private val _aboutInfo = MutableLiveData<String>()
    val aboutInfo: LiveData<String>
        get() = _aboutInfo

    private val _validInfo = MutableLiveData<String>()
    val validInfo: LiveData<String>
        get() = _validInfo


    fun setInfo(aboutText: String){
        _aboutInfo.value = aboutText.trim()
    }

    fun checkAndValidate(){
        if(_aboutInfo.value!!.isEmpty())
            _validInfo.value = MISSING_INFO
        else{
            _validInfo.value = VALID_INFO
            updateCareGiver()
        }
    }

    fun onSaveClick(){
        _saveClick.value = true
    }

    fun onSaveClicked(){
        _saveClick.value = false
    }

    private fun updateCareGiver(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateGiverInFireStore(getCurrentUser()!!.uid, REMOTE_ABOUT,_aboutInfo.value)
            currentCareGiver.value!!.about = _aboutInfo.value!!
            repository.updateGiverInRoom(currentCareGiver.value!!)
        }
    }

}