/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.viewModels.AppViewModel

sealed class FlipState(val newValue: Int) {
    class Front(temp: Int) : FlipState(newValue = temp)
    class Back(temp: Int) : FlipState(newValue = temp)

    companion object {
        fun getInstance(newValue: Int): FlipState {
            return Front(newValue)
        }
    }
}

fun FlipState.toOpposite(newValue: Int): FlipState {
    return when (this) {
        is FlipState.Front -> FlipState.Back(newValue)
        is FlipState.Back -> FlipState.Front(newValue)
    }
}

@Composable
private fun TimerDigitAnimated(
    modifier: Modifier = Modifier,
    flipState: State<FlipState?> = mutableStateOf(FlipState.Front(0) as FlipState)
) {

    val displayableDigit = remember { mutableStateOf(flipState.value?.newValue) }
    val transition = updateTransition(flipState.value)
    val rotationX by transition.animateFloat(
        transitionSpec = {
            if (initialState is FlipState.Front) {
                repeatable(
                    iterations = 1,
                    animation = keyframes {
                        durationMillis = 1000
                        0f at 0
                        180f at durationMillis
                    }
                )
            } else {
                repeatable(
                    iterations = 1,
                    animation = keyframes {
                        durationMillis = 1000
                        180f at 0
                        360f at durationMillis
                    }
                )
            }
        }
    ) { 0f }

    if (flipState.value is FlipState.Back && rotationX >= 90f) {
        displayableDigit.value = (flipState.value as FlipState.Back).newValue
    } else if (flipState.value is FlipState.Front && rotationX >= 270f) {
        displayableDigit.value = (flipState.value as FlipState.Front).newValue
    }

    Card(
        modifier
            .padding(2.dp)
            .height(44.dp)
            .width(35.dp)
            .graphicsLayer(rotationX = rotationX),
        shape = RoundedCornerShape(3.dp),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.primaryVariant
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                modifier = modifier.graphicsLayer(rotationX = 360 - rotationX),
                text = "${displayableDigit.value}",
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    fontFamily = FontFamily.Monospace
                ),
            )
        }
    }
}

@Composable
@Preview
fun JetTimer(viewModel: AppViewModel = AppViewModel()) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Hour
        Column {
            Text(
                text = "Hours",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace
                )
            )
            Row {
                TimerDigitAnimated(flipState = viewModel.h0Obs.observeAsState())
                TimerDigitAnimated(flipState = viewModel.h1Obs.observeAsState())
            }
        }

        Column {
            Text(text = "", style = TextStyle(fontSize = 10.sp))
            Text(
                text = ":",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    fontFamily = FontFamily.Monospace
                )
            )
        }

        // Minute
        Column {
            Text(
                text = "Minutes",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace
                )
            )
            Row {
                TimerDigitAnimated(flipState = viewModel.m0Obs.observeAsState())
                TimerDigitAnimated(flipState = viewModel.m1Obs.observeAsState())
            }
        }

        Column {
            Text(text = "", style = TextStyle(fontSize = 10.sp))
            Text(
                text = ":",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    fontFamily = FontFamily.Monospace
                )
            )
        }

        Column {
            Text(
                text = "Seconds",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace
                )
            )
            Row {
                TimerDigitAnimated(flipState = viewModel.s0Obs.observeAsState())
                TimerDigitAnimated(flipState = viewModel.s1Obs.observeAsState())
            }
        }
    }
}
