package com.example.counterjc.feature_counter.presentation.counter.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import coil.compose.AsyncImage
import com.example.counterjc.R

@Composable
fun BackgroundImage(

) {
    var imageUri: Any? by remember { mutableStateOf(R.drawable.cat_2) }
    AsyncImage(
        model = imageUri,
        contentDescription = "Background image"
    )
}