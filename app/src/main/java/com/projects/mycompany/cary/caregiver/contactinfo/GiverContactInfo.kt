package com.projects.mycompany.cary.caregiver.contactinfo


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.databinding.FragmentGiverContactInfoBinding
import com.projects.mycompany.cary.utils.*


class GiverContactInfo : Fragment() {

    private lateinit var binding: FragmentGiverContactInfoBinding
    private lateinit var viewModel: GiverContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_giver_contact_info, container, false)
        binding = FragmentGiverContactInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        val viewModelFactory = GiverContactVMFactory((requireContext().applicationContext as ToDoApplication).giverDataRepository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(GiverContactViewModel::class.java)
        binding.viewModel = viewModel


        viewModel.saveClick.observe(viewLifecycleOwner, Observer {
            if(it){
                viewModel.setInfo(binding.giverAddress.text.toString(), binding.giverPhoneNumber.text.toString())
                viewModel.checkThenValidate()
                viewModel.onSaveClicked()
            }
        })

        viewModel.validInfo.observe(viewLifecycleOwner, Observer {
            when(it){
                MISSING_INFO -> displayMessage(binding.giverContactLayout,getString(R.string.fill_all_fields))
                MISSING_ADDRESS -> displayMessage(binding.giverContactLayout,getString(R.string.missing_address))
                MISSING_PHONE_NUMBER -> displayMessage(binding.giverContactLayout,getString(R.string.missing_phone_number))
                VALID_INFO -> displayMessage(binding.giverContactLayout, getString(R.string.saved))
            }
        })
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.save_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_item -> viewModel.onSaveClick()
            android.R.id.home -> requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return true
    }


}
