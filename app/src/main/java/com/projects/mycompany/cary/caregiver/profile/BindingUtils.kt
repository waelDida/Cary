package com.projects.mycompany.cary.caregiver.profile

import android.widget.RatingBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.utils.*
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("setGiverImage")
fun bindImage(imageView: CircleImageView, careGiver: CareGiver?){
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

@ExperimentalStdlibApi
@BindingAdapter("setGiverName")
fun TextView.setGiverName(careGiver: CareGiver?){
    careGiver?.let {
        text = formatName(it.firstName,it.lastName)
    }
}

@BindingAdapter("setGiverRating")
fun RatingBar.setGiverRating(careGiver: CareGiver?){
    careGiver?.let {
        rating = careGiver.rating.toFloat()
    }
}

@BindingAdapter("setGiverAge")
fun TextView.setGiverAge (careGiver: CareGiver?){
    careGiver?.let {
        text = resources.getString(
            R.string.formatted_age,
            calculateAge(getDateFromString(careGiver.birthdate))
        )
    }
}

@BindingAdapter("setJobType")
fun TextView.setJobType(careGiver: CareGiver?){
    careGiver?.let {
        text = formatJobType(careGiver.job)
    }
}

@BindingAdapter("setJobTitle")
fun TextView.setJobTitle(careGiver: CareGiver?){
    careGiver?.let {
        text = when(careGiver.careCategory){
            CHILD_CARE -> resources.getString(R.string.baby_sitter)
            PET_CARE -> resources.getString(R.string.pet_sitter)
            HOUSE_KEEPING -> resources.getString(R.string.house_keeper)
            SENIOR_CARE -> resources.getString(R.string.senior_care)
            SPECIAL_NEEDS -> resources.getString(R.string.special_need_caregiver)
            else -> resources.getString(R.string.tutor)
        }
    }
}