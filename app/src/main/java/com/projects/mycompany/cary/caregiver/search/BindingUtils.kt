package com.projects.mycompany.cary.caregiver.search


import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.utils.timeAgo
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


@BindingAdapter("setSeekerImage")
fun setSeekerImg(circleImageView: CircleImageView, imgUrl: String?){
    imgUrl?.let {
        val uri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(circleImageView.context)
            .load(uri)
            .into(circleImageView)
    }
}

@BindingAdapter("setTitleName")
fun TextView.setTitle(careSeeker: CareSeeker){
    let {
        text = careSeeker.offerTitle
    }
}

@BindingAdapter("setPostDate")
fun TextView.setPostDate(date: Date?){
    date?.let {
        text = timeAgo(date)
    }
}


