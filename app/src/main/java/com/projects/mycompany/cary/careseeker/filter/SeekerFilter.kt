package com.projects.mycompany.cary.careseeker.filter



import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.databinding.FragmentSeekerFilterBinding
import com.projects.mycompany.cary.utils.*



class SeekerFilter : Fragment(), SeekBar.OnSeekBarChangeListener {

    private lateinit var binding: FragmentSeekerFilterBinding
    private lateinit var viewModel: SeekerFilterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_seeker_filter, container, false)
        binding = FragmentSeekerFilterBinding.inflate(inflater)
        val app = requireNotNull(activity).application
        val viewModelFactory = SeekerFVMFactory(app)
        viewModel = ViewModelProvider(this,viewModelFactory).get(SeekerFilterViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        // Setup experience spinner
        val experienceAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item, experienceForFilter)
        experienceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.experienceSpinner.adapter = experienceAdapter

        // Setup min age spinner
        val minAgeAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,ages)
        minAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.minAgeSpinner.adapter = minAgeAdapter

        // Setup max age spinner
        val maxAgeAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item,ages)
        maxAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.maxAgeSpinner.adapter = maxAgeAdapter

        // Setup experience spinner click
        binding.experienceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
               viewModel.setExperience(experienceForFilter[0])
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
               viewModel.setExperience(experienceForFilter[position])
            }
        }

        // Setup min age spinner click
        binding.minAgeSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                viewModel.setMinAge(ages[0])
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                viewModel.setMinAge(ages[position])
            }
        }

        // Setup max age spinner click
        binding.maxAgeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                viewModel.setMaxAge(ages[position])
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
               viewModel.setMaxAge(ages[0])
            }
        }

        // Listen to the seek bar changes
        binding.distanceSeekbar.setOnSeekBarChangeListener(this)

        viewModel.validInfo.observe(viewLifecycleOwner, Observer {
            when(it) {
                MISSING_GENDER -> displayMessage(binding.filterConstraint, getString(R.string.no_gender_selected))
                MISSING_JOB_TYPE -> displayMessage(binding.filterConstraint, getString(R.string.no_job_type_selected))
                INVALID_AGE_RANGE -> displayMessage(binding.filterConstraint,getString(R.string.invalid_age_range))
                VALID_INFO -> findNavController().navigate(SeekerFilterDirections.actionSeekerFilterToSearchSeeker())
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.save_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.save_item -> viewModel.checkThenSave()
            android.R.id.home -> requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return true
    }

    override fun onProgressChanged(p0: SeekBar?, i: Int, p2: Boolean) {
        if(p2)
            viewModel.changeMaxDistance(i)

    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }
}
