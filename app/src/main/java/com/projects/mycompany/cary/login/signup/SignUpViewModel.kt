package com.projects.mycompany.cary.login.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projects.mycompany.cary.utils.*

class SignUpViewModel: ViewModel(){


    private val _infoCheck = MutableLiveData<String?>()
    val infoCheck: LiveData<String?>
        get() = _infoCheck

    private val _signUpAction = MutableLiveData<Boolean>()
    val signUpAction: LiveData<Boolean>
        get() = _signUpAction

    fun checkValidity(firstName: String, lastName: String, email: String, password: String, retypedPss: String){
        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()
            || password.isEmpty() || retypedPss.isEmpty())
            _infoCheck.value = FILL_ALL_FIELDS
        else if(!isPasswordValid(password))
            _infoCheck.value = INVALID_PASSWORD
        else if(password != retypedPss)
            _infoCheck.value = CHECK_PASSWORD
        else if(!isValidEmail(email))
            _infoCheck.value = INVALID_EMAIL
        else
            _infoCheck.value = VALID_INFO
    }
    
    fun signUp(){
        _signUpAction.value = true
    }

    fun signedUp(){
        _signUpAction.value = false
        _infoCheck.value = null
    }
}