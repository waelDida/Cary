package com.projects.mycompany.cary.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.databinding.ActivityNoInternetBinding
import com.projects.mycompany.cary.utils.isNetworkAvailable

class NoInternetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        val binding = DataBindingUtil.setContentView<ActivityNoInternetBinding>(this,R.layout.activity_no_internet)

        binding.connectionRetry.setOnClickListener {
            if(isNetworkAvailable(this))
                startActivity(Intent(this,InitActivity::class.java))
        }
    }
    override fun onBackPressed() {}
}
