package com.production.smartsurvelliance.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.amplifyframework.auth.AuthChannelEventName
import com.amplifyframework.core.Amplify
import com.production.smartsurvelliance.R
import timber.log.Timber
import com.amplifyframework.auth.result.AuthSignInResult
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.InitializationStatus
import com.amplifyframework.core.model.query.Where
import com.amplifyframework.datastore.generated.model.ImageRecognitionStatus
import com.amplifyframework.datastore.generated.model.UserDetail
import com.amplifyframework.hub.HubChannel
import com.amplifyframework.hub.HubEvent

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
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