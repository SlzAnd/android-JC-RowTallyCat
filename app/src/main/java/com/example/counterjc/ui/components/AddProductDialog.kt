package com.example.counterjc.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.counterjc.logic.ProductEvent
import com.example.counterjc.logic.ProductState
import com.example.counterjc.ui.theme.Purple40
import com.example.counterjc.ui.theme.Purple80
import com.example.counterjc.ui.theme.PurpleGrey40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductDialog(
    state: ProductState,
    onEvent: (ProductEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { onEvent(ProductEvent.HideDialog) },
    ) {
        Box(
            modifier = modifier
                .background(
                    Color.White
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Додати новий виріб",
                    fontSize = 20.sp
                )
                TextField(
                    value = state.name,
                    onValueChange = {
                        onEvent(ProductEvent.SetProductName(it))
                    },
                    placeholder = {
                        Text(text = "Назва виробу")
                    }
                )
                Box(
                    modifier = modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(
                        onClick = { onEvent(ProductEvent.SaveProduct)},
                        colors = ButtonDefaults.buttonColors(Purple40)
                    ) {
                        Text(text = "Зберегти")
                    }
                }
            }
        }
    }
}