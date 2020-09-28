package com.production.smartsurvelliance.helper

import android.app.Activity
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.content.getSystemService

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


private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val NOTIFICATION_BUZZ_PATTERN = longArrayOf(0, 2000)

enum class BuzzType(val pattern: LongArray) {
    NOTIFICATION(NOTIFICATION_BUZZ_PATTERN),
    COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN)

}