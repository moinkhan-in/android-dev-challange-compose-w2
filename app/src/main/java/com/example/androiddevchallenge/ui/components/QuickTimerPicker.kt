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

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.toHMS

@Composable
fun QuickTimerPicker(
    modifier: Modifier,
    onClick: (Long) -> Unit
) {
    val maxTime: Long = 1000 * 60 * 60 * 99
    val offset: Long = 15 * 1000 // 15 seconds+

    LazyRow(modifier = modifier) {
        val totalButtons = maxTime / offset
        items(count = totalButtons.toInt()) { index ->
            val timeInMillis = (index + 1) * offset
            Button(
                modifier = Modifier.padding(12.dp),
                onClick = { onClick.invoke(timeInMillis) },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.yellow))
            ) {
                Text(text = timeInMillis.toHMS())
            }
        }
    }
}
