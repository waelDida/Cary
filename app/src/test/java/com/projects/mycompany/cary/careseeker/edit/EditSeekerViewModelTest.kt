package com.projects.mycompany.cary.careseeker.edit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.projects.mycompany.cary.getOrAwaitValue
import com.projects.mycompany.cary.utils.*
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

class EditSeekerViewModelTest{

    private lateinit var editSeekerViewModel: EditSeekerViewModel
    private lateinit var validInfo: LiveData<String>
    private lateinit var firstName: LiveData<String>
    private lateinit var lastName: LiveData<String>
    private lateinit var address: LiveData<String>
    private lateinit var phoneNumber: LiveData<String>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel(){
       // editSeekerViewModel = EditSeekerViewModel()
        validInfo = editSeekerViewModel.validInfo
        firstName = editSeekerViewModel.firstName
        lastName = editSeekerViewModel.lastName
        address = editSeekerViewModel.seekerAddress
        phoneNumber = editSeekerViewModel.seekerPhoneNumber

    }

    @Test
    fun testInit(){
        assertThat(firstName.getOrAwaitValue(),`is`("Wael"))
        assertThat(lastName.getOrAwaitValue(),`is`("Saadaoui"))
        assertThat(address.getOrAwaitValue(),`is`("Zayatine Street Feriana 1240"))
        assertThat(phoneNumber.getOrAwaitValue(),`is`("22520145"))

    }

    @Test
    fun validInfo(){
        editSeekerViewModel.setInfo("Wael","Saadaoui","Cité zayatine","96539246")
        editSeekerViewModel.checkAndSave()
        assertThat(validInfo.getOrAwaitValue(),`is`(VALID_INFO))
    }

    @Test
    fun missingInfo(){
        editSeekerViewModel.setInfo("","","","")
        editSeekerViewModel.checkAndSave()
        assertThat(validInfo.getOrAwaitValue(),`is`(MISSING_INFO))
    }

    @Test
    fun missingFirstName(){
        editSeekerViewModel.setInfo("","saadaoui","cite","9632565")
        editSeekerViewModel.checkAndSave()
        assertThat(validInfo.getOrAwaitValue(),`is`(MISSING_FIRST_NAME))
    }

    @Test
    fun missingLastName(){
        editSeekerViewModel.setInfo("Wael","","cite","9632565")
        editSeekerViewModel.checkAndSave()
        assertThat(validInfo.getOrAwaitValue(),`is`(MISSING_LAST_NAME))
    }

    @Test
    fun missingAddress(){
        editSeekerViewModel.setInfo("Wael","Saadaoui","","96539246")
        editSeekerViewModel.checkAndSave()
        assertThat(validInfo.getOrAwaitValue(),`is`(MISSING_ADDRESS))
    }

    @Test
    fun missingPhoneNumber(){
        editSeekerViewModel.setInfo("Wael","Saadaoui","gffd","")
        editSeekerViewModel.checkAndSave()
        assertThat(validInfo.getOrAwaitValue(),`is`(MISSING_PHONE_NUMBER))
    }

    @Test
    fun checkSpaceEliminated(){
        editSeekerViewModel.setInfo(" wael","saadaoui  ","Cité   ","  96539246")
        editSeekerViewModel.checkAndSave()
        assertThat(firstName.getOrAwaitValue(),`is`("wael"))
        assertThat(lastName.getOrAwaitValue(),`is`("saadaoui"))
        assertThat(address.getOrAwaitValue(),`is`("Cité"))
        assertThat(phoneNumber.getOrAwaitValue(),`is`("96539246"))
    }
}