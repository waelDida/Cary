package com.projects.mycompany.cary.reviews

import android.widget.RatingBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.projects.mycompany.cary.models.Review
import de.hdodenhof.circleimageview.CircleImageView


@BindingAdapter("reviewerImage")
fun setReviewerImage(circleImageView: CircleImageView, imgUrl: String){
    imgUrl.let {
        Glide.with(circleImageView.context)
            .load(imgUrl)
            .into(circleImageView)
    }
}

@BindingAdapter("reviewerReview")
fun RatingBar.setRate(review: Review){
    review.let {
        rating = review.reviewerRate.toFloat()
    }
}