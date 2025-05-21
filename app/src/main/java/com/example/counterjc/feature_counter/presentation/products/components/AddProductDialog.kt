package com.example.counterjc.feature_counter.presentation.products.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.counterjc.R
import com.example.counterjc.feature_counter.presentation.products.ProductEvent
import com.example.counterjc.feature_counter.presentation.products.ProductState

@Composable
fun AddProductDialog(
    state: ProductState,
    onEvent: (ProductEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var textGoal by rememberSaveable { mutableStateOf("") }

    Dialog (
        onDismissRequest = {
            onEvent(ProductEvent.HideDialog)
            onEvent(ProductEvent.HideErrorMessage)
        }
    ) {
        Surface(
            modifier = Modifier,
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(
                modifier = modifier
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.add_product_dialog_title),
                        style = MaterialTheme.typography.titleSmall,
                        fontFamily = FontFamily.Default
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.add_product_dialog_description),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Start,
                        modifier = modifier.padding(start = 8.dp)
                    )
                    Spacer(Modifier.height(16.dp))
                    TextField(
                        value = state.name,
                        onValueChange = {
                            if (it.isNotEmpty()) {
                                onEvent(ProductEvent.HideErrorMessage)
                            }
                            onEvent(ProductEvent.SetProductName(it))
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.add_product_dialog_productName_hint))
                        },
                        supportingText = {
                            if (state.isShowErrorMessage) {
                                Text(
                                    text = stringResource(id = R.string.add_product_dialog_empty_name_message),
                                    color = Color.Red
                                )
                            } else {
                                Text(text = stringResource(id = R.string.suporting_text_required_field))
                            }
                        },
                        isError = state.isShowErrorMessage,
                        trailingIcon = {
                            if (state.isShowErrorMessage) {
                                Icon(
                                    Icons.Filled.Warning,
                                    "",
                                    tint = Color.Red
                                )
                            }
                        }
                    )
                    Spacer(Modifier.height(16.dp))

                    TextField(
                        value = textGoal,
                        onValueChange = {
                            textGoal = it
                            if (it.isNotBlank()) {
                                val newGoal = it.trim().toIntOrNull()
                                newGoal?.let { goal ->
                                    onEvent(ProductEvent.SetGoal(goal))
                                }
                            } else {
                                onEvent(ProductEvent.SetGoal(0))
                            }
                        },
                        placeholder = {
                            Text(text = stringResource(id = R.string.add_product_dialog_productGoal_hint))
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    Spacer(Modifier.height(24.dp))

                    Box(
                        modifier = modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        TextButton(
                            onClick = {
                                if (state.name.isEmpty()) {
                                    onEvent(ProductEvent.ShowErrorMessage)
                                } else {
                                    onEvent(ProductEvent.HideErrorMessage)
                                    onEvent(ProductEvent.SaveProduct)
                                }
                            },
                        ) {
                            Text(
                                text = stringResource(id = R.string.add_product_dialog_save_button),
                            )
                        }
                    }
                }
            }
        }
    }
}