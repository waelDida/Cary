package com.projects.mycompany.cary.models

class GiverFilter(
    var gender: String,
    var experience: String,
    var maxDistance: Int,
    var minAge: Int,
    var maxAge: Int,
    var jobType: String,
    var currentLatitude: Double?,
    var currentLongitude: Double?,
    var careCategory: String
)