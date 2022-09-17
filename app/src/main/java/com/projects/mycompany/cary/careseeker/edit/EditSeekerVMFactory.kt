package com.projects.mycompany.cary.careseeker.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepositoryImp
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class EditSeekerVMFactory(private val repository: SeekerDataRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EditSeekerViewModel::class.java))
            return EditSeekerViewModel(repository) as T
        throw IllegalArgumentException("Can't create the viewModel")
    }
}