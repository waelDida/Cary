package com.projects.mycompany.cary.careseeker.filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.projects.mycompany.cary.careseeker.preference.SeekerFilterPref
import com.projects.mycompany.cary.utils.*

class SeekerFilterViewModel(application: Application): AndroidViewModel(application) {

    private val x = 1

    private var filterPref: SeekerFilterPref = SeekerFilterPref(application)

    private val _exp = MutableLiveData<String>()
    private val _maxAge = MutableLiveData<Int>()
    private val _minAge = MutableLiveData<Int>()

    private val _expIndex = MutableLiveData<Int>()
    val expIndex: LiveData<Int>
        get() = _expIndex

    private val _minAgeIndex = MutableLiveData<Int>()
    val minAgeIndex: LiveData<Int>
        get() = _minAgeIndex

    private val _maxAgeIndex = MutableLiveData<Int>()
    val maxAgeIndex: LiveData<Int>
        get() = _maxAgeIndex

    private val _maxDistance = MutableLiveData<Int>()
    val maxDistance: LiveData<Int>
        get() = _maxDistance

    private val _maleChecked = MutableLiveData<Boolean>()
    val maleChecked: LiveData<Boolean>
        get() = _maleChecked

    private val _femaleChecked = MutableLiveData<Boolean>()
    val femaleChecked: LiveData<Boolean>
        get() = _femaleChecked

    private val _fullTimeChecked = MutableLiveData<Boolean>()
    val fullTimeChecked: LiveData<Boolean>
        get() = _fullTimeChecked

    private val _partTimeChecked = MutableLiveData<Boolean>()
    val partTimeChecked: LiveData<Boolean>
        get() = _partTimeChecked

    private val _validInfo = MutableLiveData<String>()
    val validInfo: LiveData<String>
        get() = _validInfo

    init {
        _maxDistance.value = filterPref.getMaxDistance()
        _maleChecked.value = filterPref.isMaleChecked()
        _femaleChecked.value = filterPref.isFemaleChecked()
        _partTimeChecked.value = filterPref.isPartTimeChecked()
        _fullTimeChecked.value = filterPref.isFullTimeChecked()
        _expIndex.value = experienceForFilter.indexOf(filterPref.getExperience())
        _minAgeIndex.value = ages.indexOf(filterPref.getMinAge())
        _maxAgeIndex.value = ages.indexOf(filterPref.getMaxAge())
    }


    fun checkThenSave(){
        when{
            _maleChecked.value == false && _femaleChecked.value == false -> _validInfo.value = MISSING_GENDER
            _fullTimeChecked.value == false && _partTimeChecked.value == false -> _validInfo.value = MISSING_JOB_TYPE
            _maxAge.value!! <= _minAge.value!! -> _validInfo.value = INVALID_AGE_RANGE
            else -> {
                saveFilterValues()
                _validInfo.value = VALID_INFO
            }
        }
    }

    private fun saveFilterValues(){
        filterPref.setValues(_maleChecked.value!!,_femaleChecked.value!!,_exp.value!!,_maxDistance.value!!,
            _minAge.value!!,_maxAge.value!!,_fullTimeChecked.value!!,_partTimeChecked.value!!)
    }

    fun maleCheckboxClicked(){
        _maleChecked.value = _maleChecked.value != true
    }

    fun femaleCheckboxClicked(){
        _femaleChecked.value = _femaleChecked.value != true
    }

    fun partTimeCheckBoxClicked(){
        _partTimeChecked.value = _partTimeChecked.value != true
    }

    fun fullTimeCheckBoxClicked(){
        _fullTimeChecked.value = _fullTimeChecked.value != true
    }

    fun changeMaxDistance(i: Int){
        _maxDistance.value = x + i
    }

    fun setMaleCheckbox(isChecked: Boolean){
        _maleChecked.value = isChecked
    }

    fun setFemaleCheckbox(isChecked: Boolean){
        _femaleChecked.value = isChecked
    }

    fun setFullTypeCheckbox(isChecked: Boolean){
        _fullTimeChecked.value = isChecked
    }

    fun setPartTypeCheckbox(isChecked: Boolean){
        _partTimeChecked.value = isChecked
    }

    fun setExperience(experience: String){
        _exp.value = experience
    }

    fun setMinAge(age: Int){
        _minAge.value = age
    }

    fun setMaxAge(age: Int){
        _maxAge.value = age
    }
}