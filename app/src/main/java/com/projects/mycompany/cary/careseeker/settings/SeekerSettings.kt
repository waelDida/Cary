package com.projects.mycompany.cary.careseeker.settings



import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.projects.mycompany.cary.R
import com.projects.mycompany.cary.databinding.FragmentSeekerSettingsBinding
import com.projects.mycompany.cary.login.LoginActivity
import com.projects.mycompany.cary.preference.AppPreferences
import com.projects.mycompany.cary.utils.*


class SeekerSettings : Fragment() {

    private lateinit var binding: FragmentSeekerSettingsBinding
    private lateinit var packageName: String
    private lateinit var appLink: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_seeker_settings, container, false)
        binding = FragmentSeekerSettingsBinding.inflate(inflater)

        packageName = requireActivity().applicationContext.packageName
        appLink = "https://play.google.com/store/apps/details?id=+$packageName"

        val appPref = AppPreferences(requireActivity().application)


        binding.editProfileLayout.setOnClickListener {
            findNavController().navigate(SeekerSettingsDirections.actionSeekerSettingsToEditSeekerProfile())
        }

        binding.shareLayout.setOnClickListener {
           shareTheApp()
        }

        binding.rateLayout.setOnClickListener {
            rateTheApp()
        }

        binding.licencesLayout.setOnClickListener {
            showLicences(requireActivity())
        }

        binding.privacyLayout.setOnClickListener {
            showPrivacy()
        }

        binding.termsLayout.setOnClickListener {
            showTerms()
        }

        binding.signOut.setOnClickListener {
            appPref.initAppPreference()
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            requireActivity().finish()
        }

        binding.deleteAccountLayout.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.custom_delete_dialog)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val title = dialog.findViewById<TextView>(R.id.dialog_title)
            val text = dialog.findViewById<TextView>(R.id.dialog_text)
            val yesButton = dialog.findViewById<Button>(R.id.yes_dialog_button)
            val noButton = dialog.findViewById<Button>(R.id.no_dialog_button)
            title.text = getString(R.string.delete_account_text)
            text.text = getString(R.string.delete_text_confirmation)

            yesButton.setOnClickListener {
                dialog.dismiss()
                reAuthenticateDialog()
            }

            noButton.setOnClickListener {
                dialog.dismiss()
            }
            dialog.setCancelable(false)
            dialog.show()
        }

        return binding.root
    }

    private fun shareTheApp(){
        val intent = Intent()
        with(intent){
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT,appLink)
            startActivity(Intent.createChooser(this,"Share this app"))
        }
    }

    private fun rateTheApp(){
        val intent = Intent()
        with(intent){
            action = Intent.ACTION_VIEW
            data = Uri.parse(appLink)
            startActivity(this)
        }
    }

    private fun showPrivacy(){
        val intent = Intent()
        with(intent){
            action = Intent.ACTION_VIEW
            data = Uri.parse(PRIVACY_POLICY_LINK)
            startActivity(this)
        }
    }

    private fun showTerms(){
        val intent = Intent()
        with(intent){
            action = Intent.ACTION_VIEW
            data = Uri.parse(TERMS_LINK)
            startActivity(this)
        }
    }

    private fun reAuthenticateDialog(){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.enter_password_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val editPassword = dialog.findViewById<EditText>(R.id.edit_password)
        val confirm = dialog.findViewById<Button>(R.id.confirm_button)
        val cancel = dialog.findViewById<Button>(R.id.cancel_button)
        confirm.setOnClickListener {
            if(editPassword.text.toString().isNotEmpty()){
                val credential = EmailAuthProvider.getCredential(getCurrentUser()!!.email.toString(),editPassword.text.toString())
                getCurrentUser()!!.reauthenticate(credential).addOnCompleteListener {
                    if(it.isSuccessful){
                        dialog.dismiss()
                        findNavController().navigate(SeekerSettingsDirections.actionSeekerSettingsToDeleteCareSeekerData())
                    }
                }.addOnFailureListener {
                    dialog.dismiss()
                    Toast.makeText(requireContext(),getString(R.string.something_wrong_message),Toast.LENGTH_SHORT).show()
                }
            }
        }
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.show()

    }
}
