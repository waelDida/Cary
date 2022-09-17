package com.projects.mycompany.cary.test

import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.utils.*


val currentGiver = CareGiver("1","Wael","Dida",
    "dida@hotmail.fr","01/22/1988","96532585",
    "Street A - 1000 Paris",MALE,"$10-15/h", YRS_EXP_3, FULL_TIME,"yes",
    TUTOR,34.9399,8.5666,true,"It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less",1.0,"https://firebasestorage.googleapis.com/v0/b/cary-a69c0.appspot.com/o/male_test1.jpeg?alt=media&token=b68aa57c-227c-42a4-90cf-ddaca6dcf05b")


// age = 32 distance = 1km
val giver1 = CareGiver("1","Wael","Sanchez",
    "sanchez@hotmail.fr","01/22/1988","96532585",
    "Street A - 1000 Paris",MALE,"$10-15/h", YRS_EXP_3, FULL_TIME,"yes",
    TUTOR,34.9399,8.5666,true,
    "test",
    1.0,"https://firebasestorage.googleapis.com/v0/b/cary-a69c0.appspot.com/o/male_test1.jpeg?alt=media&token=b68aa57c-227c-42a4-90cf-ddaca6dcf05b")

// age = 27 distance = 35km
val giver2 = CareGiver("2","Ferdinand","Oliver",
    "wael_saadaoui@hotmail.fr","01/22/1993","96532585",
    "Street B - 4001 London",MALE,"$15-35/h", YRS_EXP_3, FULL_TIME,"yes",
    TUTOR,35.1723,8.8308,true,
    "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less",3.0,"https://firebasestorage.googleapis.com/v0/b/cary-a69c0.appspot.com/o/male_test2.jpg?alt=media&token=0f4b857d-24bb-4c9d-921c-ca1d121dfde7"
)

//age = 40 distance = 250km
val giver3 = CareGiver("3","Bertrand","Marchand",
    "sanchez@hotmail.fr","01/22/1980","96532585",
    "Street C - 5001 New York","male","$40-45/h","2 yrs exp","partTime","yes",
    TUTOR,36.8065,10.1815,true,"It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less",5.0,"https://firebasestorage.googleapis.com/v0/b/cary-a69c0.appspot.com/o/male_test3.jpg?alt=media&token=22e0ad98-0b8c-4ac9-a020-3551dd4fa4c7")

// age = 24 distance 250km
val giver4 = CareGiver("4","Lucy","Fox",
    "sanchez@hotmail.fr","01/22/1996","96532585",
    "Street D - 5001 Lyon","female","$40-45/h","4 yrs exp","partTime","yes",
    TUTOR,36.8065,10.1815,true,"Test3",5.0,"")

// age = 50 distance = 70km
val giver5 = CareGiver("5","Emili","Hernadez",
    "sanchez@hotmail.fr","01/22/1970","96532585",
    "Street C - 5001 New York","female","$40-45/h","4 yrs exp","partTime","yes",
    TUTOR,34.4311,8.7757,true,"Test3",5.0,"")


// age = 60 distance = 250km
val giver6 = CareGiver("6","Rose","Bett",
    "sanchez@hotmail.fr","01/22/1960","96532585",
    "Street C - 5001 New York", FEMALE,"$40-45/h", YRS_EXP_4, PART_TIME,"yes",
    "Pet Care",36.8065,10.1815,true,"Test3",5.0,"")