package com.example.counterjc.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Snackbar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.counterjc.ui.theme.PurpleGrey
import com.example.counterjc.ui.theme.PurpleGrey40
import com.example.counterjc.ui.theme.PurpleGrey80

@Composable
fun CustomSnackBar(
    title: String,
    message: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    bottomNavigation: @Composable (() -> Unit)? = null
) {
    Snackbar(
        modifier = modifier,
        action = bottomNavigation,
        actionOnNewLine = true,
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ){
        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                backgroundColor = PurpleGrey40,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(2.dp, color = Color.LightGray),
                modifier = Modifier
                    .padding(8.dp)
            ){
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = "",
                            tint = PurpleGrey80
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = title,
                            style = TextStyle(
                                color = PurpleGrey80,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold

                            )
                        )
                    }
                    Card (
                        backgroundColor = PurpleGrey80,
                        elevation = 6.dp,
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Text(
                            text = message,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = PurpleGrey,
                            ),
                            modifier = Modifier
                                .padding(10.dp)
                        )
                    }
                }
            }
        }
    }
}