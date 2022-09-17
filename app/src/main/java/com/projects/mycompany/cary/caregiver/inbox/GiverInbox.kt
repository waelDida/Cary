package com.projects.mycompany.cary.caregiver.inbox

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
import com.projects.mycompany.cary.databinding.FragmentGiverInboxBinding
import com.projects.mycompany.cary.databinding.FragmentNoInternetBinding
import com.projects.mycompany.cary.models.CareSeeker
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.utils.isNetworkAvailable
import java.util.*


class GiverInboxFragment : Fragment() {

    private lateinit var binding: FragmentGiverInboxBinding
    private lateinit var adapter: GiverInboxAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        if(!actionBar?.isShowing!!)
            actionBar.show()

        if(isNetworkAvailable(requireActivity())){
            inflater.inflate(R.layout.fragment_giver_inbox, container, false)
            binding = FragmentGiverInboxBinding.inflate(inflater)

            val seekerRepo = (requireActivity().applicationContext as ToDoApplication).seekerDataRepository
            val giverRepo = (requireActivity().applicationContext as ToDoApplication).giverDataRepository

            val viewModelFactory = GiverInboxVMFactory(seekerRepo,giverRepo)
            val viewModel = ViewModelProvider(this,viewModelFactory).get(GiverInboxViewModel::class.java)
            adapter = GiverInboxAdapter(CareSeekerListener {
                findNavController().navigate(GiverInboxFragmentDirections.actionGiverInboxFragmentToGiverChat(it))
            })

            viewModel.fetchData.observe(viewLifecycleOwner, Observer {
                when(it){
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        adapter.submitList(it.data)
                        searchByName(it.data)
                    }
                    is Resource.Empty -> {
                        binding.inboxRecycler.visibility = View.GONE
                        binding.noMessageImg.visibility = View.VISIBLE
                        binding.noMessageText.visibility = View.VISIBLE
                    }

                    is Resource.Failure -> {
                        Toast.makeText(activity,"An error has occurred:${it.throwable.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            })

            binding.inboxRecycler.adapter = adapter

            removeBadge()

            return binding.root
        }
        else{
            inflater.inflate(R.layout.fragment_no_internet,container,false)
            val binding = FragmentNoInternetBinding.inflate(inflater)
            return binding.root
        }

    }

    private  fun removeBadge() {
       val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.removeBadge(R.id.giverInboxFragment)
    }

    private fun searchByName(list: MutableList<CareSeeker>){
        binding.searchInbox.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(editable: String?): Boolean {
                val filteredList = mutableListOf<CareSeeker>()
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
