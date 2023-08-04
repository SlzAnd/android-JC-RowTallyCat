package com.example.counterjc.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.counterjc.R
import com.example.counterjc.logic.CounterViewModel
import com.example.counterjc.logic.MenuItem
import com.example.counterjc.logic.Screen
import com.example.counterjc.ui.components.CustomTopAppBar
import com.example.counterjc.ui.components.DrawerBody
import com.example.counterjc.ui.components.DrawerHeader
import com.example.counterjc.ui.theme.Pink40
import com.example.counterjc.ui.theme.Pink80
import com.example.counterjc.ui.theme.Purple80
import com.example.counterjc.ui.theme.achievedGoalColor
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            CustomTopAppBar(
                icon = Icons.Filled.Menu,
                iconDescription = "Navigation menu",
                title = "Row Tally Cat"
            ) {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        },
        drawerContent = {
                        DrawerHeader()
                        DrawerBody(
                            items = listOf(
                                MenuItem(
                                    id = "home",
                                    title = "Home",
                                    contentDescription = "Go to home screen",
                                    icon = Icons.Filled.Home
                                ),
                                MenuItem(
                                    id = "settings",
                                    title = "Settings",
                                    contentDescription = "Go to settings screen",
                                    icon = Icons.Filled.Settings
                                ),
                                MenuItem(
                                    id = "help",
                                    title = "Help",
                                    contentDescription = "Get help",
                                    icon = Icons.Filled.Info
                                ),
                            ),
                            onItemClick = {
                                println("Clicked on ${it.title}")
                            }
                        )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = Purple80
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = ""
                )
            }
        }
    ) {

        LazyColumn(
            Modifier.consumeWindowInsets(it),
            contentPadding = it
        ) {
            items(50) {
                ProductItem(image = painterResource(
                    id = R.drawable.knitting1),
                    productName = "Ведмедик білого кольору з круглими очима та одягом",
                    counterValue = 10,
                    goalValue = 15,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(
                                route = Screen.Counter.route
                            )
                        },
                )
            }
        }
    }
}


@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    image: Painter,
    productName: String,
    counterValue: Int,
    goalValue: Int,
    ) {
    Box (
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray,
            )
            .shadow(1.dp)
            .padding(3.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.foundation.Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = productName,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                ),
                maxLines = 2,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .requiredWidth(250.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = counterValue.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.LightGray,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = goalValue.toString(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    color = achievedGoalColor,
                )
            }
            Spacer(modifier = Modifier.width(10.dp))


            IconButton(
                modifier = Modifier,
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = ""
                )
            }
        }
    }
}

