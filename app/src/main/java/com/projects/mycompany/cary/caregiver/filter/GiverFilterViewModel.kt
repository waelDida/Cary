package com.projects.mycompany.cary.caregiver.filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.projects.mycompany.cary.caregiver.preference.GiverFilterPref

class GiverFilterViewModel(application: Application): AndroidViewModel(application) {

    private val filterPref = GiverFilterPref(application)

    private val x = 1

    private val _saveClick = MutableLiveData<Boolean>()
    val saveClick: LiveData<Boolean>
        get() = _saveClick

    private val _distance = MutableLiveData<Int>()
    val distance: LiveData<Int>
        get() = _distance

    private val _showPremiums = MutableLiveData<Boolean>()
    val showPremiums: LiveData<Boolean>
        get() = _showPremiums

    init {
        _distance.value = filterPref.getMaxDistance()
        _showPremiums.value = true
    }

    fun setDistance(distance: Int){
        _distance.value = distance + x
    }

    fun setCheckStatus(isChecked: Boolean){
        _showPremiums.value = isChecked
    }

    fun onSaveClick(){
        _saveClick.value = true
        filterPref.setValue(_distance.value!!)
    }

    fun onSaveClicked(){
        _saveClick.value = false
    }
}