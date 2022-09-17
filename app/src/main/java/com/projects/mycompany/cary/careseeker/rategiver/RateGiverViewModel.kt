package com.projects.mycompany.cary.careseeker.rategiver

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.mycompany.cary.data.repositories.reviewsDataRepository.ReviewsDataRepository
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.models.Review
import com.projects.mycompany.cary.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RateGiverViewModel(private val reviewedId: String, seekerRepository: SeekerDataRepository, val repository: ReviewsDataRepository): ViewModel() {

    private var x : Pair<MutableList<Review>,String> ? = null
    private lateinit var currentReview: Review
    private var currentIndex: String ? = null
    private var rate = 1

    private lateinit var currentSeeker: CareSeeker

    private val _mCurrentReview = MutableLiveData<Review>()
    val mCurrentReview: LiveData<Review>
        get() = _mCurrentReview
    
    private val _validInfo = MutableLiveData<String>()
    val validInfo: LiveData<String>
        get() = _validInfo

    private val _post = MutableLiveData<Boolean>()
    val post: LiveData<Boolean>
        get() = _post

    private val _review = MutableLiveData<String>()
    val review: LiveData<String>
        get() = _review

    init {
        viewModelScope.launch(Dispatchers.IO) {
            currentSeeker = seekerRepository.getSyncSeeker(getCurrentUser()!!.uid)
            x = repository.getReviewsFromFireStore(reviewedId, getCurrentUser()!!.uid)
            x?.let {
                if(x!!.first.isNotEmpty()){
                    currentReview = x!!.first[0]
                    currentIndex = x!!.second
                    withContext(Dispatchers.Main){
                        _mCurrentReview.value = currentReview
                    }
                }
            }
        }
    }

    fun setReviewText(reviewText: String){
        _review.value = reviewText.trim()
    }

    fun checkPostReview(){
        if(_review.value!!.isEmpty())
            _validInfo.value = MISSING_INFO
        else{
            createOrUpdateReview()
            _validInfo.value = VALID_INFO
        }

    }

    fun onPostClick(){
        _post.value = true

    }

    fun onPostClicked(){
        _post.value = false
    }

    fun setRate(value: Int){
        rate = value
    }

    private fun createOrUpdateReview(){
        viewModelScope.launch(Dispatchers.IO) {
            if(currentIndex != null){
                repository.updateReview(currentIndex!!, REMOTE_REVIEWER_REVIEW,_review.value!!)
                repository.updateReview(currentIndex!!, REMOTE_REVIEWER_RATE, rate)
            }
            else{
                repository.createNewReview(
                    Review(
                        currentSeeker.firstName+" "+currentSeeker.lastName,
                        currentSeeker.imgUrl,
                        rate,
                        _review.value!!,
                        reviewedId,
                        currentSeeker.uid))
            }
        }

    }
}