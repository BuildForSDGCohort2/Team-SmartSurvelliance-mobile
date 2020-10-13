package com.production.smartsurvelliance.helper

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.getSystemService
import com.production.smartsurvelliance.R

/**
 * Given a pattern, this method makes sure the device buzzes
 */
fun buzz(pattern: LongArray, activity: Activity) {
    val buzzer =  activity.getSystemService<Vibrator>()
    buzzer?.let {
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
        } else {
            //deprecated in API 26
            buzzer.vibrate(pattern, -1)
        }
    }
}

object GettingStartedData {
    fun getData(context: Context): ArrayList<Triple<String, String, Int>> {

        val titles = context.resources.getStringArray(R.array.titles)
        val descriptions = context.resources.getStringArray(R.array.descriptions)

        val items = ArrayList<Triple<String,String,Int>>()
        items.add(Triple(titles[0],descriptions[0], R.drawable.ic_logo))
        items.add(Triple(titles[1],descriptions[1],R.drawable.ic_logo))
        items.add(Triple(titles[2],descriptions[2],R.drawable.ic_logo))
        return items
    }
}


private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val NOTIFICATION_BUZZ_PATTERN = longArrayOf(0, 2000)

enum class BuzzType(val pattern: LongArray) {
    NOTIFICATION(NOTIFICATION_BUZZ_PATTERN),
    COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN)

}