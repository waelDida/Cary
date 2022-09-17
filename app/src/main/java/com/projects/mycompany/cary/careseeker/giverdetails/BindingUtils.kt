package com.projects.mycompany.cary.careseeker.giverdetails

import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.utils.*
import de.hdodenhof.circleimageview.CircleImageView


@BindingAdapter("giverImage")
fun bindImage(imageView:CircleImageView,careGiver: CareGiver?){
    val requestManager = Glide.with(imageView.context)
    careGiver?.let {
        if(careGiver.imgUrl.isNotEmpty()){
            val uri = careGiver.imgUrl.toUri().buildUpon().scheme("https").build()
            requestManager
                .load(uri)
                .into(imageView)
        }
        else{
            if(careGiver.gender == MALE)
                requestManager.load(R.drawable.male_icon).into(imageView)
            else
                requestManager.load(R.drawable.female_icon).into(imageView)
        }
    }
}

@BindingAdapter("seekerLocation","giverLocation")
fun TextView.setGiverDistance(careSeeker: CareSeeker ?,careGiver: CareGiver ?){
   careGiver?.let {
       careSeeker?.let {
           val distance = calculateDistance(careSeeker.latitude!!,careGiver.longitude!!,careGiver.latitude!!,careGiver.longitude!!)
           text = if(distance < 1) resources.getString(R.string.less_than_one)
           else resources.getString(R.string.formatted_distance, distance.toInt())
       }
    }
}
@ExperimentalStdlibApi
@BindingAdapter("giverName")
fun TextView.setGiverName(careGiver: CareGiver){
    careGiver.let {
        text = formatName(it.firstName,it.lastName)
    }
}

@BindingAdapter("giverRating")
fun RatingBar.setGiverRating(careGiver: CareGiver){
    careGiver.let {
        rating = careGiver.rating.toFloat()
    }
}

@BindingAdapter("giverAge")
fun TextView.setGiverAge (careGiver: CareGiver){
    careGiver.let {
        text = resources.getString(R.string.formatted_age,calculateAge(getDateFromString(careGiver.birthdate)))
    }
}

@BindingAdapter("jobType")
fun TextView.setJobType(careGiver: CareGiver){
    careGiver.let {
        text = formatJobType(careGiver.job)
    }
}

@BindingAdapter("messageBtnVisibility")
fun Button.setVisibility(careGiver: CareGiver){
    let {
        visibility = if(careGiver.chatList.contains(getCurrentUser()!!.uid))
            View.GONE
        else
            View.VISIBLE
    }
}

@BindingAdapter("rateVisibilityAndFormat")
fun TextView.setVisibilityAndFormat(careGiver: CareGiver){
    let {
        text = when(careGiver.careCategory){
            CHILD_CARE -> resources.getString(R.string.rate_child_sitter)
            PET_CARE -> resources.getString(R.string.rate_pet_sitter)
            HOUSE_KEEPING -> resources.getString(R.string.rate_house_keeping)
            SENIOR_CARE -> resources.getString(R.string.rate_senior_sitter)
            SPECIAL_NEEDS -> resources.getString(R.string.rate_special_needs)
            else -> resources.getString(R.string.rate_tutor)
        }
        visibility = if(careGiver.chatList.contains(getCurrentUser()!!.uid))
            View.VISIBLE
        else
            View.GONE
    }
}
