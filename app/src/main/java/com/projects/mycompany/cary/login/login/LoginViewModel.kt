package com.projects.mycompany.cary.login.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projects.mycompany.cary.utils.*

class LoginViewModel : ViewModel() {

    private val _cred = MutableLiveData<String>()
    val cred: LiveData<String>
        get() = _cred

    private val _login = MutableLiveData<Boolean>()
    val login: LiveData<Boolean>
        get() = _login

    private val _signUp = MutableLiveData<Boolean>()
    val signUp: LiveData<Boolean>
        get() = _signUp

    private val _forgetPassword = MutableLiveData<Boolean>()
    val forgetPassword: LiveData<Boolean>
        get() = _forgetPassword

    fun checkValidity(email: String, password: String){
        if(email.isEmpty() || password.isEmpty())
            _cred.value = EMPTY_EMAIL_PASSWORD
        else if(!isValidEmail(email))
            _cred.value = INVALID_EMAIL
        else if(!isPasswordValid(password))
            _cred.value = INVALID_PASSWORD
        else
            _cred.value = VALID_EMAIL_PASSWORD
    }
    fun login(){
        _login.value = true
    }

    //for test, to be removed later
    fun loggedIn(){
        _login.value = false
    }

    fun signUp(){
        _signUp.value = true
    }

    fun signedUp(){
        _signUp.value = false
        _cred.value = ""
    }

    fun clickOnForgotPassword(){
        _forgetPassword.value = true
    }

    fun onForgotPasswordClicked(){
        _forgetPassword.value = false
    }
}