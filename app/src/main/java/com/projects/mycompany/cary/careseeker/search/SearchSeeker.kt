package com.projects.mycompany.cary.careseeker.search




import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.databinding.FragmentNoInternetBinding
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.databinding.FragmentSearchSeekerBinding
import com.projects.mycompany.cary.utils.*


class SearchSeeker : Fragment() {

    private lateinit var binding: FragmentSearchSeekerBinding
    private lateinit var viewModel: SearchSeekerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)
        if(isNetworkAvailable(requireActivity())){
            inflater.inflate(R.layout.fragment_search_seeker,container,false)
            binding = FragmentSearchSeekerBinding.inflate(inflater)

            val app = (requireActivity()).application
            val seekerRepo = (requireContext().applicationContext as ToDoApplication).seekerDataRepository
            val giverRepo = (requireContext().applicationContext as ToDoApplication).giverDataRepository
            val viewModelFactory = SearchVmFactory(seekerRepo,giverRepo,app)
            viewModel = ViewModelProvider(this,viewModelFactory).get(SearchSeekerViewModel::class.java)

            val adapter = SearchAdapter(CareGiverListener {
                findNavController().navigate(SearchSeekerDirections.actionSearchSeekerToGiverDetails(it))
            })

            viewModel.fetchData.observe(viewLifecycleOwner, Observer {
                when(it){
                    is Resource.Loading -> {
                        binding.searchProgress.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        binding.searchProgress.visibility = View.INVISIBLE
                        adapter.submitList(it.data)
                    }

                    is Resource.Empty -> {
                        binding.searchRecycler.visibility = View.GONE
                        binding.searchProgress.visibility = View.INVISIBLE
                        binding.noDataText.visibility = View.VISIBLE
                        binding.noDataTextDetails.visibility = View.VISIBLE
                    }
                    is Resource.Failure -> {
                        Toast.makeText(activity,"An error has occurred:${it.throwable.message}",Toast.LENGTH_SHORT).show()
                    }
                }
            })

            binding.searchRecycler.adapter = adapter
            return binding.root
        }
        else{
            inflater.inflate(R.layout.fragment_no_internet,container,false)
            val binding = FragmentNoInternetBinding.inflate(inflater)
            return binding.root
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.filter_item)
            findNavController().navigate(SearchSeekerDirections.actionSearchSeekerToSeekerFilter())
        return true
    }
}
