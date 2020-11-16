package com.production.smartsurvelliance.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.production.smartsurvelliance.R
import com.production.smartsurvelliance.model.CameraFeedData


/*Adapter used to display a Horizontal list of camera feed items*/

class HomeRecyclerViewAdapter (private val cameraFeedList: List<CameraFeedData>?, context: Context?) : RecyclerView.Adapter<HomeRecyclerViewHolder>()  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerViewHolder {
        val layoutId = R.layout.ss_camera_feed_view
        val layoutView = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return HomeRecyclerViewHolder(layoutView)
    }


    override fun onBindViewHolder(holder: HomeRecyclerViewHolder, position: Int) {
        if(cameraFeedList != null && position < cameraFeedList.size){
            val camera = cameraFeedList[position]
            holder.feedStatus.text = camera.status
            holder.cameraDetails.text = camera.details
            /*holder.videoView.setVideoURI() = camera.videoView*/
        }

    }

    override fun getItemCount(): Int {
        return cameraFeedList?.size ?: 0
    }

}



class HomeRecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val feedStatus: TextView = itemView.findViewById(R.id.connection_status)
    val cameraDetails: TextView = itemView.findViewById(R.id.camera_details)
    val videoView: VideoView = itemView.findViewById(R.id.video_view)

}