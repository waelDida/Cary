package com.projects.mycompany.cary.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review (
    var reviewerName: String = "",
    var reviewerImage: String = "",
    var reviewerRate: Int = 0,
    var reviewerReview: String = "",
    var reviewedId: String = "",
    var reviewerId: String = ""): Parcelable