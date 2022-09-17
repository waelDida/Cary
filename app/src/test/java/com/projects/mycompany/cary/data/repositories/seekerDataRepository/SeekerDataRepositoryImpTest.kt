package com.projects.mycompany.cary.data.repositories.seekerDataRepository

import com.projects.mycompany.cary.data.source.seekers.FakeSeekerDataSource
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.test.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SeekerDataRepositoryImpTest{
    // class under test
    private lateinit var seekerDataRepositoryImp: SeekerDataRepositoryImp

    private var remoteList = mutableListOf(seeker1,seeker2)

    @Before
    fun createRepository(){
        val fakeDataSource = FakeSeekerDataSource(remoteList)
        seekerDataRepositoryImp = SeekerDataRepositoryImp(fakeDataSource)
    }


    @Test
    fun getSeekerById() = runBlockingTest{
        val x = seekerDataRepositoryImp.getRemoteSeekerById("2")
        assertThat(x, IsEqual(seeker1))
    }

    @Test
    fun addSeekerToFireStore() = runBlockingTest {
        seekerDataRepositoryImp.addNewSeekerIntoFireStore(CareSeeker("5"))
        assertThat(remoteList.size, `is`(3))
    }

    @Test
    fun deleteSeekerFromFireStore() = runBlockingTest {
        seekerDataRepositoryImp.deleteSeekerFromFireStore("2")
        assertThat(remoteList.size, `is`(1))
    }

    @Test
    fun getFilteredSeekersFromRoom()= runBlockingTest{
        val x = seekerDataRepositoryImp.getSeekers(seekerFilter) as Resource.Success
        assertThat(x.data,IsEqual(remoteList))
    }
}