package com.projects.mycompany.cary.careseeker.profile



import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.databinding.FragmentProfileSeekerBinding


class ProfileSeeker : Fragment() {

    private lateinit var binding : FragmentProfileSeekerBinding
    private lateinit var viewModel: ProfileSeekerVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_profile_seeker, container, false)
        binding = FragmentProfileSeekerBinding.inflate(inflater)
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        val appContext = requireContext().applicationContext
        val viewModelFactory = ProfileSeekerVMFactory((appContext as ToDoApplication).seekerDataRepository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(ProfileSeekerVM::class.java)
        viewModel.jobDetailsClick.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(ProfileSeekerDirections.actionProfileSeekerToJobDetails())
                viewModel.onJobDetailsClicked()
            }
        })

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.setting_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.settings_item)
            findNavController().navigate(ProfileSeekerDirections.actionProfileSeekerToSeekerSettings())
        return true
    }
}
