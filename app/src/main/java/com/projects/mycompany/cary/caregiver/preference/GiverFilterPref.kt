package com.projects.mycompany.cary.caregiver.preference

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.projects.mycompany.cary.utils.PREF_DISTANCE

class GiverFilterPref(app: Application) {

    private val prefName = "giver_pref"
    private var pref: SharedPreferences

    init {
        pref = app.applicationContext.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    fun setValue(maxDistance: Int){
        with(pref.edit()){
            putInt(PREF_DISTANCE,maxDistance).apply()
        }
    }

    fun getMaxDistance(): Int{
        return pref.getInt(PREF_DISTANCE,70)
    }
}