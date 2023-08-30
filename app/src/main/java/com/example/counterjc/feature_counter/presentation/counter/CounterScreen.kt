package com.example.counterjc.feature_counter.presentation.counter


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import coil.size.Size
import com.example.counterjc.R
import com.example.counterjc.feature_counter.presentation.counter.components.GoalDialog
import com.example.counterjc.feature_counter.presentation.util.CustomTopAppBar
import com.example.counterjc.ui.theme.achievedGoalColor


@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
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

   val constraints = ConstraintSet{
       val mainBox = createRefFor("main_box")
       val navigationBar = createRefFor("navigation_bar")
       val counterText = createRefFor("counter_text")

       constrain(mainBox) {
           top.linkTo(parent.top)
           bottom.linkTo(parent.bottom)
           start.linkTo(parent.start)
           end.linkTo(parent.end)
       }

       constrain(navigationBar) {
           bottom.linkTo(parent.bottom, margin = 20.dp)
           end.linkTo(parent.end)
       }

       constrain(counterText) {
           top.linkTo(parent.top)
           start.linkTo(parent.start)
           end.linkTo(parent.end)
       }
   }

    Scaffold(
        topBar = {
                 CustomTopAppBar(
                     icon = Icons.Filled.ArrowBack,
                     iconDescription = "Back to the home screen",
                     title = state.name,
                     onIconClick = {
                         viewModel.onEvent(CounterEvent.SaveProduct)
                         navController.popBackStack()
                     }
                 )
        },
        snackbarHost = {SnackbarHost(snackBarHostState) }
    ) {

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

        ConstraintLayout(
            constraintSet = constraints,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .layoutId("main_box")
                    .clickable(enabled = true) {
                        if (state.counter != state.goal || state.goal <= 0) {
                            viewModel.onEvent(CounterEvent.Increase)
                        }
                        if (
                            (state.counter == state.goal - 1 && state.goal != 0) ||
                            (state.counter == state.goal && state.goal != 0)
                        ) {
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
                        CircularProgressIndicator()
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }
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
                    .layoutId("counter_text")
            )
            Column (
                modifier = Modifier
                    .height(270.dp)
                    .width(70.dp)
                    .layoutId("navigation_bar")
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_goal),
                    contentDescription = "Set the goal",
                    tint = Color(state.iconsColor),
                    modifier = Modifier
                        .clip(CircleShape)
                        .weight(1f)
                        .size(50.dp)
                        .clickable { viewModel.onEvent(CounterEvent.ShowSetGoalDialog) }
                )

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
        }
    }
}