package com.projects.mycompany.cary.caregiver.seekerDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.data.repositories.inboxDataRepository.InboxDataRepository
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.utils.getCurrentUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SeekerDetailVM(private val inboxRepository: InboxDataRepository,
                     private val giverRepo: GiverDataRepository,
                     val careSeeker: CareSeeker): ViewModel() {

    val mCurrentCareGiver = giverRepo.getLocalGiverById(getCurrentUser()!!.uid)

    private val _mCareSeeker = MutableLiveData<CareSeeker>()
    val mCareSeeker: LiveData<CareSeeker>
        get() = _mCareSeeker

    private val _sendMessage = MutableLiveData<CareSeeker?>()
    val sendMessage: LiveData<CareSeeker?>
        get() = _sendMessage

    init {
        _mCareSeeker.value = careSeeker
    }

    fun onSendClick(){
        val senderReceiverList = mutableListOf(getCurrentUser()!!.uid,careSeeker.uid)
        viewModelScope.launch(Dispatchers.IO) {
            inboxRepository.createNewChat(senderReceiverList)
            giverRepo.updateChatList(getCurrentUser()!!.uid,careSeeker.uid)
            withContext(Dispatchers.Main){
                _sendMessage.value = careSeeker
            }
        }
    }
    fun onSendClicked(){
        _sendMessage.value = null
    }
}