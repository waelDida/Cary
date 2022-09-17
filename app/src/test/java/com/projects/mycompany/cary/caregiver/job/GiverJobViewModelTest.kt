package com.projects.mycompany.cary.caregiver.job

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.projects.mycompany.cary.getOrAwaitValue
import com.projects.mycompany.cary.utils.*
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GiverJobViewModelTest{

    private lateinit var giverJobViewModel: GiverJobViewModel
    private lateinit var varToTest : LiveData<String>
    private lateinit var saveClick: LiveData<Boolean>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel(){
       // giverJobViewModel = GiverJobViewModel()
        varToTest = giverJobViewModel.validInfo
        saveClick = giverJobViewModel.saveClick
    }

    @Test
    fun validInfo(){
        giverJobViewModel.setInfo("15","20","fullTime","yes")
        giverJobViewModel.validateAndSave()
        assertThat(varToTest.getOrAwaitValue(),`is`(VALID_INFO))
    }

    @Test
    fun missingPrice1(){
        giverJobViewModel.setInfo("","30","fullTime","no")
        giverJobViewModel.validateAndSave()
        assertThat(varToTest.getOrAwaitValue(),`is`(MISSING_PRICES))
    }

    @Test
    fun missingPrice2(){
        giverJobViewModel.setInfo("20","","fullTime","no")
        giverJobViewModel.validateAndSave()
        assertThat(varToTest.getOrAwaitValue(),`is`(MISSING_PRICES))
    }

    @Test
    fun missingPrice3(){
        giverJobViewModel.setInfo("","","fullTime","no")
        assertThat(varToTest.getOrAwaitValue(),`is`(MISSING_PRICES))
    }

    @Test
    fun invalidPrice(){
        giverJobViewModel.setInfo("40","10","partTime","yes")
        giverJobViewModel.validateAndSave()
        assertThat(varToTest.getOrAwaitValue(),`is`(INVALID_PRICE))
    }

    @Test
    fun missingJobType(){
        giverJobViewModel.setInfo("15","30","","no")
        giverJobViewModel.validateAndSave()
        assertThat(varToTest.getOrAwaitValue(),`is`(MISSING_JOB_TYPE))
    }

    @Test
    fun missingAvailability(){
        giverJobViewModel.setInfo("15","30","partTime","")
        giverJobViewModel.validateAndSave()
        assertThat(varToTest.getOrAwaitValue(),`is`(MISSING_AVAILABILITY))
    }

    @Test
    fun saveClick(){
        giverJobViewModel.onSaveClick()
        assertThat(saveClick.getOrAwaitValue(),not(nullValue()))
    }
}

