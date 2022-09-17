package com.projects.mycompany.cary.caregiver.search

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class SearchVMFactory(private val giverRepo: GiverDataRepository,
                      private val repository: SeekerDataRepository,
                      val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchGiverViewModel::class.java))
            return SearchGiverViewModel(giverRepo, repository,app) as T
        throw IllegalArgumentException("Unable to construct the viewModel")
    }
}