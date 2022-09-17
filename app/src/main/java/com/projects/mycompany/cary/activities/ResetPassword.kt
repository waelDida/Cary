package com.projects.mycompany.cary.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.databinding.ActivityResetPasswordBinding
import com.projects.mycompany.cary.utils.displayMessage
import com.projects.mycompany.cary.utils.isValidEmail

class ResetPassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =  DataBindingUtil.setContentView<ActivityResetPasswordBinding>(this,
            R.layout.activity_reset_password
        )

        val email = binding.resetEmailEdit.text

        auth = FirebaseAuth.getInstance()

        binding.resetSendButton.setOnClickListener {
           if(email.isNotEmpty() && isValidEmail(email.toString())){
               auth.sendPasswordResetEmail(email.toString()).addOnCompleteListener{
                   if(it.isSuccessful)
                       displayMessage(binding.resetActivityLayout,getString(R.string.check_reset))
                   else
                       displayMessage(binding.resetActivityLayout,getString(R.string.fail_reset_link))
               }
           }
           else{
               displayMessage(binding.resetActivityLayout,getString(R.string.invalid_email))
           }
        }

        binding.resetBackButton.setOnClickListener {
            onBackPressed()
        }
    }
}
