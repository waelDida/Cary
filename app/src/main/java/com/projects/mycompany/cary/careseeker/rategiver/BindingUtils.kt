package com.projects.mycompany.cary.careseeker.rategiver

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.projects.mycompany.cary.models.Review
import com.projects.mycompany.cary.utils.rateList

@BindingAdapter("setRateValue")
fun Spinner.setRate(review: Review ?){
    review?.let {
        setSelection(rateList.indexOf(review.reviewerRate))
    }
}