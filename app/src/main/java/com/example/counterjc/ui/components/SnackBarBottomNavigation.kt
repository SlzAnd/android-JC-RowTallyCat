package com.example.counterjc.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.counterjc.ui.theme.PurpleGrey
import com.example.counterjc.ui.theme.PurpleGrey80


@Composable
fun BottomNavigation(
    leftButtonText: String,
    rightButtonText: String,
    leftButtonAction: () -> Unit,
    rightButtonAction: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Button(
                modifier = Modifier,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurpleGrey,
                    contentColor = PurpleGrey80
                ),
                onClick = {leftButtonAction()}
            ) {
                Text(
                    modifier = Modifier.background(Color.Transparent),
                    text = leftButtonText
                )
            }
            Spacer(modifier = Modifier
                .width(16.dp)
            )
            OutlinedButton(
                onClick = {rightButtonAction()},
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurpleGrey80,
                    contentColor = PurpleGrey
                ),
            ) {
                Text(text = rightButtonText)
            }
        }
    }
}