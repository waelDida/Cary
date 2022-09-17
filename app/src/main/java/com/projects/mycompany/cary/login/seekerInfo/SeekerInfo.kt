package com.projects.mycompany.cary.login.seekerInfo


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.activities.CreateCareSeeker
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.databinding.FragmentSeekerInfoBinding
import com.projects.mycompany.cary.login.LoginViewModel
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.utils.CARE_SEEKER_TAG
import com.projects.mycompany.cary.utils.PASSWORD_TAG
import com.projects.mycompany.cary.utils.displayMessage
import com.projects.mycompany.cary.utils.isNetworkAvailable

class SeekerInfo : Fragment() {

    private lateinit var binder: FragmentSeekerInfoBinding

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var viewModel: SeekerInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_seeker_info,container, false)
        binder = FragmentSeekerInfoBinding.inflate(inflater)

        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        viewModel = ViewModelProvider(this).get(SeekerInfoViewModel::class.java)
        binder.viewModel = viewModel
        binder.lifecycleOwner = this

        viewModel.finishClick.observe(viewLifecycleOwner, Observer {
            if(it){
                viewModel.setInfo(
                    binder.phoneNumberEdit.text.toString(),
                    binder.addressEdit.text.toString())
                viewModel.checkValidity()
                viewModel.onFinishClicked()
            }
        })

        viewModel.validity.observe(viewLifecycleOwner, Observer {
            if(it)
                displayMessage(binder.seekerInfoLayout,getString(R.string.fill_all_fields))
            else
                sendDataToCreation()
        })
        return binder.root
    }

    private fun sendDataToCreation(){
        if(isNetworkAvailable(requireActivity())){
            val careSeeker = CareSeeker(
                "",
                loginViewModel.firstName.value.toString(),
                loginViewModel.lastName.value.toString(),
                loginViewModel.email.value.toString(),
                viewModel.phoneNumber.value.toString(),
                viewModel.address.value.toString(),
                loginViewModel.careCategory.value.toString(),
                loginViewModel.latitude.value?.toDouble() ?: 0.0,
                loginViewModel.longitude.value?.toDouble() ?: 0.0
            )
            val intent = Intent(activity,
                CreateCareSeeker::class.java)
            intent.putExtra(PASSWORD_TAG,loginViewModel.password.value)
            intent.putExtra(CARE_SEEKER_TAG,careSeeker)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        else
            displayMessage(binder.seekerInfoLayout,getString(R.string.no_internet))

    }
}
