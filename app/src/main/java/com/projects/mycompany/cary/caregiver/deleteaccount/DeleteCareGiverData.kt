package com.projects.mycompany.cary.caregiver.deleteaccount

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.login.LoginActivity
import com.projects.mycompany.cary.utils.DELETE_ACCOUNT_TAG
import com.projects.mycompany.cary.utils.DELETE_ACCOUNT_VAL


class DeleteCareGiverData : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.hide()
        return inflater.inflate(R.layout.fragment_delete_care_giver_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toDoApp = (requireContext().applicationContext) as ToDoApplication
        val application = requireActivity().application
        val giverDataRepository = toDoApp.giverDataRepository
        val inboxDataRepository = toDoApp.inboxDataRepository
        val reviewsDataRepository = toDoApp.reviewsDataRepository
        val viewModelFactory = DeleteGiverVMFactory(giverDataRepository,inboxDataRepository,reviewsDataRepository,application)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(DeleteGiverDataVM::class.java)
        viewModel.deleteCompleted.observe(viewLifecycleOwner, Observer {
            if(it){
                val intent = Intent(activity, LoginActivity::class.java)
                intent.putExtra(DELETE_ACCOUNT_TAG, DELETE_ACCOUNT_VAL)
                startActivity(intent)
                requireActivity().finish()
            }
        })

    }

}
