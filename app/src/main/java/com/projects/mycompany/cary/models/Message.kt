package com.projects.mycompany.cary.models


import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class Message(
    var text: String = "",
    var receiverId: String = "",
   /* var receiverName: String = "",
    var receiverPhoto: String = "",*/
    var senderId: String = "",
    //var senderName: String = "",
    //var senderPhoto: String = "",
    @ServerTimestamp var dateOfCreation: Date? = null
)