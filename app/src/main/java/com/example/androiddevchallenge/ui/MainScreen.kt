package com.example.androiddevchallenge.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.components.JetTimer
import com.example.androiddevchallenge.ui.components.QuickTimerPicker
import com.example.androiddevchallenge.ui.theme.shapes
import com.example.androiddevchallenge.viewModels.AppViewModel

@ExperimentalAnimationApi
@Composable
@Preview
fun MainScreen(viewModel: AppViewModel = AppViewModel()) {

    val isTimerRunningState = viewModel.isTimerRunning.observeAsState(false)
    val timerValueState = viewModel.timeInMillis.observeAsState(0)

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (topView, centerView, bottomView) = createRefs()

        // App Title
        TopBar(
            modifier = Modifier.constrainAs(topView) {
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            },
            viewModel = viewModel
        )

        // Center view, main countdown
        Row(
            modifier = Modifier.constrainAs(centerView) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            JetTimer(viewModel = viewModel)
        }

        // Buttons
        Column(
            Modifier
                .fillMaxWidth()
                .constrainAs(bottomView) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(Modifier.padding(bottom = 32.dp)) {
                Button(
                    onClick = { viewModel.startTimer() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primaryVariant,
                        disabledBackgroundColor = MaterialTheme.colors.primaryVariant.copy(alpha = 0.5f)
                    ),
                    enabled = timerValueState.value != 0L && isTimerRunningState.value.not()
                ) {
                    Text(text = "  Start  ", style = TextStyle(fontSize = 18.sp, fontFamily = FontFamily.Monospace))
                }
                Spacer(modifier = Modifier.size(32.dp))
                Button(
                    onClick = { viewModel.resetTimer() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primaryVariant,
                        disabledBackgroundColor = MaterialTheme.colors.primaryVariant.copy(alpha = 0.5f)
                    ),
                    enabled = timerValueState.value != 0L
                ) {
                    Text(text = "  Reset  ", style = TextStyle(fontSize = 18.sp, fontFamily = FontFamily.Monospace))
                }
            }
            AnimatedVisibility(visible = isTimerRunningState.value.not()) {
                Column(
                    Modifier
                        .background(
                            color = MaterialTheme.colors.primaryVariant
                        )
                ) {
                    Text(
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, bottom = 4.dp),
                        text = "Quick Timers",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    )
                    QuickTimerPicker(
                        modifier = Modifier.padding(bottom = 20.dp),
                        onClick = { viewModel.updateNewTime(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun TopBar(
    modifier: Modifier,
    viewModel: AppViewModel,
) {

    val isTimerRunningState = viewModel.isTimerRunning.observeAsState(false)

    Box(
        modifier = modifier.width(750.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Box(
            modifier = Modifier
                .graphicsLayer(rotationZ = 20f)
                .offset(y = (-70).dp)
                .scale(1.2f)
                .height(200.dp)
                .background(MaterialTheme.colors.primaryVariant)
                .fillMaxWidth()
        )
        ConstraintLayout {

            val (title, icon) = createRefs()

            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                    },
                text = "Jet CountDown",
                style = TextStyle(
                    fontSize = 28.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                ),
            )
            Icon(
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 16.dp)
                    .constrainAs(icon) {
                        top.linkTo(title.bottom)
                        end.linkTo(parent.end)
                    },
                painter = painterResource(id = R.drawable.ic_baseline_timer_24),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}