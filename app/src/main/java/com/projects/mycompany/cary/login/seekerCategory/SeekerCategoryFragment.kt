package com.projects.mycompany.cary.login.seekerCategory


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.databinding.FragmentSeekerCategoryBinding
import com.projects.mycompany.cary.login.LoginViewModel


class SeekerCategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_seeker_category,container,false)
        val binding = FragmentSeekerCategoryBinding.inflate(inflater)
        val loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)

        binding.childCareLayout.setOnClickListener {
            loginViewModel.childCareClick()
            findNavController().navigate(SeekerCategoryFragmentDirections.actionSeekerCategoryFragmentToSeekerInfo())
        }

        binding.petCareLayout.setOnClickListener {
            loginViewModel.petCareClick()
            findNavController().navigate(SeekerCategoryFragmentDirections.actionSeekerCategoryFragmentToSeekerInfo())
        }

        binding.houseKeepingLayout.setOnClickListener {
            loginViewModel.housekeepingClick()
            findNavController().navigate(SeekerCategoryFragmentDirections.actionSeekerCategoryFragmentToSeekerInfo())
        }

        binding.seniorCareLayout.setOnClickListener {
            loginViewModel.seniorCareClick()
            findNavController().navigate(SeekerCategoryFragmentDirections.actionSeekerCategoryFragmentToSeekerInfo())
        }

        binding.specialNeedLayout.setOnClickListener {
            loginViewModel.specialNeedsClick()
            findNavController().navigate(SeekerCategoryFragmentDirections.actionSeekerCategoryFragmentToSeekerInfo())
        }

        binding.tutorLayout.setOnClickListener {
            loginViewModel.tutorClick()
            findNavController().navigate(SeekerCategoryFragmentDirections.actionSeekerCategoryFragmentToSeekerInfo())
        }

        return binding.root
    }


}
