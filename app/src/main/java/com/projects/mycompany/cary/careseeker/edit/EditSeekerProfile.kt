package com.projects.mycompany.cary.careseeker.edit


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.databinding.FragmentEditSeekerProfileBinding
import com.projects.mycompany.cary.preference.AppPreferences
import com.projects.mycompany.cary.utils.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class EditSeekerProfile : Fragment(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: FragmentEditSeekerProfileBinding
    private lateinit var viewModel: EditSeekerViewModel
    private lateinit var appPref: AppPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_edit_seeker_profile, container, false)
        binding = FragmentEditSeekerProfileBinding.inflate(inflater)
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
        val viewModelFactory = EditSeekerVMFactory((requireActivity().applicationContext as ToDoApplication).seekerDataRepository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(EditSeekerViewModel::class.java)

        binding.viewModel = viewModel

        appPref = AppPreferences(requireActivity().application)

        viewModel.uploadImage.observe(viewLifecycleOwner, Observer {
            if(it){
                onUploadPhotoClick()
                viewModel.uploadImageClicked()
            }
        })

        viewModel.saveClick.observe(viewLifecycleOwner, Observer {
            if(it){
                viewModel.onSaveClicked()
                viewModel.setInfo(
                    binding.seekerFirstName.text.toString(),
                    binding.seekerLastName.text.toString(),
                    binding.seekerAddress.text.toString(),
                    binding.seekerPhoneNumber.text.toString())
                viewModel.checkAndSave()
            }
        })

        viewModel.validInfo.observe(viewLifecycleOwner, Observer {
            when(it){
                MISSING_INFO -> displayMessage(binding.editSeekerLayout,getString(R.string.fill_all_fields))
                MISSING_FIRST_NAME -> displayMessage(binding.editSeekerLayout,getString(R.string.missing_first_name))
                MISSING_LAST_NAME -> displayMessage(binding.editSeekerLayout,getString(R.string.missing_last_name))
                MISSING_ADDRESS -> displayMessage(binding.editSeekerLayout,getString(R.string.missing_address))
                MISSING_PHONE_NUMBER -> displayMessage(binding.editSeekerLayout,getString(R.string.missing_phone_number))
                VALID_INFO -> displayMessage(binding.editSeekerLayout, VALID_INFO)
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

    private fun onUploadPhotoClick(){
        if(Build.VERSION.SDK_INT >= 23)
            requestStoragePermission()
        else
           startUploadActivity()

    }

    private fun startUploadActivity(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, RC_CHOOSE_PHOTO)
    }

    private fun requestStoragePermission(){
        if(EasyPermissions.hasPermissions(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE)){
            startUploadActivity()
        }else{
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.gallery_permission_text),
                MY_PERMISSION_REQUEST_GALLERY,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms))
            AppSettingsDialog.Builder(requireActivity()).build().show()
        else
            requestStoragePermission()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        startUploadActivity()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == RC_CHOOSE_PHOTO && resultCode == Activity.RESULT_OK){
            if(data == null || data.data == null){
                return
            }
            uploadPhoto(activity,data.data,appPref.getCurrentUserType(),binding.seekerPhoto,binding.progress,null,viewModel.careSeeker.value)
        }
    }
}
