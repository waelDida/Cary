package com.projects.mycompany.cary.preference

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.projects.mycompany.cary.utils.PREF_EMAIL_VERIFIED
import com.projects.mycompany.cary.utils.PREF_USER_TYPE

class AppPreferences(app: Application) {

    private val prefName = "appPref"
    private var pref: SharedPreferences

    init {
        pref = app.applicationContext.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    fun setEmailVerifiedToTrue(){
        with(pref.edit()){
            putBoolean(PREF_EMAIL_VERIFIED, true).apply()
        }
    }

    fun initAppPreference(){
        with(pref.edit()){
            putBoolean(PREF_EMAIL_VERIFIED,false)
            putString(PREF_USER_TYPE,"").apply()
        }
    }
    fun isEmailVerified() = pref.getBoolean(PREF_EMAIL_VERIFIED,false)

    fun setCurrentUser(currentUser: String){
        with(pref.edit()){
            putString(PREF_USER_TYPE,currentUser).apply()
        }
    }

    fun getCurrentUserType(): String = pref.getString(PREF_USER_TYPE,"")!!


}