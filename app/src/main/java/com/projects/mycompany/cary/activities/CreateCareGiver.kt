package com.projects.mycompany.cary.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.api.createGiver
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.login.LoginActivity
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.utils.*

class CreateCareGiver : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var giverDataRepository: GiverDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_giver_create)
        auth = FirebaseAuth.getInstance()
        giverDataRepository = (application as ToDoApplication).giverDataRepository
        val careGiver = intent.getParcelableExtra<CareGiver>(CARE_GIVER_TAG)
        val password = intent.getStringExtra(PASSWORD_TAG)
        auth.createUserWithEmailAndPassword(careGiver!!.email, password!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    auth.currentUser!!.sendEmailVerification().addOnCompleteListener{
                        if(it.isSuccessful){
                            careGiver.uid = auth.currentUser!!.uid
                            createGiver(careGiver).addOnCompleteListener {
                                getMessageToken()
                            }
                        }
                        else{
                           returnToLoginActivity(2)
                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    returnToLoginActivity(3)
                }
            }
    }
    private fun returnToLoginActivity(x: Int){
        val intent = Intent(this@CreateCareGiver,LoginActivity::class.java)
        intent.putExtra(INDICE_AFTER_CREATION,x)
        startActivity(intent)
        finish()
    }

    private fun getMessageToken(){
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            if(!it.isSuccessful) {
                Toast.makeText(this,getString(R.string.something_wrong_message), Toast.LENGTH_SHORT).show()
            }
            else{
                val token = it.result?.token
                giverDataRepository.updateTokenList(getCurrentUser()!!.uid,token!!).addOnCompleteListener {
                    returnToLoginActivity(1)
                }

            }
        }
    }

    override fun onBackPressed() {}
}
