package com.projects.mycompany.cary.caregiver.contactinfo


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.projects.mycompany.cary.getOrAwaitValue
import com.projects.mycompany.cary.utils.MISSING_ADDRESS
import com.projects.mycompany.cary.utils.MISSING_INFO
import com.projects.mycompany.cary.utils.MISSING_PHONE_NUMBER
import com.projects.mycompany.cary.utils.VALID_INFO
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class GiverContactInfoTest{
    private lateinit var giverContactViewModel: GiverContactViewModel
    private lateinit var validInfo: LiveData<String>
    private lateinit var saveClick: LiveData<Boolean>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel(){
        //giverContactViewModel = GiverContactViewModel()
        validInfo = giverContactViewModel.validInfo
        saveClick = giverContactViewModel.saveClick
    }

    @Test
    fun validInfo(){
        giverContactViewModel.setInfo("Cite","96539246")
        giverContactViewModel.checkThenValidate()
        assertThat(validInfo.getOrAwaitValue(),`is`(VALID_INFO))
    }

    @Test
    fun missingInfo(){
        giverContactViewModel.setInfo("","")
        giverContactViewModel.checkThenValidate()
        assertThat(validInfo.getOrAwaitValue(),`is`(MISSING_INFO))
    }

    @Test
    fun missingAddress(){
        giverContactViewModel.setInfo("","96539246")
        giverContactViewModel.checkThenValidate()
        assertThat(validInfo.getOrAwaitValue(),`is`(MISSING_ADDRESS))
    }

    @Test
    fun missingPhoneNumber(){
        giverContactViewModel.setInfo("Cite","")
        giverContactViewModel.checkThenValidate()
        assertThat(validInfo.getOrAwaitValue(),`is`(MISSING_PHONE_NUMBER))
    }

    @Test
    fun handleEmptySpace(){
        giverContactViewModel.setInfo("  ","  ")
        giverContactViewModel.checkThenValidate()
        assertThat(validInfo.getOrAwaitValue(),`is`(MISSING_INFO))
    }

    @Test
    fun onSaveClick(){
        giverContactViewModel.onSaveClick()
        assertThat(saveClick.getOrAwaitValue(), not(nullValue()))
    }
}