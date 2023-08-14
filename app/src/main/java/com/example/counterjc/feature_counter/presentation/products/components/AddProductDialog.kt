package com.example.counterjc.feature_counter.presentation.products.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.counterjc.R
import com.example.counterjc.feature_counter.presentation.products.ProductEvent
import com.example.counterjc.feature_counter.presentation.products.ProductState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductDialog(
    state: ProductState,
    onEvent: (ProductEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var textGoal by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onEvent(ProductEvent.HideDialog)
            onEvent(ProductEvent.HideErrorMessage)
        },
    ) {
        Box(
            modifier = modifier
                .background(
                    MaterialTheme.colorScheme.background
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.add_product_dialog_title),
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = stringResource(id = R.string.add_product_dialog_description),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Start,
                    modifier = modifier.padding(start = 8.dp)
                )
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
                TextField(
                    value = textGoal,
                    onValueChange = {
                        textGoal = it
                        if (it.isNotBlank()) {
                            val newGoal = it.trim().toInt()
                            onEvent(ProductEvent.SetGoal(newGoal))
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

                Box(
                    modifier = modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(
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