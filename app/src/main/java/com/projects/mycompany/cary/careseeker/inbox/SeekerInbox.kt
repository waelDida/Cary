package com.projects.mycompany.cary.careseeker.inbox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.databinding.FragmentNoInternetBinding
import com.projects.mycompany.cary.databinding.FragmentSeekerInboxBinding
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.utils.isNetworkAvailable
import java.util.*


class SeekerInbox : Fragment() {
    private lateinit var binder: FragmentSeekerInboxBinding
    private lateinit var adapter: SeekerInboxAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        if(!actionBar?.isShowing!!)
            actionBar.show()

        if(isNetworkAvailable(requireActivity())){
            inflater.inflate(R.layout.fragment_seeker_inbox, container, false)
            binder = FragmentSeekerInboxBinding.inflate(inflater)

            val giverRepo = (requireActivity().applicationContext as ToDoApplication).giverDataRepository
            val seekerRepo = (requireActivity().applicationContext as ToDoApplication).seekerDataRepository
            val viewModelFactory = SeekerInboxVMFactory(giverRepo,seekerRepo)
            val viewModel = ViewModelProvider(this,viewModelFactory).get(SeekerInboxViewModel::class.java)
            adapter = SeekerInboxAdapter(CareGiverListener {
                findNavController().navigate(SeekerInboxDirections.actionSeekerInboxToSeekerChat(it))
            })

            viewModel.fetchData.observe(viewLifecycleOwner, Observer {
                when(it){
                    is Resource.Loading ->{

                    }

                    is Resource.Success ->{
                        adapter.submitList(it.data)
                        searchByName(it.data)
                    }

                    is Resource.Empty -> {
                        binder.seekerInboxRecycler.visibility = View.GONE
                        binder.noMessageImg.visibility = View.VISIBLE
                        binder.noMessageText.visibility = View.VISIBLE
                    }

                    is Resource.Failure -> {
                        Toast.makeText(activity,"An error has occurred:${it.throwable.message}", Toast.LENGTH_SHORT).show()
                    }

                }
            })

            binder.seekerInboxRecycler.adapter = adapter

            removeBadge()

            return binder.root
        }
        else{
            inflater.inflate(R.layout.fragment_no_internet,container, false)
            val binding = FragmentNoInternetBinding.inflate(inflater)
            return binding.root
        }

    }

    private  fun removeBadge() {
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.removeBadge(R.id.seekerInbox)
    }

    private fun searchByName(list: MutableList<CareGiver>){
        binder.searchInbox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(editable: String?): Boolean {
                val filteredList = mutableListOf<CareGiver>()
                for(careGiver in list)
                    if(careGiver.firstName.toLowerCase(Locale.getDefault()).contains(editable.toString().toLowerCase(
                            Locale.getDefault())))
                        filteredList.add(careGiver)
                adapter.submitList(filteredList)
                return true
            }
        })
    }
}
