package com.example.counterjc.ui.components


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.counterjc.logic.CounterAction
import com.example.counterjc.logic.CounterState
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.TextFieldStyle
import com.vanpra.composematerialdialogs.input
import com.vanpra.composematerialdialogs.message
import com.vanpra.composematerialdialogs.title
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@Composable
fun GoalDialog(
    goalDialogState: MaterialDialogState,
    counterState: CounterState,
    onAction: (CounterAction) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    Box {
        MaterialDialog (
            dialogState = goalDialogState,
            buttons = {
                positiveButton(
                    "OK",
                    textStyle = TextStyle(
                        color = Color.Black
                    ),
                ) {
                    if (counterState.goal > 0) {
                        coroutineScope.launch {
                            snackBarHostState
                                .showSnackbar(
                                    "",
                                    duration = SnackbarDuration.Short
                                )
                        }
                    }
                }
                negativeButton("Cancel")
                negativeButton("Reset") {
                    if (counterState.goal > 0) {
                        onAction(CounterAction.ClearGoal)
                        coroutineScope.launch {
                            snackBarHostState
                                .showSnackbar(
                                    "",
                                    duration = SnackbarDuration.Short
                                )
                        }
                    }
                }
            },
            properties = DialogProperties(
                dismissOnClickOutside = true
            )
        ){
            title(
                text = "Встановити ціль?",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontSize = 22.sp,
                ),
                center = true
            )
            message(
                text = "Тут можна вказати яку кількість рядків тобі необхідно зробити і ми " +
                        "повідомимо коли вона буде досягнута",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray,
                    fontSize = 15.sp,
                )
            )
            input(
                label = "Кількість рядків",
                prefill = if (counterState.goal <= 0) {
                    ""
                } else {
                    counterState.goal.toString()
                },
                textFieldStyle = TextFieldStyle.Outlined,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            ) {
                if (it != "") {
                    val newGoal = it.trim().toInt()
                    counterState.goal = newGoal
                }
            }
        }

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxSize()
            ,
            snackbar = {
                val rowsString = when (counterState.goal) {
                    1 -> "рядок"
                    in 2..4 -> "рядки"
                    else -> "рядків"
                }

                var title = "Встановлено ціль!"
                var message = "Ваша нова ціль ${counterState.goal} $rowsString!"
                var icon = Icons.Default.CheckCircle

                if (counterState.goal == 0) {
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
}