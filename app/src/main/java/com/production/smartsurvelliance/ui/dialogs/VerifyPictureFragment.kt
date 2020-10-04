package com.production.smartsurvelliance.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.production.smartsurvelliance.R
import com.production.smartsurvelliance.databinding.FragmentVerifyPictureBinding

class VerifyPictureFragment : DialogFragment() {

    private lateinit var viewModel: VerifyPictureViewModel
    private lateinit var binding: FragmentVerifyPictureBinding
    private lateinit var imageUrl: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bundle = arguments
        bundle?.let {
            imageUrl = it.getString("IMAGE_URL", null)

            binding.imageData = imageUrl


//            if (imageUrl != null){
//                binding.imageData = imageUrl
//            } else {
//                binding.imageData = "https://fetch-mc.s3.amazonaws.com/fetchmc/Wasse-picture-B13.jpg"
//            }
        }

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_verify_picture,
            container,
            false
        )



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

}