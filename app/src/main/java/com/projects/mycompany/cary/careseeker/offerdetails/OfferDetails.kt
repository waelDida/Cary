package com.projects.mycompany.cary.careseeker.offerdetails


import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.databinding.FragmentOfferDetailsBinding
import com.projects.mycompany.cary.utils.*


class OfferDetails : Fragment(), AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {

    private lateinit var binding : FragmentOfferDetailsBinding
    private lateinit var viewModel: OfferDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_offer_details, container, false)
        binding = FragmentOfferDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        val viewModelFactory = OfferDetailsVMFactory((requireContext().applicationContext as ToDoApplication).seekerDataRepository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(OfferDetailsViewModel::class.java)
        binding.viewModel = viewModel

        ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item, categoriesList)
            .also {adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.jobDetailsSpinner.adapter = adapter
            }

        binding.jobDetailsSpinner.onItemSelectedListener = this
        binding.statusSwitch.setOnCheckedChangeListener(this)

        viewModel.postClick.observe(viewLifecycleOwner, Observer {
            if(it){
                viewModel.setOfferDetails(
                    binding.jobTitleEdit.text.toString(),
                    binding.jobDescriptionEdit.text.toString())
                viewModel.validate()
                viewModel.onPostClicked()
            }
        })

        viewModel.mProgressStatus.observe(viewLifecycleOwner, Observer {
            if(it)
                binding.offerDetailsprogress.visibility = View.VISIBLE
            else
                binding.offerDetailsprogress.visibility = View.GONE
        })

        viewModel.validInfo.observe(viewLifecycleOwner, Observer {
            when(it){
                MISSING_INFO -> displayMessage(binding.jobDetailsLayout,getString(R.string.fill_all_fields))
                MISSING_OFFER_TITLE -> displayMessage(binding.jobDetailsLayout,getString(R.string.missing_offer_title))
                MISSING_OFFER_DESCRIPTION -> displayMessage(binding.jobDetailsLayout,getString(R.string.missing_offer_description))
                VALID_INFO -> displayMessage(binding.jobDetailsLayout, getString(R.string.saved))
            }
        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.save_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_item -> viewModel.onPostClick()
            android.R.id.home -> requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return true
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        viewModel.setCategory(categoriesList[0])
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        viewModel.setCategory(categoriesList[position])
    }

    override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
        viewModel.setSearchingStatus(isChecked)
    }


}
