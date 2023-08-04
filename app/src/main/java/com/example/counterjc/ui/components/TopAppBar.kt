package com.example.counterjc.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.counterjc.ui.theme.Purple80

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    icon: ImageVector,
    iconDescription: String,
    title: String,
    onIconClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                onIconClick()
            }) {
                Icon(imageVector = icon,
                    contentDescription = iconDescription)
            }
        },
        title = {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 26.sp,
                    fontFamily = FontFamily.Cursive
                )
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Purple80
        )
    )
}