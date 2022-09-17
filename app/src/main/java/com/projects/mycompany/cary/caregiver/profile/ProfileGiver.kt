package com.projects.mycompany.cary.caregiver.profile



import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.databinding.FragmentProfileGiverBinding


class ProfileGiver : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        inflater.inflate(R.layout.fragment_profile_giver, container, false)
        val binding = FragmentProfileGiverBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val appContext = requireContext().applicationContext
        val viewModelFactory = ProfileGiverVMFactory((appContext as ToDoApplication).giverDataRepository)
        val viewModel = ViewModelProvider(this,viewModelFactory)
            .get(ProfileGiverViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.aboutClick.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(ProfileGiverDirections.actionProfileGiverToGiverAboutDetails(it))
                viewModel.onAboutClicked()
            }
        })

        viewModel.rateAndReviewClick.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(ProfileGiverDirections.actionProfileGiverToCurrentReviews())
                viewModel.onRateAndReviewsClicked()
            }
        })

        return binding.root

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.setting_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.settings_item)
            findNavController().navigate(ProfileGiverDirections.actionProfileGiverToGiverSettingsFragment())
        return true
    }
}
