package com.projects.mycompany.cary.careseeker.filter

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.projects.mycompany.cary.getOrAwaitValue
import com.projects.mycompany.cary.utils.INVALID_AGE_RANGE
import com.projects.mycompany.cary.utils.MISSING_GENDER
import com.projects.mycompany.cary.utils.MISSING_JOB_TYPE
import com.projects.mycompany.cary.utils.VALID_INFO
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class SeekerFilterViewModelTest{

    lateinit var seekerFilterViewModel: SeekerFilterViewModel
    lateinit var testedLiveData: LiveData<String>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel(){
        val app = Mockito.mock(Application::class.java)
        seekerFilterViewModel = SeekerFilterViewModel(app)
        testedLiveData = seekerFilterViewModel.validInfo
    }

    @Test
    fun validInfo(){
        seekerFilterViewModel.setMaleCheckbox(false)
        seekerFilterViewModel.setFemaleCheckbox(false)
        seekerFilterViewModel.maleCheckboxClicked()
        seekerFilterViewModel.setFullTypeCheckbox(false)
        seekerFilterViewModel.setPartTypeCheckbox(false)
        seekerFilterViewModel.fullTimeCheckBoxClicked()
        seekerFilterViewModel.setMaxAge(50)
        seekerFilterViewModel.setMinAge(30)
        seekerFilterViewModel.checkThenSave()
        assertThat(testedLiveData.getOrAwaitValue(),`is`(VALID_INFO))
    }

    @Test
    fun invalidAgeRange(){
        seekerFilterViewModel.setMaxAge(25)
        seekerFilterViewModel.setMinAge(25)
        seekerFilterViewModel.checkThenSave()
        assertThat(testedLiveData.getOrAwaitValue(),`is`(INVALID_AGE_RANGE))
    }

    @Test
    fun missingGender(){
        seekerFilterViewModel.setMaleCheckbox(false)
        seekerFilterViewModel.setFemaleCheckbox(false)
        seekerFilterViewModel.checkThenSave()
        assertThat(testedLiveData.getOrAwaitValue(),`is`(MISSING_GENDER))
    }

    @Test
    fun missingGender2(){
        seekerFilterViewModel.setMaleCheckbox(false)
        seekerFilterViewModel.checkThenSave()
        assertThat(testedLiveData.getOrAwaitValue(),`is`(MISSING_GENDER))
    }

    @Test
    fun missingTypeJob(){
        seekerFilterViewModel.setFullTypeCheckbox(false)
        seekerFilterViewModel.setPartTypeCheckbox(false)
        seekerFilterViewModel.checkThenSave()
        assertThat(testedLiveData.getOrAwaitValue(),`is`(MISSING_JOB_TYPE))
    }
}