package com.example.counterjc.feature_counter.presentation.counter.components


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.counterjc.R
import com.example.counterjc.feature_counter.presentation.counter.CounterEvent
import com.example.counterjc.feature_counter.presentation.counter.CounterState
import com.example.counterjc.feature_counter.presentation.products.ProductEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun GoalDialog(
    title: String,
    message: String,
    counterState: CounterState,
    onAction: (CounterEvent) -> Unit,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    val rowsString = when (counterState.goal) {
        1 -> stringResource(id = R.string.goal_set_snackbar_one_row)
        in 2..4 -> stringResource(id = R.string.goal_set_snackbar_2_4_row)
        else -> stringResource(id = R.string.goal_set_snackbar_more_rows)
    }
    val cleanMessage = stringResource(id = R.string.goal_clean_snackbar_message)
    val setMessage =
        "${stringResource(id = R.string.goal_set_snackbar_message)} ${counterState.goal} $rowsString"
    var textGoal by rememberSaveable {
        if (counterState.goal <= 0) {
            mutableStateOf("")
        } else {
            mutableStateOf(counterState.goal.toString())
        }
    }

    AlertDialog(
        onDismissRequest = {
            onAction(CounterEvent.HideSetGoalDialog)
            onAction(CounterEvent.HideAchievedGoalDialog)
        },
        modifier = Modifier
            .shadow(elevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 8.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = textGoal,
                    onValueChange = {
                        textGoal = it
                        if (it.isNotBlank()) {
                            it.trim().toIntOrNull()?.let { goal ->
                                onAction(CounterEvent.SetGoal(goal))
                            }
                        } else {
                            onAction(CounterEvent.SetGoal(0))
                        }
                    },
                    placeholder = {
                        Text(text = stringResource(id = R.string.add_product_dialog_productGoal_hint))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            if (counterState.goal > 0) {
                                onAction(CounterEvent.ClearGoal)

                                coroutineScope.launch {
                                    snackBarHostState
                                        .showSnackbar(
                                            cleanMessage,
                                            duration = SnackbarDuration.Short
                                        )
                                }
                            }
                            onAction(CounterEvent.HideSetGoalDialog)
                            onAction(CounterEvent.HideAchievedGoalDialog)
                        },
                    ) {
                        Text(
                            text = stringResource(id = R.string.goal_dialog_reset_button_text),
                        )
                    }

                    TextButton(
                        onClick = {
                            onAction(CounterEvent.HideSetGoalDialog)
                            onAction(CounterEvent.HideAchievedGoalDialog)
                            coroutineScope.launch {
                                snackBarHostState
                                    .showSnackbar(
                                        setMessage,
                                        duration = SnackbarDuration.Short
                                    )
                            }
                        },
                    ) {
                        Text(
                            text = stringResource(id = R.string.positive_button_text),
                        )
                    }
                }
            }
        }
    }
}