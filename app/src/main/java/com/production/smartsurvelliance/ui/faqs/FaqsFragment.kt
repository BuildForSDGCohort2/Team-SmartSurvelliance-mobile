package com.production.smartsurvelliance.ui.faqs

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.production.smartsurvelliance.R

class FaqsFragment : Fragment() {

    companion object {
        fun newInstance() = FaqsFragment()
    }

    private lateinit var viewModel: FaqsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.faqs_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FaqsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}