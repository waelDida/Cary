package com.projects.mycompany.cary.caregiver.job


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.databinding.FragmentGiverJobBinding
import com.projects.mycompany.cary.utils.*


class GiverJob : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentGiverJobBinding
    private lateinit var viewModel: GiverJobViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_giver_job, container, false)
        binding = FragmentGiverJobBinding.inflate(inflater)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        val viewModelFactory = GiverJobVMFactory((requireContext().applicationContext as ToDoApplication).giverDataRepository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(GiverJobViewModel::class.java)
        binding.viewModel = viewModel


       ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,experience)
           .also {adapter ->
               adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
               binding.experienceSpinner.adapter = adapter
           }
        binding.experienceSpinner.onItemSelectedListener = this

        viewModel.saveClick.observe(viewLifecycleOwner, Observer {
            if(it){
                viewModel.setInfo(binding.minPrice.text.toString(),
                    binding.maxPrice.text.toString(),
                    resources.getResourceEntryName(binding.jobTypeRadioGroup.checkedRadioButtonId),
                    resources.getResourceEntryName(binding.availabilityRadioGroup.checkedRadioButtonId))
                viewModel.validateAndSave()
                viewModel.onSaveClicked()
            }
        })

        viewModel.validInfo.observe(viewLifecycleOwner, Observer {
            when(it){
                MISSING_PRICES -> displayMessage(binding.giverJobConstraint,getString(R.string.missing_price))
                INVALID_PRICE -> displayMessage(binding.giverJobConstraint,getString(R.string.invalid_prices))
                MISSING_JOB_TYPE -> displayMessage(binding.giverJobConstraint,getString(R.string.no_job_type_selected))
                MISSING_AVAILABILITY -> displayMessage(binding.giverJobConstraint,getString(R.string.missing_availability))
                VALID_INFO -> displayMessage(binding.giverJobConstraint, getString(R.string.saved))
            }
        })
        return binding.root
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        viewModel.setExperience(experience[0])
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        viewModel.setExperience(experience[position])
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
