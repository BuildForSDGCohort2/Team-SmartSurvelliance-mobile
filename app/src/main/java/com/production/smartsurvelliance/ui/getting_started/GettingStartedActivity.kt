package com.production.smartsurvelliance.ui.getting_started

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.production.smartsurvelliance.MainActivity
import com.production.smartsurvelliance.R
import com.production.smartsurvelliance.SplashScreen
import com.production.smartsurvelliance.adapters.WelcomeAdapter
import com.production.smartsurvelliance.helper.PREF_FILE
import com.production.smartsurvelliance.helper.PREF_IS_FIRST_TIME
import com.production.smartsurvelliance.helper.saveProfileLanguage
import kotlinx.android.synthetic.main.activity_getting_started.*
import timber.log.Timber

class GettingStartedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_getting_started)

        supportActionBar?.hide()

        val sharedPrefs = getSharedPreferences(PREF_FILE ,
            Context.MODE_PRIVATE
        )

        val isFirstTime = sharedPrefs.getBoolean(PREF_IS_FIRST_TIME,true)

        if (!isFirstTime) {
            startActivity(Intent(this, SplashScreen::class.java))
            finish()
        }




        viewPager.adapter = WelcomeAdapter(this) {
            Timber.d("Called Paging")
            sharedPrefs.saveProfileLanguage(it)
//            Toast.makeText(this, "Your default language set to ${this.getProfileLanguage()}", Toast.LENGTH_LONG).show()
            viewPager.currentItem = viewPager.currentItem + 1

        }
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Timber.d("Page $position")

                if(position == 0) {
                    viewPager.isUserInputEnabled = false
                    skipTexView.visibility = View.INVISIBLE
                    nextTextView.visibility = View.INVISIBLE
                }else {
                    viewPager.isUserInputEnabled = true
                    skipTexView.visibility = View.VISIBLE
                    nextTextView.visibility = View.VISIBLE
                }

                switchDots(position)
            }
        })

        nextTextView.setOnClickListener {
            when(viewPager.currentItem) {
                3 -> {
                    // Do nothing
                    val intent = Intent(this, SplashScreen::class.java)
//                    with(sharedPrefs.edit()!!) {
//                        putBoolean(PREF_IS_FIRST_TIME,false)
//                        apply()
//                    }
                    startActivity(intent)
                    finish()
                }

                1,2 -> {
                    skipTexView.visibility = View.VISIBLE
                    switchDots(viewPager.currentItem + 1)
                    viewPager.currentItem = viewPager.currentItem + 1
                }
            }
        }

        skipTexView.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            with(sharedPrefs.edit()!!) {
                putBoolean(PREF_IS_FIRST_TIME,false)
                apply()
            }
            startActivity(intent)

            finish()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun switchDots(position:Int) {

        when(position) {

            0 ->{
                dot_one.setBackgroundResource(R.drawable.round_dot_selected)
                dot_two.setBackgroundResource(R.drawable.round_dot_unselected)
                dot_three.setBackgroundResource(R.drawable.round_dot_unselected)
                dot_four.setBackgroundResource(R.drawable.round_dot_unselected)

            }

            1 ->{
                dot_one.setBackgroundResource(R.drawable.round_dot_unselected)
                dot_two.setBackgroundResource(R.drawable.round_dot_selected)
                dot_three.setBackgroundResource(R.drawable.round_dot_unselected)
                dot_four.setBackgroundResource(R.drawable.round_dot_unselected)

            }

            2 ->{
                dot_one.setBackgroundResource(R.drawable.round_dot_unselected)
                dot_two.setBackgroundResource(R.drawable.round_dot_unselected)
                dot_three.setBackgroundResource(R.drawable.round_dot_selected)
                dot_four.setBackgroundResource(R.drawable.round_dot_unselected)

            }

            3 ->{
                dot_one.setBackgroundResource(R.drawable.round_dot_unselected)
                dot_two.setBackgroundResource(R.drawable.round_dot_unselected)
                dot_three.setBackgroundResource(R.drawable.round_dot_unselected)
                dot_four.setBackgroundResource(R.drawable.round_dot_selected)

            }
        }
    }

}