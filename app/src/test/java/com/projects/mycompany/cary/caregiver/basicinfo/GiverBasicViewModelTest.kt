package com.projects.mycompany.cary.caregiver.basicinfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.projects.mycompany.cary.getOrAwaitValue
import com.projects.mycompany.cary.utils.INVALID_AGE
import com.projects.mycompany.cary.utils.MISSING_INFO
import com.projects.mycompany.cary.utils.UNDER_18
import com.projects.mycompany.cary.utils.VALID_INFO
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GiverBasicViewModelTest{

    private lateinit var giverBasicViewModel: GiverBasicViewModel
    private lateinit var value: LiveData<String>

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel(){
       // giverBasicViewModel = GiverBasicViewModel()
        value = giverBasicViewModel.validInfo
    }

    @Test
    fun test_valid_info(){
        giverBasicViewModel.setInfo("Wael","Saadaoui","12/18/1985","male")
        giverBasicViewModel.checkAndValidate()
        assertThat(value.getOrAwaitValue(),`is`(VALID_INFO))
    }

    @Test
    fun test_missing_info(){
        giverBasicViewModel.setInfo("","Saadaoui","12/18/1985","male")
        giverBasicViewModel.checkAndValidate()
        assertThat(value.getOrAwaitValue(), `is`(MISSING_INFO))
    }

    @Test
    fun test_invalid_age(){
        giverBasicViewModel.setInfo("wael","saadaoui","04/15/2020","male")
        giverBasicViewModel.checkAndValidate()
        assertThat(value.getOrAwaitValue(), `is`(INVALID_AGE))
    }

    @Test
    fun test_under_18_age(){
        giverBasicViewModel.setInfo("wael","saadaoui","04/15/2010","male")
        giverBasicViewModel.checkAndValidate()
        assertThat(value.getOrAwaitValue(), `is`(UNDER_18))
    }
}