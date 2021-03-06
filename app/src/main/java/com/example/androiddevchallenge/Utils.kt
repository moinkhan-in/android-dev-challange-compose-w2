package com.example.androiddevchallenge

import java.util.concurrent.TimeUnit

fun Long.toHMS(): String {
    val hours = TimeUnit.MILLISECONDS.toHours(this)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this))
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this))

    return when {
        hours > 0 -> "%02dh %02dm %02ds".format(hours, minutes, seconds)
        minutes > 0 -> "%02dm %02ds".format(minutes, seconds)
        else -> "%02ds".format(seconds)
    }
}