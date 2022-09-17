package com.projects.mycompany.cary.caregiver.job

import android.widget.RadioButton
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.utils.*

@BindingAdapter("setFullTimeCheck")
fun RadioButton.setFullTime(careGiver: CareGiver?){
    careGiver?.let {
        isChecked = careGiver.job == FULL_TIME
    }

}

@BindingAdapter("setPartTimeCheck")
fun RadioButton.setPartTime(careGiver: CareGiver?){
    careGiver?.let {
        isChecked = careGiver.job == PART_TIME
    }

}

@BindingAdapter("setExperienceJob")
fun Spinner.setExperience(careGiver: CareGiver?){
    careGiver?.let {
        setSelection(experience.indexOf(careGiver.experience))
    }

}

@BindingAdapter("setAvailabilityYes")
fun RadioButton.setYesChecked(careGiver: CareGiver?){
    careGiver?.let {
        isChecked = careGiver.availability == YES
    }

}

@BindingAdapter("setAvailabilityNo")
fun RadioButton.setNoChecked(careGiver: CareGiver?){
    careGiver?.let {
        isChecked = careGiver.availability == NO
    }
}