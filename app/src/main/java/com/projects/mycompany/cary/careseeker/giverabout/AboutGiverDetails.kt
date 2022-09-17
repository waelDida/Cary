package com.projects.mycompany.cary.careseeker.giverabout


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.databinding.FragmentAboutGiverDetailsBinding


class AboutGiverDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_about_giver_details, container, false)
        val binding = FragmentAboutGiverDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val args = AboutGiverDetailsArgs.fromBundle(requireArguments()).aboutCareGiver
        val viewModelFactory = AboutVMFactory(args)
        val viewModel = ViewModelProvider(this,viewModelFactory)
            .get(AboutGDViewModel::class.java)
        binding.viewModel = viewModel

       return binding.root

    }
}
