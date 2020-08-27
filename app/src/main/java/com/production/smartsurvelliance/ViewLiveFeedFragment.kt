package com.production.smartsurvelliance

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ViewLiveFeedFragment : Fragment() {

    companion object {
        fun newInstance() = ViewLiveFeedFragment()
    }

    private lateinit var viewModel: ViewLiveFeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_live_feed, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ViewLiveFeedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}