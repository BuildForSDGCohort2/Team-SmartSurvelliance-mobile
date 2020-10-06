package com.production.smartsurvelliance.ui.dialogs

import android.os.Bundle
import android.util.Log
import android.util.TimeUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.generated.model.ImageRecognitionStatus
import com.amplifyframework.datastore.generated.model.UserDetail
import com.production.smartsurvelliance.R
import com.production.smartsurvelliance.databinding.FragmentVerifyPictureBinding
import timber.log.Timber
import java.sql.Time
import kotlin.random.Random

class VerifyPictureFragment : DialogFragment() {

    private lateinit var viewModel: VerifyPictureViewModel
    private lateinit var binding: FragmentVerifyPictureBinding
    private lateinit var imageUrl: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_verify_picture,
            container,
            false
        )

        val bundle = arguments
        bundle?.let {
            imageUrl = it.getString("IMAGE_URL", null)
            binding.imageData = imageUrl
        }

        binding.declineButton.setOnClickListener {
            updateImageVerification(imageUrl, false, Random(34).nextDouble().toString())
            dismiss()
        }

        binding.acceptButton.setOnClickListener {
            updateImageVerification(imageUrl, true, Random(34).nextDouble().toString())
            dismiss()
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(VerifyPictureViewModel::class.java)
        // TODO: Use the ViewModel
    }

    companion object {
        var playRingTone : Boolean = false

        // TODO: Customize parameters
        fun newInstance(_playRingTone: Boolean = false): VerifyPictureFragment {

            playRingTone = _playRingTone

            val newDialogInstance =
                VerifyPictureFragment()
            newDialogInstance.isCancelable = false

            return newDialogInstance
        }
    }

    private fun updateImageVerification(imageUrl: String, isRecognised: Boolean, individualsName: String) {

        if(Amplify.Auth.currentUser != null) {
            val currentUser = Amplify.Auth.currentUser.username


            Amplify.DataStore.query(
                ImageRecognitionStatus::class.java, Where.matches(ImageRecognitionStatus.USER_ID.eq(currentUser)),
                { matches ->

                    while (matches.hasNext()) {
                        val oldValue = matches.next()

                        Timber.d(oldValue.individualsName )
                        Timber.d(individualsName)

                        if(oldValue.individualsName == individualsName) {
                            //Prompt user to change name
                            return@query
                        }
                    }

                    val imageVerificationStatus = ImageRecognitionStatus.builder()
                        .userId(currentUser)
                        .isRecognised(isRecognised)
                        .imageUrl(imageUrl)
                        .individualsName(individualsName)
                        .build()

                    Amplify.DataStore.save(imageVerificationStatus,
                        { Timber.tag("MyAmplifyApp").d("Created a Image Verification Status.${it}", ) },
                        { Timber.tag("MyAmplifyApp").e("Created failed: ${it}") }
                    )

                },
                { Log.e("MyAmplifyApp", "Query failed.", it) }
            )

        } else {
            Toast.makeText(requireContext(), "User is not Authenticated to Save Verification Status", Toast.LENGTH_LONG).show()
        }
    }


}