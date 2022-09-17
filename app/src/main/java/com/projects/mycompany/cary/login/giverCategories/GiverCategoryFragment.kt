package com.projects.mycompany.cary.login.giverCategories


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.databinding.FragmentGiverCategoryBinding
import com.projects.mycompany.cary.login.LoginViewModel

class GiverCategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar?.show()

        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_giver_category,container,false)
        val binding = FragmentGiverCategoryBinding.inflate(inflater)
        val loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)


        binding.babySitterLayout.setOnClickListener {
            loginViewModel.childCareClick()
            findNavController().navigate(GiverCategoryFragmentDirections.actionGiverCategoryFragmentToGiverInfo())
        }

        binding.petSitterLayout.setOnClickListener {
            loginViewModel.petCareClick()
            findNavController().navigate(GiverCategoryFragmentDirections.actionGiverCategoryFragmentToGiverInfo())
        }

        binding.houseKeepingLayout.setOnClickListener {
            loginViewModel.housekeepingClick()
            findNavController().navigate(GiverCategoryFragmentDirections.actionGiverCategoryFragmentToGiverInfo())
        }

        binding.seniorCareLayout.setOnClickListener {
            loginViewModel.seniorCareClick()
            findNavController().navigate(GiverCategoryFragmentDirections.actionGiverCategoryFragmentToGiverInfo())
        }

        binding.specialNeedLayout.setOnClickListener {
            loginViewModel.specialNeedsClick()
            findNavController().navigate(GiverCategoryFragmentDirections.actionGiverCategoryFragmentToGiverInfo())
        }

        binding.tutorLayout.setOnClickListener {
            loginViewModel.tutorClick()
            findNavController().navigate(GiverCategoryFragmentDirections.actionGiverCategoryFragmentToGiverInfo())
        }

        return binding.root
    }


}
