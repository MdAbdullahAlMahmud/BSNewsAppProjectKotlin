package com.example.mvvmbsnews.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.mvvmbsnews.R
import com.example.mvvmbsnews.adapter.OnBoardAdapter
import com.example.mvvmbsnews.model.OnBoardITem
import com.example.mvvmbsnews.util.Constant.Companion.ON_BOARD_FINISHED_SHRED_KEY
import com.example.mvvmbsnews.util.Constant.Companion.ON_BOARD_SHARED_PREF_NAME
import com.google.android.material.button.MaterialButton
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator


class OnBoardFragment : Fragment() {

    lateinit var viewPager2 : ViewPager2
    lateinit var  actionButton : MaterialButton

    lateinit var adapter : OnBoardAdapter
    lateinit var indicator : WormDotsIndicator

    val LAST_ONBOARD_POSITION = 2
    var currentPosition = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)


        var  onBoardList = ArrayList<OnBoardITem>()

        var firstItem = OnBoardITem("Breaking News", R.drawable.onboarding_picture_one, "Get the latest news headline of your country in one place. See the details of every news headline")
        var secondItem = OnBoardITem("Search News", R.drawable.onboarding_picture_two, "Want to know specific topic news ? We are here to give the all news based on your search keyword")
        var thirdItem = OnBoardITem("Favourites News", R.drawable.onboarding_picture_three, "No worries to lost your favourite news . We have favourite section to saved your favourite news")


        onBoardList.add(firstItem)
        onBoardList.add(secondItem)
        onBoardList.add(thirdItem)

        adapter = OnBoardAdapter(onBoardList)
        viewPager2.adapter =adapter
        setUpIndicator()

        viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                currentPosition = position

                if (currentPosition == LAST_ONBOARD_POSITION){
                    actionButton.text = " Finish"
                }else actionButton.text = "Next"
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(position: Int,
                                        positionOffset: Float,
                                        positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })

        actionButton.setOnClickListener{
            if (currentPosition <2){
                currentPosition ++
                viewPager2.setCurrentItem(currentPosition)
            }else{
                onBoardFinished()
                findNavController().navigate(R.id.action_onBoardFragment_to_breakingNewsFragment)
            }
        }

    }

    private fun setUpIndicator() {
        indicator.attachTo(viewPager2)
    }


    private fun  onBoardFinished(){

        val  sharedPreferences = requireActivity().getSharedPreferences(ON_BOARD_SHARED_PREF_NAME,
            Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putBoolean(ON_BOARD_FINISHED_SHRED_KEY,true).apply()

    }
    fun  init(view: View){
        viewPager2 = view.findViewById(R.id.viewPager)
        indicator = view.findViewById(R.id.worm_dots_indicator)
        actionButton = view.findViewById(R.id.actionButton)
    }

}