package com.projects.mycompany.cary.caregiver.seekerDetail

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.utils.calculateDistance
import com.projects.mycompany.cary.utils.formatName
import com.projects.mycompany.cary.utils.getCurrentUser
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("seekerImgDetail")
fun setSeekerImg(circleImageView: CircleImageView, url: String?){
    url?.let {
        val imgUri = url.toUri().buildUpon().scheme("https").build()
        Glide.with(circleImageView.context)
            .load(imgUri)
            .apply(RequestOptions().error(R.drawable.unknown_user))
            .into(circleImageView)
    }
}

@ExperimentalStdlibApi
@BindingAdapter("seekerNameDetail")
fun TextView.setSeekerName(careSeeker: CareSeeker?){
    let {
        text = formatName(careSeeker!!.firstName, careSeeker.lastName)
    }
}

@BindingAdapter("messageBtnVisibilitySeeker")
fun Button.setVisibility(careSeeker: CareSeeker){
    let {
        visibility = if(careSeeker.chatList.contains(getCurrentUser()!!.uid)){
            View.GONE
        }else{
            View.VISIBLE
        }
    }
}

@BindingAdapter("mCareGiverLocation","mCareSeekerLocation")
fun TextView.setGiverDistance(careGiver: CareGiver?, careSeeker: CareSeeker?){
    careGiver?.let {
        careSeeker?.let {
            val distance = calculateDistance(careGiver.latitude!!,careGiver.longitude!!,careSeeker.latitude!!,careSeeker.longitude!!)
            text = if(distance < 1) resources.getString(R.string.less_than_one)
            else resources.getString(R.string.formatted_distance, distance.toInt())
        }

    }
}