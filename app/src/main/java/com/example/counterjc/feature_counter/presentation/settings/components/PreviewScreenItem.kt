package com.example.counterjc.feature_counter.presentation.settings.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.example.counterjc.R
import com.example.counterjc.feature_counter.presentation.counter.CounterEvent
import com.example.counterjc.ui.theme.PurpleGrey
import com.example.counterjc.ui.theme.achievedGoalColor
import com.example.counterjc.ui.theme.backgroundPanelColor

@Composable
fun PreviewScreenItem(

) {
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
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }


    Box (
        modifier = Modifier
            .fillMaxSize()
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
                //TODO: Insert custom image picked by user
                Image(
                    alpha = 0.75f,
                    painter = painterResource(id = R.drawable.cat_2),
                    contentDescription = "Background image",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = "0",
                color = backgroundPanelColor,
                textAlign = TextAlign.Center,
                fontSize = 70.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .layoutId("counter_text")
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
                    tint = PurpleGrey, //TODO: Add color picker for these 3 icons
                    modifier = Modifier
                        .clip(CircleShape)
                        .weight(1f)
                        .size(20.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = "Subtract row",
                    tint = PurpleGrey,
                    modifier = Modifier
                        .clip(CircleShape)
                        .weight(1f)
                        .size(20.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_rabbit),
                    contentDescription = "Reset counter",
                    tint = PurpleGrey,
                    modifier = Modifier
                        .clip(CircleShape)
                        .weight(1f)
                        .size(20.dp)
                )
            }

        }
    }
}