package com.projects.mycompany.cary.models

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class CareGiver(
    @PrimaryKey @NonNull var uid : String = "",
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var birthdate: String = "",
    var phone: String = "",
    var address: String = "",
    var gender: String = "",
    var price: String = "",
    var experience: String = "",
    var job: String = "",
    var availability: String = "",
    var careCategory: String = "",
    var latitude: Double? = 0.0,
    var longitude: Double? = 0.0,
    var emailVerified: Boolean = false,
    var about: String = "",
    var rating: Double = 0.0,
    var imgUrl: String = "",
    var order: Int = 1,
    var subNotification: Int = 0,

    var chatList: MutableList<String> = mutableListOf(),

    @Ignore
    var registrationTokens: MutableList<String> = mutableListOf(),

    @Ignore
    var notReadMessages: MutableList<String> = mutableListOf() ): Parcelable


