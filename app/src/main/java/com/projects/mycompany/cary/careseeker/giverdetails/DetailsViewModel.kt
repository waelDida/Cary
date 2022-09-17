package com.projects.mycompany.cary.careseeker.giverdetails



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.mycompany.cary.data.repositories.inboxDataRepository.InboxDataRepository
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.utils.getCurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(private val inboxRepository: InboxDataRepository,
                       private val seekerRepository: SeekerDataRepository,
                       val careGiver: CareGiver): ViewModel() {

    val currentCareSeeker = seekerRepository.getLocalSeekerById(getCurrentUser()!!.uid)



    private val _mCareGiver = MutableLiveData<CareGiver>()
    val mCareGiver: LiveData<CareGiver>
        get() = _mCareGiver

    private val _aboutClick = MutableLiveData<String?>()
    val aboutClick: LiveData<String?>
        get() = _aboutClick

    private val _reviewsClick = MutableLiveData<String?>()
    val reviewsClick: LiveData<String?>
        get() = _reviewsClick

    private val _rateClick = MutableLiveData<String?>()
    val rateClick: LiveData<String?>
        get() = _rateClick

    private val _sendMessage = MutableLiveData<CareGiver?>()
    val sendMessage: LiveData<CareGiver?>
        get() = _sendMessage

    init {
        _mCareGiver.value = careGiver
    }

    fun onAboutClick(){
        _aboutClick.value = _mCareGiver.value!!.about
    }

    fun onAboutClicked(){
        _aboutClick.value = null
    }

    fun onReviewsClick(){
        _reviewsClick.value = careGiver.uid
    }

    fun onReviewsClicked(){
        _reviewsClick.value = null
    }

    fun onRateClick(){
        _rateClick.value = careGiver.uid
    }

    fun onRateClicked(){
        _rateClick.value = null
    }

    fun onSendClick(){
        val senderReceiverList = mutableListOf(getCurrentUser()!!.uid,careGiver.uid)
        viewModelScope.launch(Dispatchers.IO) {
            inboxRepository.createNewChat(senderReceiverList)
            seekerRepository.updateChatList(getCurrentUser()!!.uid,careGiver.uid)
            withContext(Dispatchers.Main){
                _sendMessage.value = careGiver
            }
        }
    }

    fun onSendClicked(){
        _sendMessage.value = null
    }
}