package com.projects.mycompany.cary.caregiver.basicinfo


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.databinding.FragmentGiverBasicInfoBinding
import com.projects.mycompany.cary.utils.*


class GiverBasicInfo : Fragment() {

    private lateinit var binding: FragmentGiverBasicInfoBinding
    private lateinit var viewModel: GiverBasicViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_giver_basic_info, container, false)
        binding = FragmentGiverBasicInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        val viewModelFactory = GiverBasicVMFactory((requireContext().applicationContext as ToDoApplication).giverDataRepository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(GiverBasicViewModel::class.java)
        binding.viewModel = viewModel


        viewModel.saveBasicInfo.observe(viewLifecycleOwner, Observer {
            if(it){
                viewModel.setInfo(
                    binding.giverFirstName.text.toString(),
                    binding.giverLastName.text.toString(),
                    binding.giverBirthDate.text.toString(),
                    resources.getResourceEntryName(binding.genderRadioGroup.checkedRadioButtonId))
                viewModel.checkAndValidate()
                viewModel.onSaveClicked()
            }
        })

        viewModel.validInfo.observe(viewLifecycleOwner, Observer {
            when(it){
                INVALID_AGE -> displayMessage(binding.basicInfoLayout,getString(R.string.invalid_birth_date))
                UNDER_18 -> displayMessage(binding.basicInfoLayout,getString(R.string.age_under_18))
                MISSING_INFO -> displayMessage(binding.basicInfoLayout,getString(R.string.fill_all_fields))
                VALID_INFO -> displayMessage(binding.basicInfoLayout, getString(R.string.saved))
            }
        })

        viewModel.onBirthdayClick.observe(viewLifecycleOwner, Observer {
            if(it){
                showDatePickerDialog(requireContext(),binding.giverBirthDate)
                viewModel.onBirthdayClicked()
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
            R.id.save_item ->  viewModel.onSaveClick()
            android.R.id.home -> requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return true
    }


}
