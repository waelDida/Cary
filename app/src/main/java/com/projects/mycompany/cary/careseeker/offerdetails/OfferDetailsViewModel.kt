package com.projects.mycompany.cary.careseeker.offerdetails


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OfferDetailsViewModel(private val repository: SeekerDataRepository): ViewModel() {

    private val currentSeekerId = getCurrentUser()!!.uid

    private val _mProgressStatus = MutableLiveData<Boolean>()
    val mProgressStatus: LiveData<Boolean>
        get() = _mProgressStatus

    private val _mCurrentCareSeeker = MutableLiveData<CareSeeker>()
    val mCurrentCareSeeker: LiveData<CareSeeker>
        get() = _mCurrentCareSeeker

    private val _validInfo = MutableLiveData<String>()
    val validInfo: LiveData<String>
        get() = _validInfo

    private var mCategory: String = ""
    private var status: Boolean = false

    private val _offerTitle = MutableLiveData<String>()
    val offerTitle: LiveData<String>
        get() = _offerTitle

    private val _offerDescription = MutableLiveData<String>()
    val offerDescription: LiveData<String>
        get() = _offerDescription


    private val _postClick = MutableLiveData<Boolean>()
    val postClick: LiveData<Boolean>
        get() = _postClick

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val test = repository.getSyncSeeker(currentSeekerId)
           withContext(Dispatchers.Main){
               _mCurrentCareSeeker.value = test
               status = test.search
           }
        }
    }

    fun setCategory(category: String){
        mCategory = category
    }

    fun setSearchingStatus(isSwitchChecked: Boolean){
        status = isSwitchChecked
    }

    fun setOfferDetails(title: String, description: String){
        _offerTitle.value = title.trim()
        _offerDescription.value = description.trim()
    }

    fun validate(){
        when{
            _offerTitle.value!!.isEmpty() && _offerDescription.value!!.isEmpty() -> _validInfo.value = MISSING_INFO
            _offerTitle.value!!.isEmpty() -> _validInfo.value = MISSING_OFFER_TITLE
            _offerDescription.value!!.isEmpty() -> _validInfo.value = MISSING_OFFER_DESCRIPTION
            else -> {
                updateFields()
            }
        }
    }

    fun onPostClick(){
        _postClick.value = true
    }

    fun onPostClicked(){
        _postClick.value = false
    }

    private fun updateFields(){
        _mProgressStatus.value = true
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSeekerInFireStore(currentSeekerId, REMOTE_OFFER_TITLE, _offerTitle.value)
            repository.updateSeekerInFireStore(currentSeekerId, REMOTE_OFFER_DESCRIPTION, _offerDescription.value)
            repository.updateSeekerInFireStore(currentSeekerId, REMOTE_CARE_CATEGORY, mCategory)
            repository.updateSeekerInFireStore(currentSeekerId, REMOTE_IS_SEARCHING, status)
            repository.updateSeekerInFireStore(currentSeekerId, REMOTE_JOB_UPDATE_DATE,Timestamp.now().toDate())

            _mCurrentCareSeeker.value!!.offerTitle = _offerTitle.value!!
            _mCurrentCareSeeker.value!!.offerDescription = _offerDescription.value!!
            _mCurrentCareSeeker.value!!.careCategory = mCategory
            _mCurrentCareSeeker.value!!.search = status

            repository.updateSeekerInRoom(_mCurrentCareSeeker.value!!)
            withContext(Dispatchers.Main){
                _mProgressStatus.value = false
                _validInfo.value = VALID_INFO
            }
        }

    }

}