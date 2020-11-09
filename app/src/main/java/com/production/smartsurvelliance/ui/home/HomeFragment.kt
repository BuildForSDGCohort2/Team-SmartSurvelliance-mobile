package com.production.smartsurvelliance.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.production.smartsurvelliance.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.ss_home_fragment, container, false)
      /*  val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



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