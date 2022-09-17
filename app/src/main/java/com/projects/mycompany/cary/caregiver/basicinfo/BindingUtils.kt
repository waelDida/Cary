package com.projects.mycompany.cary.caregiver.basicinfo


import android.widget.RadioButton
import androidx.databinding.BindingAdapter
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.utils.FEMALE
import com.projects.mycompany.cary.utils.MALE

@BindingAdapter("maleCheckStatus")
fun RadioButton.setMaleCheck(careGiver: CareGiver?){
    careGiver?.let {
        this.isChecked= careGiver.gender == MALE
    }

}

@BindingAdapter("femaleCheckStatus")
fun RadioButton.setFemaleCheck(careGiver: CareGiver?){
    careGiver?.let {
        this.isChecked = careGiver.gender == FEMALE
    }


}