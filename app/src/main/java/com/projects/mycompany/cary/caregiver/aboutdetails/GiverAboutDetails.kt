package com.projects.mycompany.cary.caregiver.aboutdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.databinding.FragmentGiverAboutDetailsBinding


class GiverAboutDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflater.inflate(R.layout.fragment_giver_about_details,container,false)
        val binding = FragmentGiverAboutDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val args = GiverAboutDetailsArgs.fromBundle(requireArguments()).aboutGiver
        val viewModel = ViewModelProvider(this,GiverAboutDVMFactory(args)).get(GiverADViewModel::class.java)

        binding.viewModel = viewModel

        return binding.root
    }
}
