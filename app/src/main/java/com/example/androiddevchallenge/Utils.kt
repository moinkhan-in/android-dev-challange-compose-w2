package com.example.androiddevchallenge

import java.util.concurrent.TimeUnit

fun Long.toHMS(): String {
    return "%02dh %02dm %02ds".format(
        TimeUnit.MILLISECONDS.toHours(this),
        TimeUnit.MILLISECONDS.toMinutes(this) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this)),
        TimeUnit.MILLISECONDS.toSeconds(this) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this))
    )
}