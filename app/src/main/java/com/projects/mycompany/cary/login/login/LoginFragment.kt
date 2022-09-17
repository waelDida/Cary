package com.projects.mycompany.cary.login.login



import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.projects.mycompany.cary.activities.InitActivity
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.activities.ResetPassword
import com.projects.mycompany.cary.activities.SignInUser
import com.projects.mycompany.cary.databinding.FragmentLoginBinding
import com.projects.mycompany.cary.utils.*


class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding
    private lateinit var auth : FirebaseAuth
    private val privacyAndTerms = "<html>By signing in, you agree with our <a href=https://sites.google.com/view/caryservices/privacy>Terms of service</a> and <a href=https://sites.google.com/view/caryservices/terms>Privacy policy</html>"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(inflater)
        val viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        (activity as AppCompatActivity).supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        binding.privacyTermsText.text = HtmlCompat.fromHtml(privacyAndTerms,HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.login.observe(viewLifecycleOwner, Observer {
            if(it){
                viewModel.checkValidity(binding.emailEdit.text.toString(),binding.passwordEdit.text.toString())
                viewModel.loggedIn()
            }
        })

        viewModel.signUp.observe(viewLifecycleOwner, Observer {
            if(it){
                if(auth.currentUser == null){
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
                    viewModel.signedUp()
                }
                else
                    displayMessage(binding.loginFragmentLayout,getString(R.string.already_signed_up))
            }
        })

        viewModel.forgetPassword.observe(viewLifecycleOwner, Observer {
            if(it){
                startActivity(Intent(activity,
                    ResetPassword::class.java))
                viewModel.onForgotPasswordClicked()
            }
        })

        viewModel.cred.observe(viewLifecycleOwner, Observer {
            when(it){
                EMPTY_EMAIL_PASSWORD -> displayMessage(binding.loginFragmentLayout,getString(R.string.enter_email_pass))
                INVALID_EMAIL -> displayMessage(binding.loginFragmentLayout,getString(R.string.invalid_email))
                INVALID_PASSWORD -> displayLongMessage(binding.loginFragmentLayout,getString(R.string.invalid_password))
                VALID_EMAIL_PASSWORD -> login()
            }
        })
        return binding.root
    }

    private fun login(){
        val intent = Intent(activity,
            SignInUser::class.java)
        intent.putExtra(EMAIL_TAG,binding.emailEdit.text.toString().trim())
        intent.putExtra(PASSWORD_TAG,binding.passwordEdit.text.toString().trim())
        startActivityForResult(intent, LOGIN_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == LOGIN_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                when(data?.getStringExtra(SIGN_IN_RESULT)){
                    SUCCESS -> startCheckUserActivity()
                    EMAIL_VERIFICATION_FAIL -> displayMessage(binding.loginFragmentLayout,getString(R.string.verify_email))
                    else -> displayMessage(binding.loginFragmentLayout,getString(R.string.authentication_failed))
                }
            }
        }
    }

    private fun startCheckUserActivity(){
        startActivity(Intent(activity, InitActivity::class.java))
        requireActivity().finish()
    }
}
