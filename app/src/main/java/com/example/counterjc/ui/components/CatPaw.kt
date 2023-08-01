package com.example.counterjc.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.counterjc.R

@Composable
fun CatPaw(
    modifier: Modifier = Modifier,
    color: Color = Color.Black
) {
    Box(
        modifier = modifier
            .offset(x = 0.dp, y = 0.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.paw),
            contentDescription = "Cat paw",
            tint = color
        )
    }
}
