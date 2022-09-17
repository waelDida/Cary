package com.projects.mycompany.cary.data.repositories.giversDataRepository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.projects.mycompany.cary.data.source.givers.FakeGiversDataSource
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.test.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GiverDataRepositoryImpTest{

    private lateinit var repository: GiverDataRepositoryImp
    private var remoteList = mutableListOf(giver1,giver2,giver3)
    private var sortedList = mutableListOf(giver3,giver1,giver2)


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createRepository(){
        val fakeDataSource = FakeGiversDataSource(remoteList)
        repository = GiverDataRepositoryImp(fakeDataSource)
    }

    @Test
    fun getGiverById() = runBlockingTest{
        val x = repository.getRemoteGiverById("2")
        assertThat(x,IsEqual(giver2))
    }

    @Test
    fun getSortedGivers() = runBlockingTest{
        val x = repository.getGivers(filter1) as Resource.Success
        assertThat(x.data,IsEqual(sortedList))

    }
}