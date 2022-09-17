package com.projects.mycompany.cary.careseeker.inbox


import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.utils.MALE
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("ImageBindingGiver")
fun setImage(img: CircleImageView, careGiver: CareGiver ?){
    val requestManager = Glide.with(img.context)
    careGiver?.let {
        if(careGiver.imgUrl.isNotEmpty()){
            val uri = careGiver.imgUrl.toUri().buildUpon().scheme("https").build()
            requestManager
                .load(uri)
                .into(img)
        }
        else{
            if(careGiver.gender == MALE)
                requestManager.load(R.drawable.male_icon).into(img)
            else
                requestManager.load(R.drawable.female_icon).into(img)
        }
    }
}
