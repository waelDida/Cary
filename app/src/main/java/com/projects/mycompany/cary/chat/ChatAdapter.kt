package com.projects.mycompany.cary.chat

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.models.Message


class ChatAdapter(options: FirestoreRecyclerOptions<Message>, private val idCurrentUser: String, private val context: Context) :
    FirestoreRecyclerAdapter<Message, ChatAdapter.MessageViewHolder>(options) {


    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var colorCurrentUser = 0
        private var colorRemoteUser = 0

        //ROOT VIEW
        private val rootView = itemView.findViewById<RelativeLayout>(R.id.activity_mentor_chat_item_root_view)

        //PROFILE CONTAINER
        private val profileContainer = itemView.findViewById<FrameLayout>(R.id.activity_mentor_chat_item_profile_container)

        //MESSAGE CONTAINER
        private val messageContainer = itemView.findViewById<RelativeLayout>(R.id.activity_mentor_chat_item_message_container)

        //TEXT MESSAGE CONTAINER
        private val textMessageContainer = itemView.findViewById<LinearLayout>(R.id.activity_mentor_chat_item_message_container_text_message_container)
        private val textViewMessage = itemView.findViewById<TextView>(R.id.activity_mentor_chat_item_message_container_text_message_container_text_view)

        init {
            colorCurrentUser = ContextCompat.getColor(itemView.context, R.color.currentMessageColor)
            colorRemoteUser = ContextCompat.getColor(itemView.context, R.color.colorPrimaryMessage)
        }

         fun updateWithMessage(message: Message, currentUserId: String, context: Context){
            val isCurrentUser = message.senderId == currentUserId

            // Update message TextView
             textViewMessage.apply {
                this.setTextColor(if(isCurrentUser) ContextCompat.getColor(context,R.color.white)
                    else ContextCompat.getColor(context,R.color.senderTextColor))
                this.text = message.text
                this.textAlignment = if(isCurrentUser) View.TEXT_ALIGNMENT_TEXT_END else View.TEXT_ALIGNMENT_TEXT_START
            }


            // Update Message Bubble Color Background
            (textMessageContainer.background as GradientDrawable).setColor(if(isCurrentUser) colorCurrentUser else colorRemoteUser)

            // Update all views alignment depending is current user or not
            updateDesignDependingUser(isCurrentUser)
        }

        private fun updateDesignDependingUser(isSender: Boolean){
            // PROFILE CONTAINER
            val paramsLayoutHeader = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
            paramsLayoutHeader.addRule(if(isSender) RelativeLayout.ALIGN_PARENT_RIGHT else RelativeLayout.ALIGN_PARENT_LEFT)
            profileContainer.layoutParams = paramsLayoutHeader

            // MESSAGE CONTAINER
            val paramsLayoutContent = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
            paramsLayoutContent.addRule(if(isSender) RelativeLayout.LEFT_OF else RelativeLayout.RIGHT_OF,R.id.activity_mentor_chat_item_profile_container)
            messageContainer.layoutParams = paramsLayoutContent

            rootView.requestLayout()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
       return MessageViewHolder(
           LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
       )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, message: Message) {
        holder.updateWithMessage(message,idCurrentUser,context)
    }
}