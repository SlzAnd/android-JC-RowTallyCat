package com.example.counterjc.ui.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavHostController
import com.example.counterjc.logic.CounterAction
import com.example.counterjc.logic.CounterState
import com.example.counterjc.R
import com.example.counterjc.ui.components.CustomSnackBar
import com.example.counterjc.ui.components.CustomTopAppBar
import com.example.counterjc.ui.components.GoalDialog
import com.example.counterjc.ui.theme.Purple80
import com.example.counterjc.ui.theme.PurpleGrey
import com.example.counterjc.ui.theme.achievedGoalColor
import com.example.counterjc.ui.theme.backgroundPanelColor
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CounterScreen(
    state: CounterState,
    onAction: (CounterAction) -> Unit,
    navController: NavHostController
) {
    val goalDialogState = rememberMaterialDialogState()
    val achievedGoalDialogState = rememberMaterialDialogState()
    val snackBarHostState = remember {
        SnackbarHostState()
    }

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
           top.linkTo(parent.top, margin = (-30).dp)
           start.linkTo(parent.start)
           end.linkTo(parent.end)
       }
   }

    Scaffold(
        topBar = {
                 CustomTopAppBar(
                     icon = Icons.Filled.ArrowBack,
                     iconDescription = "Back to the home screen",
                     title = "product_name",
                     onIconClick = {navController.popBackStack()})
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier,
                snackbar = {
                    val rowsString = when (state.goal) {
                        1 -> "рядок"
                        in 2..4 -> "рядки"
                        else -> "рядків"
                    }

                    var title = "Встановлено ціль!"
                    var message = "Ваша нова ціль ${state.goal} $rowsString!"
                    var icon = Icons.Default.CheckCircle

                    if (state.goal == 0) {
                        title = "Ціль відсутня!"
                        message = "Ціль було успішно анульовано!"
                        icon = Icons.Default.Clear
                    }

                    CustomSnackBar(
                        title = title,
                        message = message,
                        icon = icon
                    )
                }
            )
        }
    ) {
        ConstraintLayout(
            constraints,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .layoutId("main_box")
                    .clickable(enabled = true) {
                        if (state.counter != state.goal || state.goal <= 0) {
                            onAction(CounterAction.Increase)
                        }
                        if (
                            (state.counter == state.goal - 1 && state.goal != 0) ||
                            (state.counter == state.goal && state.goal != 0)
                        ) {
                            achievedGoalDialogState.show()
                        }
                    },
            ) {
                Image(
                    alpha = 0.75f,
                    painter = painterResource(id = R.drawable.cat_2),
                    contentDescription = "Background image",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = state.counter.toString(),
                color = if (state.counter != state.goal || state.goal == 0) {
                    backgroundPanelColor
                } else {
                    achievedGoalColor
                },
                textAlign = TextAlign.Center,
                fontSize = 150.sp,
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
                    tint = PurpleGrey,
                    modifier = Modifier
                        .clip(CircleShape)
                        .weight(1f)
                        .size(50.dp)
                        .clickable { goalDialogState.show() }
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = "Minus one row",
                    tint = PurpleGrey,
                    modifier = Modifier
                        .clip(CircleShape)
                        .weight(1f)
                        .size(50.dp)
                        .clickable { onAction(CounterAction.Decrease) }
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_rabbit),
                    contentDescription = "Clear the counter",
                    tint = PurpleGrey,
                    modifier = Modifier
                        .clip(CircleShape)
                        .weight(1f)
                        .size(50.dp)
                        .clickable { onAction(CounterAction.ClearCounter) }
                )
            }

            // Dialog window for setting the goal of rows
            GoalDialog(
                title = stringResource(id = R.string.goal_dialog_title),
                message = stringResource(id = R.string.goal_dialog_message),
                dialogState = goalDialogState,
                counterState = state,
                onAction = onAction,
                snackBarHostState = snackBarHostState
            )

            // Dialog window which appears when user achieved the goal of rows
            GoalDialog(
                title = stringResource(id = R.string.achieved_goal_dialog_title),
                message = stringResource(id = R.string.achieved_goal_dialog_message),
                dialogState = achievedGoalDialogState,
                counterState = state,
                onAction = onAction,
                snackBarHostState = snackBarHostState)
        }
    }
}