package com.projects.mycompany.cary.careseeker.search



import android.widget.RatingBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.utils.MALE
import com.projects.mycompany.cary.utils.formatName
import de.hdodenhof.circleimageview.CircleImageView

@ExperimentalStdlibApi
@BindingAdapter("nameBinding")
fun TextView.setNameBinding(item: CareGiver){
    item.let {
        text = formatName(item.firstName,item.lastName)
    }
}

@BindingAdapter("imageBinding")
fun setImage(imageView: CircleImageView, careGiver: CareGiver?){
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

@BindingAdapter("rateBinding")
fun RatingBar.setRateBinding(item: CareGiver){
    item.let {
        rating = item.rating.toFloat()
    }
}

@BindingAdapter("priceFormatted")
fun TextView.setPriceFormatted(item: CareGiver){
    item.let {
        text = item.price
    }
}

@BindingAdapter("experienceFormatted")
fun TextView.setExperienceFormatted(item: CareGiver){
    item.let {
         text = item.experience
    }
}

