package com.projects.mycompany.cary.login.giverInfo



import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.activities.CreateCareGiver
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.databinding.FragmentGiverInfoBinding
import com.projects.mycompany.cary.login.LoginViewModel
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.utils.*


class GiverInfo : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var binding: FragmentGiverInfoBinding
    private lateinit var exp: String
    private lateinit var loginViewModel: LoginViewModel
    lateinit var viewModel: GiverInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_giver_info, container, false)
        binding = FragmentGiverInfoBinding.inflate(inflater)

        ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, experience)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.experienceSpinner.adapter = adapter
            }
        binding.experienceSpinner.onItemSelectedListener = this

        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        Log.d("TAG","${loginViewModel.careCategory.value}")
        viewModel = ViewModelProvider(this).get(GiverInfoViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.birthDateClick.observe(viewLifecycleOwner, Observer {
            if (it) {
                showDatePickerDialog(requireContext(), binding.birthdayEditText)
                viewModel.onBirthDateClicked()
            }
        })

        viewModel.onFinishClick.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.setLastInfo(
                    binding.birthdayEditText.text.toString(),
                    binding.phoneEditText.text.toString(),
                    binding.addressEditText.text.toString(),
                    resources.getResourceEntryName(binding.genderRadioGroup.checkedRadioButtonId),
                    resources.getString(
                        R.string.formatted_prices,
                        binding.minPrice.text.toString(),
                        binding.maxPrice.text.toString()
                    ),
                    exp,
                    resources.getResourceEntryName(binding.jobTypeRadioGroup.checkedRadioButtonId),
                    resources.getResourceEntryName(binding.availabilityRadioGroup.checkedRadioButtonId)
                )
                viewModel.checkValidity()
                viewModel.finishClicked()
            }
        })

        viewModel.validInfo.observe(viewLifecycleOwner, Observer {
            when (it) {
                INVALID_AGE -> displayMessage(
                    binding.giverInfoConstraintLout,
                    getString(R.string.invalid_age_range)
                )
                UNDER_18 -> displayMessage(
                    binding.giverInfoConstraintLout,
                    getString(R.string.age_under_18)
                )
                MISSING_INFO -> displayMessage(
                    binding.giverInfoConstraintLout,
                    getString(R.string.fill_all_fields)
                )
                VALID_INFO -> sendDateToCreation()
            }
        })

        return binding.root
    }

    private fun sendDateToCreation() {
        if(isNetworkAvailable(requireActivity())){
            val intent = Intent(
                activity,
                CreateCareGiver::class.java
            )
            val careGiver = CareGiver(
                uid = "",
                firstName = loginViewModel.firstName.value.toString(),
                lastName = loginViewModel.lastName.value.toString(),
                email = loginViewModel.email.value.toString(),
                birthdate = viewModel.birthDate.value.toString(),
                phone = viewModel.phoneNumber.value.toString(),
                address = viewModel.address.value.toString(),
                gender = viewModel.gender.value.toString(),
                price = viewModel.price.value.toString(),
                experience = viewModel.experience.value.toString(),
                job = viewModel.jobType.value.toString(),
                availability = viewModel.availability.value.toString(),
                careCategory = loginViewModel.careCategory.value.toString(),
                latitude = loginViewModel.latitude.value?.toDouble() ?: 0.0,
                longitude = loginViewModel.longitude.value?.toDouble() ?: 0.0,
                rating = 0.0
            )
            intent.putExtra(PASSWORD_TAG, loginViewModel.password.value.toString())
            intent.putExtra(CARE_GIVER_TAG, careGiver)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        else{
            displayMessage(binding.giverInfoConstraintLout,getString(R.string.no_internet))
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        exp = experience[0]
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        // When spinner selected item
        exp = experience[position]
    }
}