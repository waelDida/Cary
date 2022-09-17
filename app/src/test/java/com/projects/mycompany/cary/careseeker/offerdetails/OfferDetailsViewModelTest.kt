package com.projects.mycompany.cary.careseeker.offerdetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.projects.mycompany.cary.getOrAwaitValue
import com.projects.mycompany.cary.utils.MISSING_OFFER_DESCRIPTION
import com.projects.mycompany.cary.utils.MISSING_OFFER_TITLE
import com.projects.mycompany.cary.utils.VALID_INFO
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class OfferDetailsViewModelTest{

    private lateinit var viewModel: OfferDetailsViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel(){
      //  viewModel = OfferDetailsViewModel()
    }

    @Test
    fun initTest(){
    }

    @Test
    fun validInfo(){
        viewModel.setOfferDetails("test"," test ")
        viewModel.validate()
        assertThat(viewModel.validInfo.getOrAwaitValue(),`is`(VALID_INFO))
        assertThat(viewModel.offerDescription.getOrAwaitValue(),`is`("test"))
    }

    @Test
    fun testMissingTitle(){
        viewModel.setOfferDetails("","test")
        viewModel.validate()
        assertThat(viewModel.validInfo.getOrAwaitValue(),`is`(MISSING_OFFER_TITLE))
    }

    @Test
    fun testMissingDescription(){
        viewModel.setOfferDetails("test","")
        viewModel.validate()
        assertThat(viewModel.validInfo.getOrAwaitValue(),`is`(MISSING_OFFER_DESCRIPTION))
    }
}