package com.projects.mycompany.cary.caregiver.about


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.databinding.FragmentAboutBinding
import com.projects.mycompany.cary.utils.MISSING_INFO
import com.projects.mycompany.cary.utils.VALID_INFO
import com.projects.mycompany.cary.utils.displayMessage


class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding
    private lateinit var viewModel: AboutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_about, container, false)
        binding = FragmentAboutBinding.inflate(inflater)
        val viewModelFactory = AboutVMFactory((requireContext().applicationContext as ToDoApplication).giverDataRepository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(AboutViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        viewModel.saveClick.observe(viewLifecycleOwner, Observer {
            if(it){
                viewModel.onSaveClicked()
                viewModel.setInfo(binding.caregiverAboutText.text.toString())
                viewModel.checkAndValidate()
            }
        })

        viewModel.validInfo.observe(viewLifecycleOwner, Observer {
            when(it){
                MISSING_INFO -> displayMessage(binding.aboutConstraintLayout,getString(R.string.fill_all_fields))
                VALID_INFO -> displayMessage(binding.aboutConstraintLayout, getString(R.string.saved))
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
            R.id.save_item -> viewModel.onSaveClick()
            android.R.id.home -> requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return true
    }


}
