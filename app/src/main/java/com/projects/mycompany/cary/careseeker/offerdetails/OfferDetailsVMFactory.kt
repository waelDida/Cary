package com.projects.mycompany.cary.careseeker.offerdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class OfferDetailsVMFactory(private val repository: SeekerDataRepository):  ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(OfferDetailsViewModel::class.java))
            return OfferDetailsViewModel(repository) as T
        throw IllegalArgumentException("Can't create the viewModel")
    }
}