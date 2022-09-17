package com.projects.mycompany.cary.careseeker.offerdetails

import android.widget.Spinner
import android.widget.Switch
import androidx.databinding.BindingAdapter
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.utils.categoriesList


@BindingAdapter("jobDetails")
fun Spinner.setJobDetail(careSeeker: CareSeeker?){
    careSeeker?.let {
        setSelection(categoriesList.indexOf(careSeeker.careCategory))
    }

}
@BindingAdapter("offerStatus")
fun Switch.setCheckStatus(careSeeker: CareSeeker?){
    careSeeker?.let {
        isChecked = careSeeker.search
    }

}