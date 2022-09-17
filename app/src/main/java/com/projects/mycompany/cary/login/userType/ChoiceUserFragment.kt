package com.projects.mycompany.cary.login.userType


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.databinding.FragmentChoiceUserBinding
import com.projects.mycompany.cary.login.LoginViewModel


class ChoiceUserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_choice_user,container,false)
        val binding = FragmentChoiceUserBinding.inflate(inflater)
        (activity as AppCompatActivity).supportActionBar?.show()
        val loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        binding.careGiverText.setOnClickListener {
            loginViewModel.selectCareGiver()
            findNavController().navigate(ChoiceUserFragmentDirections.actionChoiceUserFragmentToGiverCategoryFragment())
        }
        binding.careSeekerText.setOnClickListener {
            loginViewModel.selectCareSeeker()
            findNavController().navigate(ChoiceUserFragmentDirections.actionChoiceUserFragmentToSeekerCategoryFragment())
        }

        return binding.root
    }
}
