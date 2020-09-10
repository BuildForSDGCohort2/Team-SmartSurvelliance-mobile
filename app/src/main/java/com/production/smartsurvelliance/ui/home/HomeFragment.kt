package com.production.smartsurvelliance.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

    //fun signIn(view: View?) {
//        Amplify.Auth.signInWithWebUI(
//            requireActivity(),
//            { result: AuthSignInResult -> Timber.d( result.toString()) },
//            { error: AuthException -> Timber.d( error.toString()) }
//        )
//        Amplify.Auth.signUp(
//            "username",
//            "Password123",
//            AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), "my@email.com").build(),
//            { result -> Timber.tag("AuthQuickStart").d("Result: $result") },
//            { error -> Timber.tag("AuthQuickStart").d("Sign up failed $error") })

//        fun signIn(view: View?) {
//            Timber.d("Initiate Signin Sequence")
//
//            Amplify.Auth.fetchAuthSession(
//                { result -> Timber.d( result.toString()) },
//                { error -> Timber.e( error.toString()) }
//            )
//
//            Amplify.Auth.signInWithWebUI(
//                requireActivity(),
//                { result: AuthSignInResult ->
//                    Timber.d( result.toString())
//
//                    // fetch session details (incl token)
//                    Amplify.Auth.fetchAuthSession(
//                        { result -> Timber.d( result.toString()) },
//                        { error -> Timber.d( error.toString()) }
//                    )
//
//                    // get user details
//                    val user = Amplify.Auth.currentUser
//                    Timber.d( user.toString() )
//
//                },
//                { error: AuthException -> Timber.d( error.toString()) }
//            )
//
//        }


    //}
}