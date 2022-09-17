package com.projects.mycompany.cary.caregiver.offerdetail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.databinding.FragmentOfferDetailBinding

class OfferDetail : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_offer_detail, container, false)
        val binding = FragmentOfferDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val args = OfferDetailArgs.fromBundle(requireArguments()).careSeeker
        val viewModelFactory = OfferDetailVMFactory(args)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(OfferDetailViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.showProfile.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(OfferDetailDirections.actionOfferDetailToSeekerDetail(it))
                viewModel.onShowProfileClicked()
            }
        })
        return binding.root
    }


}
