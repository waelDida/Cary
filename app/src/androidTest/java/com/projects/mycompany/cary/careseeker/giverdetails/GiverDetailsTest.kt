package com.projects.mycompany.cary.careseeker.giverdetails



import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test

import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class GiverDetailsTest{

    //private lateinit var repository:

    @Test
    fun test(){
        launchFragmentInContainer<GiverDetails>()
    }
}