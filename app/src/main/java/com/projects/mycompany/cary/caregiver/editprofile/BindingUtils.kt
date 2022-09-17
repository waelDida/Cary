package com.projects.mycompany.cary.caregiver.editprofile

import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.utils.MALE
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("setGiverPhotoEdit")
fun setGiverImg(circleImageView: CircleImageView, careGiver: CareGiver?){
    val requestManager = Glide.with(circleImageView.context)
    careGiver?.let {
        if(careGiver.imgUrl.isNotEmpty()){
            val uri = careGiver.imgUrl.toUri().buildUpon().scheme("https").build()
            requestManager
                .load(uri)
                .into(circleImageView)
        }
        else{
            if(careGiver.gender == MALE)
                requestManager.load(R.drawable.male_icon).into(circleImageView)
            else
                requestManager.load(R.drawable.female_icon).into(circleImageView)
        }
    }


}