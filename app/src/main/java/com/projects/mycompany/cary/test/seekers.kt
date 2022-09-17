package com.projects.mycompany.cary.test

import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.utils.TUTOR

val currentSeeker = CareSeeker("1","Wael","Saadaoui","wael@hotmail.fr","96539246",
    "Zayatine street 1240 Feriana", TUTOR, 34.9399,8.5666,true,"https://firebasestorage.googleapis.com/v0/b/cary-a69c0.appspot.com/o/male_test1.jpeg?alt=media&token=b68aa57c-227c-42a4-90cf-ddaca6dcf05b",true,"offer1","descr1")

val seeker1 = CareSeeker("2", careCategory = TUTOR,latitude = 34.9399,longitude = 8.5666)
val seeker2 = CareSeeker("3",careCategory = TUTOR,latitude = 34.9399,longitude = 8.5666)
val seeker4 = CareSeeker("4",careCategory = TUTOR,latitude = 34.9399,longitude = 8.5666)