package com.projects.mycompany.cary.careseeker.giverdetails



import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.databinding.FragmentGiverDetailsBinding
import com.projects.mycompany.cary.utils.displayMessage
import com.projects.mycompany.cary.utils.isNetworkAvailable

class GiverDetails : Fragment() {
    private lateinit var binding: FragmentGiverDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        if(!actionBar?.isShowing!!)
            actionBar.show()

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_giver_details,container,false)
        binding.lifecycleOwner = this

        val careGiver = GiverDetailsArgs.fromBundle(requireArguments()).careGiver
        val seekerRepo = (requireContext().applicationContext as ToDoApplication).seekerDataRepository
        val inboxRepo = (requireContext().applicationContext as ToDoApplication).inboxDataRepository
        val detailsVMFactory = DetailsVMFactory(inboxRepo,seekerRepo,careGiver)
        val viewModel = ViewModelProvider(this,detailsVMFactory).get(DetailsViewModel::class.java)

        binding.viewModel = viewModel

        binding.sendMessage.setOnClickListener {
            if(isNetworkAvailable(requireActivity()))
                viewModel.onSendClicked()
            else
                displayMessage(binding.giverDetailLayout,getString(R.string.no_internet))
        }

        viewModel.aboutClick.observe(viewLifecycleOwner, Observer {about ->
            about?.let {
                findNavController().navigate(GiverDetailsDirections.actionGiverDetailsToAboutGiverDetails(about))
                viewModel.onAboutClicked()
            }
        })

        viewModel.rateClick.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(GiverDetailsDirections.actionGiverDetailsToRateGiverFragment(it))
                viewModel.onRateClicked()
            }
        })

        viewModel.reviewsClick.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(GiverDetailsDirections.actionGiverDetailsToGiverReviews(it))
                viewModel.onReviewsClicked()
            }
        })

        viewModel.sendMessage.observe(viewLifecycleOwner, Observer {
            it?.let{
                findNavController().navigate(GiverDetailsDirections.actionGiverDetailsToChatFragment(it))
                viewModel.onSendClicked()
            }
        })

        return binding.root
    }
}
