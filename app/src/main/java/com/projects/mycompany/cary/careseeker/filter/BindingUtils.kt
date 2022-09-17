package com.projects.mycompany.cary.careseeker.filter

import android.widget.Spinner
import androidx.databinding.BindingAdapter

@BindingAdapter("setMinAge")
fun setMinAgePosition(spinner :Spinner, index : Int){
    spinner.setSelection(index)
}

@BindingAdapter("setMaxAge")
fun Spinner.setMaxAgePosition(index: Int){
    this.setSelection(index)
}

@BindingAdapter("setExperience")
fun Spinner.setExp(index: Int){
    this.setSelection(index)
}