package com.production.smartsurvelliance.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.production.smartsurvelliance.R

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.ss_dashboard_fragment, container, false)
        /*val image: ImageView = root.findViewById(R.id.imageView)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            image.toString() = it
        })*/
        return root
    }
}