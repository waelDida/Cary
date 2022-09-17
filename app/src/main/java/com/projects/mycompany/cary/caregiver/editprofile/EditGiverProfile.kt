package com.projects.mycompany.cary.caregiver.editprofile


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.ToDoApplication
import com.projects.mycompany.cary.databinding.FragmentEditGiverProfileBinding
import com.projects.mycompany.cary.preference.AppPreferences
import com.projects.mycompany.cary.utils.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


class EditGiverProfile : Fragment(),EasyPermissions.PermissionCallbacks {

    private lateinit var appPref: AppPreferences
    private lateinit var binding: FragmentEditGiverProfileBinding
    private lateinit var viewModel: EditGiverViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_edit_giver_profile, container, false)
        binding = FragmentEditGiverProfileBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModelFactory = EditGiverVMFactory((requireContext().applicationContext as ToDoApplication).giverDataRepository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(EditGiverViewModel::class.java)
        binding.viewModel = viewModel

        appPref = AppPreferences(requireActivity().application)

        viewModel.uploadImage.observe(viewLifecycleOwner, Observer {
            if(it){
                onUploadPhotoClick()
                viewModel.uploadImageClicked()
            }

        })

        viewModel.basicInfo.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(EditGiverProfileDirections.actionEditGiverProfileToGiverBasicInfo())
                viewModel.basicInfoClicked()
            }
        })

        viewModel.contactInfo.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(EditGiverProfileDirections.actionEditGiverProfileToGiverContactInfo())
                viewModel.contactInfoClicked()
            }
        })

        viewModel.job.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(EditGiverProfileDirections.actionEditGiverProfileToGiverJob())
                viewModel.jobClicked()
            }
        })

        viewModel.about.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(EditGiverProfileDirections.actionEditGiverProfileToAboutFragment())
                viewModel.aboutClicked()
            }
        })

        return binding.root
    }


    private fun onUploadPhotoClick(){
        if(Build.VERSION.SDK_INT >= 23)
            requestStoragePermission()
        else
            startUploadActivity()
    }

    private fun requestStoragePermission(){
        if(EasyPermissions.hasPermissions(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE))
            startUploadActivity()
        else
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.gallery_permission_text),
                MY_PERMISSION_REQUEST_GALLERY,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
    }

    private fun startUploadActivity(){
        val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, RC_CHOOSE_PHOTO)
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
        if(requestCode == RC_CHOOSE_PHOTO && resultCode == RESULT_OK){
            if(data == null || data.data == null){
                return
            }
            if(viewModel.isImgUrlNotEmpty())
                getDetailedStorageReference(viewModel.getImgUrl()).delete()
            uploadPhoto(activity,data.data,appPref.getCurrentUserType(),binding.giverPhoto,binding.progress, viewModel.careGiver.value,null)
        }

    }
}



//val intent = Intent()
  //      intent.type = "image/*"
    //    intent.action = Intent.ACTION_GET_CONTENT
      //  startActivityForResult(Intent.createChooser(intent, "Select Picture"), RC_CHOOSE_PHOTO)
