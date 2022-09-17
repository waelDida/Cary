package com.projects.mycompany.cary.careseeker.profile

import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.utils.formatName
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("seekerImg")
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
@BindingAdapter("seekerName")
fun TextView.setSeekerName(careSeeker: CareSeeker?){
    careSeeker?.let {
        text = formatName(careSeeker.firstName, careSeeker.lastName)
    }
}