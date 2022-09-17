package com.projects.mycompany.cary.chat


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.api.getAllMessagesForChat
import com.projects.mycompany.cary.data.repositories.inboxDataRepository.InboxDataRepository
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import com.projects.mycompany.cary.databinding.FragmentSeekerChatBinding
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.Chat
import com.projects.mycompany.cary.models.Message
import com.projects.mycompany.cary.utils.getCurrentUser
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SeekerChat : Fragment() {

    private lateinit var binding: FragmentSeekerChatBinding
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var inboxRepo: InboxDataRepository
    private lateinit var careSeekerRepo: SeekerDataRepository
    private lateinit var chatId: String
    private lateinit var careGiver: CareGiver
    private lateinit var message: Message

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
         inflater.inflate(R.layout.fragment_seeker_chat, container, false)
        binding = FragmentSeekerChatBinding.inflate(inflater)
        inboxRepo = (requireContext().applicationContext as ToDoApplication).inboxDataRepository
        careSeekerRepo = (requireContext().applicationContext as ToDoApplication).seekerDataRepository

        careGiver = SeekerChatArgs.fromBundle(requireArguments()).careGiver
        getChatRoomIdAndInitChats(getCurrentUser()!!.uid,careGiver.uid)

        binding.sendMessageImage.setOnClickListener {
            val messageText = binding.sendMessageEdit.text.toString()
            message = Message(messageText.trim(), careGiver.uid, getCurrentUser()!!.uid)
            if(messageText.isNotEmpty()){
                GlobalScope.launch(Dispatchers.IO) {
                    inboxRepo.createNewMessage(chatId,message)
                }
                binding.sendMessageEdit.setText("")
            }
        }

        // SetUp the toolbar
        val toolbar = binding.includedToolbar
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        val userName = toolbar.findViewById<TextView>(R.id.toolbar_user_name)
        val userPhoto = toolbar.findViewById<CircleImageView>(R.id.toolbar_user_photo)
        val returnBack = toolbar.findViewById<ImageView>(R.id.back_arrow)
        careGiver.let {
            val uri = careGiver.imgUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(userPhoto.context)
                .load(uri)
                .error(R.drawable.unknown_user)
                .into(userPhoto)
        }
        userName.text = careGiver.firstName
        userPhoto.setOnClickListener {
            findNavController().navigate(SeekerChatDirections.actionSeekerChatToGiverDetails(careGiver))
        }
        returnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        return binding.root
    }

    private fun getChatRoomIdAndInitChats(uid: String, fid: String){
        GlobalScope.launch(Dispatchers.IO) {
            val chatList = inboxRepo.getCurrentChats(uid)
            chatList.forEach {
               val chat = it.toObject(Chat::class.java)
                if(chat!!.senderReceiverList.contains(fid))
                    chatId = it.id
            }
            withContext(Dispatchers.Main){
                configureChatRecycler(chatId)
            }

        }
    }

    private fun configureChatRecycler(chatRoomId: String){
        chatAdapter = ChatAdapter(
            generateOptionForAdapter(getAllMessagesForChat(chatRoomId)),
            getCurrentUser()!!.uid,
            requireActivity().applicationContext
        )
        chatAdapter.registerAdapterDataObserver(e)
        binding.chatRecycler.adapter = chatAdapter
    }

    private var e = object: RecyclerView.AdapterDataObserver(){
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            binding.chatRecycler.smoothScrollToPosition(chatAdapter.itemCount)
        }
    }

    private fun generateOptionForAdapter(query: Query): FirestoreRecyclerOptions<Message>{
        return FirestoreRecyclerOptions.Builder<Message>()
            .setQuery(query,Message::class.java)
            .setLifecycleOwner(viewLifecycleOwner)
            .build()
    }
}
