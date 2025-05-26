package com.example.counterjc.feature_counter.presentation.counter


import android.annotation.SuppressLint
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import coil.size.Size
import com.example.counterjc.R
import com.example.counterjc.feature_counter.presentation.counter.components.GoalDialog
import com.example.counterjc.ui.theme.DarkPurple80
import com.example.counterjc.ui.theme.achievedGoalColor


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CounterScreen(
    viewModel: CounterViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val state = viewModel.counterState.value
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()
    val vibrator = context.getSystemService(Vibrator::class.java)
    var isLoading by rememberSaveable {
        mutableStateOf(false)
    }

    LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
        viewModel.onEvent(CounterEvent.SaveProduct)
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .systemBarsPadding(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = state.name,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { padding ->
        if (viewModel.counterState.value.isShowAchievedGoalDialog) {
            // Dialog window which appears when user achieved the goal of rows
            GoalDialog(
                title = stringResource(id = R.string.achieved_goal_dialog_title),
                message = stringResource(id = R.string.achieved_goal_dialog_message),
                counterState = state,
                onAction = viewModel::onEvent,
                snackBarHostState = snackBarHostState,
                coroutineScope = coroutineScope
            )
        }
        if (viewModel.counterState.value.isShowSetGoalDialog) {
            // Dialog window for setting the goal of rows
            GoalDialog(
                title = stringResource(id = R.string.goal_dialog_title),
                message = stringResource(id = R.string.goal_dialog_message),
                counterState = state,
                onAction = viewModel::onEvent,
                snackBarHostState = snackBarHostState,
                coroutineScope = coroutineScope
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    if (state.counter != state.goal || state.goal <= 0) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator?.vibrate(
                                VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
                            )
                        } else {
                            vibrator?.vibrate(50)
                        }
                        viewModel.onEvent(CounterEvent.Increase)
                    }
                    if (
                        (state.counter == state.goal - 1 && state.goal != 0) ||
                        (state.counter == state.goal && state.goal != 0)
                    ) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator?.vibrate(
                                VibrationEffect.createWaveform(longArrayOf(0, 200, 100, 200, 100, 200), -1)
                            )
                        } else {
                            vibrator?.vibrate(longArrayOf(0, 200, 100, 200, 100, 200), -1)
                        }
                        viewModel.onEvent(CounterEvent.ShowAchievedGoalDialog)
                    }
                },
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                alpha = 0.75f,
                contentScale = ContentScale.Crop,
                model = ImageRequest.Builder(context)
                    .data(state.backgroundImage)
                    .size(Size.ORIGINAL)
                    .build(),
                contentDescription = "Background image"
            ) {
                val imageState = painter.state
                if (
                    imageState is AsyncImagePainter.State.Loading ||
                    imageState is AsyncImagePainter.State.Error
                ) {
                    isLoading = true
                } else {
                    isLoading = false
                    SubcomposeAsyncImageContent()
                }
            }

            IconButton(
                onClick = {
                    viewModel.onEvent(CounterEvent.SaveProduct)
                    navController.popBackStack()
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .systemBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = null
                )
            }

            Text(
                text = state.counter.toString(),
                color = if (state.counter != state.goal || state.goal == 0) {
                    Color(state.counterColor)
                } else {
                    achievedGoalColor
                },
                textAlign = TextAlign.Center,
                fontSize = 130.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(padding),
            )


            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 24.dp)
                    .height(270.dp)
                    .width(70.dp)
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_goal),
                        contentDescription = "Set the goal",
                        tint = Color(state.iconsColor),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(50.dp)
                            .clickable { viewModel.onEvent(CounterEvent.ShowSetGoalDialog) }
                    )

                    if (state.goal > 0) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .height(24.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${state.goal}",
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                        }
                    }
                }

                Icon(
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = "Subtract row",
                    tint = Color(state.iconsColor),
                    modifier = Modifier
                        .clip(CircleShape)
                        .weight(1f)
                        .size(50.dp)
                        .clickable { viewModel.onEvent(CounterEvent.Decrease) }
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_rabbit),
                    contentDescription = "Reset counter",
                    tint = Color(state.iconsColor),
                    modifier = Modifier
                        .clip(CircleShape)
                        .weight(1f)
                        .size(50.dp)
                        .clickable { viewModel.onEvent(CounterEvent.ClearCounter) }
                )
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.75f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        LoadingIndicator(
                            modifier = Modifier.size(100.dp),
                            color = DarkPurple80
                        )

                        Text(
                            text = "Loading image... Wait please",
                            fontSize = 24.sp,
                            color = Color.LightGray
                        )
                    }
                }
            }
        }
    }
}