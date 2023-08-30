package com.example.counterjc.feature_counter.presentation.settings.components

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.counterjc.R
import com.example.counterjc.feature_counter.presentation.settings.SettingsEvent


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PreviewScreenItem(
    imageUri: String,
    counterColor: Color,
    iconsColor: Color,
    context: Context,
    counterOffsetX: Float,
    counterOffsetY: Float,
    onEvent: (SettingsEvent) -> Unit,
    ) {

    val d = LocalDensity.current

    val constraints = ConstraintSet{
        val mainBox = createRefFor("main_box")
        val navigationBar = createRefFor("navigation_bar")
        val counterText = createRefFor("counter_text")

        constrain(mainBox) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom, margin = 20.dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(navigationBar) {
            bottom.linkTo(parent.bottom, margin = 20.dp)
            end.linkTo(parent.end, margin = (-20).dp)
        }

        constrain(counterText) {
            top.linkTo(parent.top, margin = (-20).dp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }


    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .drawBehind {
                drawRoundRect(
                    color = Color.White,
                    size = Size(
                        width = size.width,
                        height = (size.height / 1.25).toFloat()
                    )
                )
            }
    ) {
        ConstraintLayout(
            constraintSet = constraints,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .layoutId("main_box")
            ) {
                SubcomposeAsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        alpha = 0.75f,
                        contentScale = ContentScale.Crop,
                        model = ImageRequest.Builder(context)
                            .data(imageUri)
                            .size(coil.size.Size.ORIGINAL)
                            .build(),
                        contentDescription = "Background image"
                    ) {
                        val state = painter.state
                        if (
                            state is AsyncImagePainter.State.Loading ||
                            state is AsyncImagePainter.State.Error
                        ) {
                            CircularProgressIndicator()
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }


            }
            Text(
                text = "0",
                color = counterColor,
                textAlign = TextAlign.Center,
                fontSize = 70.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .layoutId("counter_text")
                    .offset(
                        x = (counterOffsetX/d.density).dp,
                        y = (counterOffsetY/d.density).dp
                    )
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            onEvent(SettingsEvent.SetCounterOffset(dragAmount.x, dragAmount.y))
                        }
                    }
            )

            Column (
                modifier = Modifier
                    .height(130.dp)
                    .width(70.dp)
                    .layoutId("navigation_bar")
                    .background(Color.Transparent),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_goal),
                    contentDescription = "Set the goal",
                    tint = iconsColor,
                    modifier = Modifier
                        .clip(CircleShape)
                        .weight(1f)
                        .size(20.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = "Subtract row",
                    tint = iconsColor,
                    modifier = Modifier
                        .clip(CircleShape)
                        .weight(1f)
                        .size(20.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_rabbit),
                    contentDescription = "Reset counter",
                    tint = iconsColor,
                    modifier = Modifier
                        .clip(CircleShape)
                        .weight(1f)
                        .size(20.dp)
                )
            }

        }
    }
}




