package com.example.androiddevchallenge.viewModels

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androiddevchallenge.ui.components.FlipState
import com.example.androiddevchallenge.ui.components.toOpposite
import java.util.concurrent.TimeUnit


class AppViewModel : ViewModel() {

    val h0Obs = MutableLiveData(FlipState.getInstance(0))
    val h1Obs = MutableLiveData(FlipState.getInstance(0))

    val m0Obs = MutableLiveData(FlipState.getInstance(0))
    val m1Obs = MutableLiveData(FlipState.getInstance(0))

    val s0Obs = MutableLiveData(FlipState.getInstance(0))
    val s1Obs = MutableLiveData(FlipState.getInstance(0))

    val isTimerRunning = MutableLiveData(false)

    var countDownTimer: CountDownTimer? = null
    var timeInMillis: MutableLiveData<Long> = MutableLiveData(0)

    fun startTimer() {
        isTimerRunning.value = true
        countDownTimer = object : CountDownTimer(timeInMillis.value ?: 0, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeInMillis.value = millisUntilFinished
                updateTimer()
            }

            override fun onFinish() {
                isTimerRunning.value = false
                timeInMillis.value = 0
                updateTimer()
            }
        }
        countDownTimer?.start()
    }

    fun updateTimer() {

        val newTime = timeInMillis.value ?: 0
        val minute = "%02d".format(TimeUnit.MILLISECONDS.toMinutes(newTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(newTime)))
        val second = "%02d".format(TimeUnit.MILLISECONDS.toSeconds(newTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(newTime)))
        val hour = "%02d".format(TimeUnit.MILLISECONDS.toHours(newTime))

        val h0 = hour[0].toString().toInt()
        val h1 = hour[1].toString().toInt()

        val m0 = minute[0].toString().toInt()
        val m1 = minute[1].toString().toInt()

        val s0 = second[0].toString().toInt()
        val s1 = second[1].toString().toInt()

        if (h0Obs.value?.newValue != h0) {
            h0Obs.value = h0Obs.value?.toOpposite(h0)
        }

        if (h1Obs.value?.newValue != h1) {
            h1Obs.value = h1Obs.value?.toOpposite(h1)
        }

        if (m0Obs.value?.newValue != m0) {
            m0Obs.value = m0Obs.value?.toOpposite(m0)
        }

        if (m1Obs.value?.newValue != m1) {
            m1Obs.value = m1Obs.value?.toOpposite(m1)
        }

        if (s0Obs.value?.newValue != s0) {
            s0Obs.value = s0Obs.value?.toOpposite(s0)
        }

        if (s1Obs.value?.newValue != s1) {
            s1Obs.value = s1Obs.value?.toOpposite(s1)
        }
    }

    fun resetTimer() {
        countDownTimer?.cancel()
        timeInMillis.value = 0
        isTimerRunning.value = false
        updateTimer()
    }

    fun updateNewTime(time: Long) {
        timeInMillis.value = time
        updateTimer()
    }
}
