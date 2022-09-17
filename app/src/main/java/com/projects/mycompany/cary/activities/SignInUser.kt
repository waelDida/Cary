package com.projects.mycompany.cary.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.utils.*

class SignInUser : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_user)

        auth = FirebaseAuth.getInstance()

        val email = intent.getStringExtra(EMAIL_TAG)
        val password = intent.getStringExtra(PASSWORD_TAG)

        auth.signInWithEmailAndPassword(email!!,password!!).addOnCompleteListener(this){task->
            if (task.isSuccessful) {
                // Sign in and email verification success
                if(auth.currentUser!!.isEmailVerified){
                    val returnIntent = Intent()
                    returnIntent.putExtra(SIGN_IN_RESULT, SUCCESS)
                    setResult(Activity.RESULT_OK,returnIntent)
                    finish()
                }
                else{
                    //email verification failed
                    val returnIntent = Intent()
                    returnIntent.putExtra(SIGN_IN_RESULT, EMAIL_VERIFICATION_FAIL)
                    setResult(Activity.RESULT_OK,returnIntent)
                    finish()
                }

            } else {
                // If sign in fails
                val returnIntent = Intent()
                returnIntent.putExtra(SIGN_IN_RESULT, FAIL)
                setResult(Activity.RESULT_OK,returnIntent)
                finish()
            }
        }
    }
}
