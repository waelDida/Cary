package com.projects.mycompany.cary.careseeker.rategiver


import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.databinding.FragmentRateGiverBinding
import com.projects.mycompany.cary.utils.MISSING_INFO
import com.projects.mycompany.cary.utils.VALID_INFO
import com.projects.mycompany.cary.utils.displayMessage
import com.projects.mycompany.cary.utils.rateList

class RateGiverFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentRateGiverBinding
    private lateinit var viewModel: RateGiverViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_rate_giver, container, false)
        binding = FragmentRateGiverBinding.inflate(inflater)
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        val reviewedId = RateGiverFragmentArgs.fromBundle(requireArguments()).reviewedId
        val seekerRepository = (requireActivity().applicationContext as ToDoApplication).seekerDataRepository
        val reviewsRepository = (requireActivity().applicationContext as ToDoApplication).reviewsDataRepository
        val viewModelFactory = RateGiverVMFactory(reviewedId,seekerRepository,reviewsRepository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(RateGiverViewModel::class.java)
        binding.viewModel = viewModel

        ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item, rateList)
            .also {adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.rateSpinner.adapter = adapter
            }

        binding.rateSpinner.onItemSelectedListener = this

        viewModel.post.observe(viewLifecycleOwner, Observer {
            if(it){
                viewModel.onPostClicked()
                viewModel.setReviewText(binding.editReview.text.toString())
                viewModel.checkPostReview()
            }
        })

        viewModel.validInfo.observe(viewLifecycleOwner, Observer {
            when(it){
                MISSING_INFO -> displayMessage(binding.rateLayout,getString(R.string.missing_review))
                VALID_INFO -> displayMessage(binding.rateLayout, getString(R.string.review_sent))
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.post_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.post_item -> viewModel.onPostClick()
            android.R.id.home -> requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return true
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        viewModel.setRate(rateList[position])
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
       viewModel.setRate(rateList[0])
    }
}
