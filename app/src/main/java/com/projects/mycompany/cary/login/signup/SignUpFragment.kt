package com.projects.mycompany.cary.login.signup



import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.databinding.FragmentSignUpBinding
import com.projects.mycompany.cary.login.LoginViewModel
import com.projects.mycompany.cary.utils.*


class SignUpFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var viewModel: SignUpViewModel
    private lateinit var binder: FragmentSignUpBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_sign_up,container,false)
        binder = FragmentSignUpBinding.inflate(inflater)

        (activity as AppCompatActivity).supportActionBar?.show()

        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        binder.viewModel = viewModel
        binder.lifecycleOwner = this

        viewModel.signUpAction.observe(viewLifecycleOwner, Observer {
            if(it){
               viewModel.checkValidity(
                   binder.firstNameEdit.text.toString().trim(),
                   binder.lastNameEdit.text.toString().trim(),
                   binder.emailEdit.text.toString(),
                   binder.passwordEdit.text.toString(),
                   binder.retypePassEdit.text.toString())
            }
        })

        viewModel.infoCheck.observe(viewLifecycleOwner, Observer {
            when(it){
                FILL_ALL_FIELDS -> displayMessage(binder.constrainLayout,getString(R.string.fill_all_fields))
                INVALID_PASSWORD -> displayLongMessage(binder.constrainLayout,getString(R.string.invalid_password))
                CHECK_PASSWORD -> displayMessage(binder.constrainLayout,getString(R.string.check_password))
                INVALID_EMAIL -> displayMessage(binder.constrainLayout,getString(R.string.invalid_email))
                VALID_INFO -> saveData()
            }
        })
        return binder.root
    }

    private fun saveData(){
        loginViewModel.setFirstInfos(
            binder.firstNameEdit.text.toString().trim(),
            binder.lastNameEdit.text.toString().trim(),
            binder.emailEdit.text.toString(),
            binder.passwordEdit.text.toString()
        )
        findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToChoiceUserFragment())
        viewModel.signedUp()
    }
}
