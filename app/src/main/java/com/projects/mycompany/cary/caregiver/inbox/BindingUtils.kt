package com.projects.mycompany.cary.caregiver.inbox

import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.projects.mycompany.cary.models.CareSeeker
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("ImageBindingSeeker")
fun setPhotoSeeker(img: CircleImageView, url: String?){
    url?.let {
        val uri = url.toUri().buildUpon().scheme("https").build()
        Glide.with(img.context)
            .load(uri)
            .into(img)
    }

}