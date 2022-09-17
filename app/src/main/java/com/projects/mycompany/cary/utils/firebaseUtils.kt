package com.projects.mycompany.cary.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage


fun getCurrentUser(): FirebaseUser? = FirebaseAuth.getInstance().currentUser

fun isCurrentUserLogged() = getCurrentUser() != null

fun getStorageReference() =  FirebaseStorage.getInstance().reference

fun getDetailedStorageReference(url: String) = FirebaseStorage.getInstance().getReferenceFromUrl(url)


