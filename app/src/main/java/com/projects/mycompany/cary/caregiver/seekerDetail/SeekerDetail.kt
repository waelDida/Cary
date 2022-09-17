package com.projects.mycompany.cary.caregiver.seekerDetail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.databinding.FragmentSeekerDetailBinding
import com.projects.mycompany.cary.utils.displayMessage
import com.projects.mycompany.cary.utils.isNetworkAvailable

class SeekerDetail : Fragment() {
    private lateinit var binding: FragmentSeekerDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        if(!actionBar?.isShowing!!)
            actionBar.show()

        inflater.inflate(R.layout.fragment_seeker_detail, container, false)
        binding = FragmentSeekerDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val args = SeekerDetailArgs.fromBundle(requireArguments()).careSeeker
        val giverRepo = (requireContext().applicationContext as ToDoApplication).giverDataRepository
        val inboxRepo = (requireContext().applicationContext as ToDoApplication).inboxDataRepository
        val viewModelFactory = SeekerDetailVMFactory(inboxRepo,giverRepo,args)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(SeekerDetailVM::class.java)

        binding.sendMessage.setOnClickListener {
            if(isNetworkAvailable(requireActivity()))
                viewModel.onSendClick()
            else
                displayMessage(binding.seekerDetailLayout,getString(R.string.no_internet))
        }

        viewModel.sendMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(SeekerDetailDirections.actionSeekerDetailToGiverChat(it))
                viewModel.onSendClicked()
            }
        })

        binding.viewModel = viewModel

        return binding.root
    }
}
