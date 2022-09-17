package com.projects.mycompany.cary.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.api.createSeeker
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import com.projects.mycompany.cary.login.LoginActivity
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.utils.CARE_SEEKER_TAG
import com.projects.mycompany.cary.utils.INDICE_AFTER_CREATION
import com.projects.mycompany.cary.utils.PASSWORD_TAG
import com.projects.mycompany.cary.utils.getCurrentUser

class CreateCareSeeker : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var seekerDataRepository: SeekerDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_care_seeker)
        seekerDataRepository = (application as ToDoApplication).seekerDataRepository
        val careSeeker = intent.getParcelableExtra<CareSeeker>(CARE_SEEKER_TAG)
        val password = intent.getStringExtra(PASSWORD_TAG)

        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(careSeeker!!.email,password!!).addOnCompleteListener (this){task->
            if(task.isSuccessful){
               auth.currentUser!!.sendEmailVerification().addOnCompleteListener {
                   if(it.isSuccessful){
                       // Create seeker in Firebase
                       careSeeker.uid = auth.currentUser!!.uid
                       createSeeker(careSeeker).addOnCompleteListener {
                           getMessageToken()
                       }
                   }
                   else
                       returnToLoginActivity(2)
               }
            }
            else
                returnToLoginActivity(3)
        }
    }

    private fun returnToLoginActivity(x: Int){
        val intent = Intent(this@CreateCareSeeker, LoginActivity::class.java)
        intent.putExtra(INDICE_AFTER_CREATION,x)
        startActivity(intent)
        finish()
    }

    private fun getMessageToken(){
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            if(!it.isSuccessful) {
                Toast.makeText(this,getString(R.string.something_wrong_message),Toast.LENGTH_SHORT).show()
            }
            else{
                val token = it.result?.token
                seekerDataRepository.updateTokenList(getCurrentUser()!!.uid,token!!).addOnCompleteListener {
                    returnToLoginActivity(1)
                }

            }
        }
    }

    override fun onBackPressed() {}
}
