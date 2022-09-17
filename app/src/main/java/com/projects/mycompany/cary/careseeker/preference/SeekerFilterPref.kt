package com.projects.mycompany.cary.careseeker.preference

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.projects.mycompany.cary.utils.*

class SeekerFilterPref(app: Application) {

    private val prefName = "seeker_pref"
    private var pref: SharedPreferences

    init {
        pref = app.applicationContext.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    fun setValues(maleChecked: Boolean, femaleChecked: Boolean, experience: String, maxDistance: Int, minAge: Int,
                  maxAge: Int, fullChecked: Boolean, partChecked: Boolean){
        with(pref.edit()){
            putBoolean(PREF_GENDER_MALE,maleChecked)
            putBoolean(PREF_GENDER_FEMALE,femaleChecked)
            putString(PREF_EXPERIENCE,experience)
            putInt(PREF_DISTANCE,maxDistance)
            putInt(PREF_MIN_AGE,minAge)
            putInt(PREF_MAX_AGE,maxAge)
            putBoolean(PREF_JOB_FULL,fullChecked)
            putBoolean(PREF_JOB_PART,partChecked)
                .apply()
        }
    }

    fun isMaleChecked(): Boolean{
        return pref.getBoolean(PREF_GENDER_MALE, true)
    }

    fun isFemaleChecked(): Boolean{
        return pref.getBoolean(PREF_GENDER_FEMALE, true)
    }

    fun getExperience(): String?{
        return pref.getString(PREF_EXPERIENCE, experienceForFilter[0])
    }

    fun getMaxDistance(): Int{
        return pref.getInt(PREF_DISTANCE,70)
    }

    fun getMinAge(): Int{
        return pref.getInt(PREF_MIN_AGE,25)
    }

    fun getMaxAge(): Int{
        return pref.getInt(PREF_MAX_AGE,50)
    }
    fun isFullTimeChecked(): Boolean{
        return pref.getBoolean(PREF_JOB_FULL, true)
    }
    fun isPartTimeChecked(): Boolean{
        return pref.getBoolean(PREF_JOB_PART, true)
    }
}