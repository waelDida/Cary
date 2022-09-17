package com.projects.mycompany.cary.caregiver.filter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.projects.mycompany.cary.getOrAwaitValue
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GiverFilterViewModelTest{

    private lateinit var giverFilterViewModel: GiverFilterViewModel
    private lateinit var distance : LiveData<Int>
    private lateinit var showPremiums: LiveData<Boolean>
    private lateinit var saveClick: LiveData<Boolean>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel(){
       // giverFilterViewModel = GiverFilterViewModel()
        distance = giverFilterViewModel.distance
        showPremiums = giverFilterViewModel.showPremiums
        saveClick = giverFilterViewModel.saveClick
    }

    @Test
    fun initViewModel(){
        assertThat(distance.getOrAwaitValue(),`is`(70))
        assertThat(showPremiums.getOrAwaitValue(),`is`(true))
    }

    @Test
    fun changeSwitch(){
        giverFilterViewModel.setCheckStatus(false)
        assertThat(showPremiums.getOrAwaitValue(),`is`(false))
    }

    @Test
    fun saveClick(){
        giverFilterViewModel.onSaveClicked()
        assertThat(saveClick.getOrAwaitValue(),not(nullValue()))
    }

}