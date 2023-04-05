package com.example.mvvmbsnews.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mvvmbsnews.R
import com.example.mvvmbsnews.util.Constant.Companion.ON_BOARD_FINISHED_SHRED_KEY
import com.example.mvvmbsnews.util.Constant.Companion.ON_BOARD_SHARED_PREF_NAME
import kotlinx.coroutines.delay


class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        return  view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            goToOnBoardFragment()
        }
    }

    private suspend fun goToOnBoardFragment() {
        delay(3000)

        if (isOnBoardCompleted()){
            findNavController().navigate(R.id.action_splashFragment_to_breakingNewsFragment)

        }else{
            findNavController().navigate(R.id.action_splashFragment_to_breakingNewsFragment)

        }

    }


    fun isOnBoardCompleted() :Boolean{
        val  sharedPreferences = requireActivity().getSharedPreferences(ON_BOARD_SHARED_PREF_NAME,
            Context.MODE_PRIVATE)
        return  sharedPreferences.getBoolean(ON_BOARD_FINISHED_SHRED_KEY,false)

    }
}