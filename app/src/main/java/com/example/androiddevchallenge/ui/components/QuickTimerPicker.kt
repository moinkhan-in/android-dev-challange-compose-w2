package com.example.androiddevchallenge.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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