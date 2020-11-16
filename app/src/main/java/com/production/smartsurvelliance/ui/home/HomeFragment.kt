package com.production.smartsurvelliance.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.production.smartsurvelliance.R
import com.production.smartsurvelliance.adapters.HomeRecyclerViewAdapter
import com.production.smartsurvelliance.model.CameraFeedData
import kotlinx.android.synthetic.main.ss_home_fragment.*
import kotlinx.android.synthetic.main.ss_home_fragment.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private  var cameraFeedList = mutableListOf<CameraFeedData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment with the Home theme
        val view = inflater.inflate(R.layout.ss_home_fragment, container, false)

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        //Setup RecyclerView
        view.recycler_view.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)

        view.recycler_view.layoutManager = layoutManager
        populateCameraFeedList()

       /* val adapter = HomeRecyclerViewAdapter(cameraFeedList,context)
        recycler_view.adapter = adapter*/
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = HomeRecyclerViewAdapter(cameraFeedList,context)
        recycler_view.adapter = adapter

    }
     /*Function to be removed*/
    private fun populateCameraFeedList(){
         val camera1 = CameraFeedData("Live","St 23 MountView")
         val camera2 = CameraFeedData("Offline", "20th Ibadan Str")
         val camera3 = CameraFeedData("Live", "Obieri Plaza")
         val camera4 = CameraFeedData("Offline", "12th Lane Lagos Str")

         cameraFeedList.add(camera1)
         cameraFeedList.add(camera2)
         cameraFeedList.add(camera3)
         cameraFeedList.add(camera4)

    }

    /*Function to check status of video feed and return while changing the drawable colour to either red or green*/


//    fun getPost() {
//        Amplify.DataStore.query(Post::class.java,
//            { result -> Timber.i("Posts retrieved successfully") },
//            { error -> Timber.tag("Error retrieving posts").e(  error) }
//        )

//        Amplify.DataStore.query(
//            Comment::class.java, Where.matches(Post.STATUS.eq(PostStatus.ACTIVE)),
//            {
//                while (it.hasNext()) {
//                    val comment: Comment = it.next()
//                    val content: String = comment.content
//                    Log.i("MyAmplifyApp", "Content: $content")
//                }
//            },
//            { Log.e("MyAmplifyApp", "Query failed.", it) }
//        )
// }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        Timber.d(data.toString())
//
//        if (data?.data != null && "myapp" == data.data!!.scheme) {
//            Timber.d("handleWebSigninResponse")
//            Amplify.Auth.handleWebUISignInResponse(data)
//        }
//    }


}