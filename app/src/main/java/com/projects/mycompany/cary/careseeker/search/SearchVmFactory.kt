package com.projects.mycompany.cary.careseeker.search

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class SearchVmFactory(private val seekerDataRepository: SeekerDataRepository,
                      private val giverDataRepository: GiverDataRepository,
                      val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if(modelClass.isAssignableFrom(SearchSeekerViewModel::class.java))
           return SearchSeekerViewModel(seekerDataRepository,giverDataRepository,app) as T
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}