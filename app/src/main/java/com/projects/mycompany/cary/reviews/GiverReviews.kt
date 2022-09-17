package com.projects.mycompany.cary.reviews


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.resources.Resource
import com.projects.mycompany.cary.databinding.FragmentGiverReviewsBinding



class GiverReviews : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_giver_reviews, container, false)
        val binding = FragmentGiverReviewsBinding.inflate(inflater)
        val adapter =
            GiverReviewsAdapter()

        val careGiverId = GiverReviewsArgs.fromBundle(requireArguments()).giverId

        val viewModelFactory = GiverReviewsVMFactory(careGiverId, (requireContext().applicationContext as ToDoApplication).reviewsDataRepository)

        val viewModel = ViewModelProvider(this,viewModelFactory)
            .get(GiverReviewsViewModel::class.java)

        viewModel.fetchData.observe(viewLifecycleOwner, Observer {
            when(it){

                is Resource.Loading -> {
                    //loading
                }

                is Resource.Success -> {
                    adapter.submitList(it.data)
                }

                is Resource.Empty -> {
                    binding.reviewRecycler.visibility = View.GONE
                    binding.reviewsImg.visibility = View.VISIBLE
                    binding.noReviewsText.visibility = View.VISIBLE
                }

                is Resource.Failure -> {
                    Toast.makeText(activity,"An error has occurred: ${it.throwable.message}",Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.reviewRecycler.adapter = adapter
        return binding.root
    }


}
