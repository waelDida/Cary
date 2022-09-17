package com.projects.mycompany.cary.utils

import com.google.firebase.firestore.DocumentSnapshot
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.models.SeekerFilter


fun filterCareSeekers(list: MutableList<CareSeeker>, filter: SeekerFilter):MutableList<CareSeeker>{
    val filteredList = mutableListOf<CareSeeker>()
    list.forEach {
        val distance = calculateDistance(filter.currentLatitude!!,filter.currentLongitude!!,it.latitude!!,it.longitude!!)
        if(distance.toInt() <= filter.maxDistance)
            filteredList.add(it)
    }
    return filteredList
}

fun convertToCareSeekerList(docList: List<DocumentSnapshot>): MutableList<CareSeeker>{
    val filteredList = mutableListOf<CareSeeker>()
    docList.forEach {
        val careSeeker = it.toObject(CareSeeker::class.java)
        filteredList.add(careSeeker!!)
    }
    return filteredList
}