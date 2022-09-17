package com.projects.mycompany.cary.caregiver.offerdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.models.CareSeeker
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class OfferDetailVMFactory(private val careSeeker: CareSeeker): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(OfferDetailViewModel::class.java))
            return OfferDetailViewModel(careSeeker) as T
        throw IllegalArgumentException("Can't create the viewModel")
    }
}