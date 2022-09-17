package com.projects.mycompany.cary.utils

import com.google.firebase.firestore.DocumentSnapshot
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.GiverFilter

fun filterCareGivers(list: MutableList<CareGiver>, filter: GiverFilter): MutableList<CareGiver>{
    val filteredList = mutableListOf<CareGiver>()
    for(careGiver in list){
        val age = calculateAge(getDateFromString(careGiver.birthdate))
        val distance = calculateDistance(filter.currentLatitude!!,filter.currentLongitude!!,careGiver.latitude!!,careGiver.longitude!!)
        if(careGiver.careCategory == filter.careCategory
            && age >= filter.minAge
            && age <= filter.maxAge
            && distance.toInt() <= filter.maxDistance
        )
            filteredList.add(careGiver)
    }
    return filteredList
}

fun convertToCareGiverList(docList: List<DocumentSnapshot>): MutableList<CareGiver>{
    val filteredList = mutableListOf<CareGiver>()
    for(documentSnapshot in docList){
        val careGiver = documentSnapshot.toObject(CareGiver::class.java)
        filteredList.add(careGiver!!)
    }
    return filteredList
}