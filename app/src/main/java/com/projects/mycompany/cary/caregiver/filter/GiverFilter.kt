package com.projects.mycompany.cary.caregiver.filter


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.databinding.FragmentGiverFilterBinding


class GiverFilter : Fragment(), SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    private lateinit var viewModel: GiverFilterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_giver_filter, container, false)
        val binding = FragmentGiverFilterBinding.inflate(inflater)

        setHasOptionsMenu(true)

        val app = requireActivity().application
        viewModel = ViewModelProvider(this,GiverFilterVMFactory(app)).get(GiverFilterViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.saveClick.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(GiverFilterDirections.actionGiverFilterToSearchGiver())
                viewModel.onSaveClicked()
            }
        })

        binding.seekBar.setOnSeekBarChangeListener(this)
        binding.filterSwitch.setOnCheckedChangeListener(this)

        return binding.root

    }

    override fun onProgressChanged(p0: SeekBar?, value: Int, p2: Boolean) {
        if(p2)
            viewModel.setDistance(value)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

    }

    override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
        viewModel.setCheckStatus(isChecked)
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
