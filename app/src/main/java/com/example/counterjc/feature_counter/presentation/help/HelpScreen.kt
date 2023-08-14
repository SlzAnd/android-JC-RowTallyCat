package com.example.counterjc.feature_counter.presentation.help

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.counterjc.R
import com.example.counterjc.feature_counter.presentation.help.components.IconDescription
import com.example.counterjc.feature_counter.presentation.util.CustomTopAppBar
import com.example.counterjc.feature_counter.presentation.util.NavigationDrawer
import kotlinx.coroutines.launch

@Composable
fun HelpScreen(
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavigationDrawer(
                navController = navController
            )
        }
    ) {
        Scaffold(
            topBar = {
                CustomTopAppBar(
                    icon = Icons.Filled.Menu,
                    iconDescription = "Navigation menu",
                    title = stringResource(id = R.string.app_name)
                ) {
                    scope.launch {
                        drawerState.open()
                    }
                }
            }
        ) {
            val scrollState = rememberScrollState()
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = stringResource(id = R.string.help_screen_title),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.help_screen_text),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(16.dp))

                IconDescription(
                    text = stringResource(id = R.string.help_screen_add_product_button_description),
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add new product"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.help_screen_text_2),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(16.dp))

                IconDescription(
                    text = stringResource(id = R.string.help_screen_setGoal_button_description),
                    painter = painterResource(id = R.drawable.ic_goal),
                    contentDescription = "Set the goal"
                )

                Spacer(modifier = Modifier.height(16.dp))

                IconDescription(
                    text = stringResource(id = R.string.help_screen_subtractRow_button_description),
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = "Subtract row"
                )

                Spacer(modifier = Modifier.height(16.dp))

                IconDescription(
                    text = stringResource(id = R.string.help_screen_resetCounter_button_description),
                    painter = painterResource(id = R.drawable.ic_rabbit),
                    contentDescription = "Reset counter"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.help_screen_final_text),
                    textAlign = TextAlign.Start
                )
            }

        }
    }

}