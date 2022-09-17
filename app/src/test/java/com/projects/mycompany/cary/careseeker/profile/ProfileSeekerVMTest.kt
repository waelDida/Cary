package com.projects.mycompany.cary.careseeker.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.projects.mycompany.cary.data.source.seekers.FakeSeekerDataRepo
import com.projects.mycompany.cary.getOrAwaitValue
import com.projects.mycompany.cary.test.currentSeeker
import com.projects.mycompany.cary.test.seeker1
import com.projects.mycompany.cary.test.seeker2
import com.projects.mycompany.cary.test.seeker4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileSeekerVMTest{

    private lateinit var profileSeekerVM: ProfileSeekerVM
    private lateinit var seekerDataRepo: FakeSeekerDataRepo

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun createViewModel(){
        Dispatchers.setMain(Dispatchers.Unconfined)
        seekerDataRepo = FakeSeekerDataRepo()
        seekerDataRepo.addSeekers(currentSeeker,seeker1, seeker2, seeker4)
        profileSeekerVM = ProfileSeekerVM(seekerDataRepo)
    }

    @Test
    fun getCurrentSeeker(){
        assertThat(profileSeekerVM.currentSeeker.getOrAwaitValue(), `is`(currentSeeker))
    }

}