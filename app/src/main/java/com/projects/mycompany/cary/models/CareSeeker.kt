package com.projects.mycompany.cary.models

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
data class CareSeeker(
        @PrimaryKey @NonNull var uid: String = "",
        var firstName: String = "",
        var lastName: String = "",
        var email: String = "",
        var phone: String = "",
        var address: String = "",
        var careCategory: String = "",
        var latitude: Double? = 0.0,
        var longitude: Double? = 0.0,
        var emailVerified: Boolean = false,
        var imgUrl: String = "",
        var search: Boolean = false,
        var offerTitle: String = "",
        var offerDescription: String = "",
        var order: Int = 1,
        var subNotification: Int = 0,

        var chatList: MutableList<String> = mutableListOf(),


        @ServerTimestamp var jobDetailsUpdateDate: Date? = null,

        @Ignore
        var registrationTokens: MutableList<String> = mutableListOf(),

        @Ignore
        var notReadMessages: MutableList<String> = mutableListOf()): Parcelable