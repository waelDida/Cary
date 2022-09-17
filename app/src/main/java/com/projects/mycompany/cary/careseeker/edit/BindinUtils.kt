package com.projects.mycompany.cary.careseeker.edit

import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.projects.mycompany.cary.R
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("setCareSeekerPhotoEdit")
fun setCareSeekerPhoto(imgView: CircleImageView, url: String?){
    url?.let {
        val uri = url.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(uri)
            .apply(RequestOptions().error(R.drawable.unknown_user))
            .into(imgView)
    }

}