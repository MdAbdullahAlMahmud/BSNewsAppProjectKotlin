package com.example.mvvmbsnews.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.mvvmbsnews.R
import com.example.mvvmbsnews.databinding.FragmentAuthenticationBinding
import com.example.mvvmbsnews.util.Constant
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


class AuthenticationFragment : Fragment() {


    lateinit var  mAuth: FirebaseAuth
    lateinit var  binding: FragmentAuthenticationBinding
    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

    private  val  TAG= "GAuth"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthenticationBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        val  signInClient = GoogleSignIn.getClient(view.context,options)


        googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }

        binding.googleSignInCV.setOnClickListener {

            val signInIntent =  signInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)


        }

    }
    private  fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            Toast.makeText(context,"Name ${account.displayName} ==> ${account.email}",Toast.LENGTH_SHORT).show()

            Log.v("Information", "Account -> ${account.idToken}")
                connectWithFirebase(account.idToken!!)



            // Signed in successfully, show authenticated UI.
            // You can get user details from the account object
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(context,"Error ==> ${e.localizedMessage}",Toast.LENGTH_SHORT).show()

            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }


    private  fun  connectWithFirebase(idToken:String){
        val credential = GoogleAuthProvider.getCredential(idToken,null)
         mAuth.signInWithCredential(credential).addOnCompleteListener(
            OnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(context,"Logged In Successfully",Toast.LENGTH_SHORT).show()

                    findNavController().navigate(R.id.action_authenticationFragment_to_breakingNewsFragment)

                }else{
                    Toast.makeText(context,"Failed ${it.exception?.localizedMessage}",Toast.LENGTH_SHORT).show()

                    Log.v(TAG,"Error ------>" + it.exception?.localizedMessage)
                }
            })


        }


}