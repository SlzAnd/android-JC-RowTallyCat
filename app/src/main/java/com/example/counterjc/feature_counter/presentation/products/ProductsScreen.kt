package com.example.counterjc.feature_counter.presentation.products

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.counterjc.R
import com.example.counterjc.feature_counter.domain.util.SortType
import com.example.counterjc.feature_counter.presentation.navigation.Screen
import com.example.counterjc.feature_counter.presentation.products.components.AddProductDialog
import com.example.counterjc.feature_counter.presentation.products.components.ProductItem
import com.example.counterjc.feature_counter.presentation.products.components.SortTypeItem
import com.example.counterjc.feature_counter.presentation.util.CustomTopAppBar
import com.example.counterjc.feature_counter.presentation.util.NavigationDrawer
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
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
            snackbarHost = { SnackbarHost(snackBarHostState) },
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
            },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        viewModel.onEvent(ProductEvent.ShowDialog)
                    },
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Add new product",
                    )
                }
            },
        ) { padding ->

            if (state.isAddingProduct) {
                AddProductDialog(
                    state = state,
                    onEvent = viewModel::onEvent,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = stringResource(id = R.string.sorting_area_title),
                        style = MaterialTheme.typography.titleSmall
                    )

                    FlowRow(
                        modifier = Modifier.align(Alignment.Start),
                        verticalArrangement = Arrangement.Center
                    ) {

                        SortType.entries.forEach { sortType ->
                            SortTypeItem(
                                modifier = Modifier,
                                onClick = viewModel::onEvent,
                                sortType = sortType,
                                state = state
                            )
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    items(state.products) {product ->
                        val onDeleteMessage = "${stringResource(id = R.string.deleted_product_snackbar_message_first_part)} \"${product.name}\" ${stringResource(id = R.string.deleted_product_snackbar_message_second_part)}"
                        val onDeleteUndoActionString = stringResource(id = R.string.deleted_product_snackbar_action)
                        ProductItem(
                            image = painterResource(
                                id = R.drawable.knitting1
                            ),
                            product = product,
                            onDeleteClick = {
                                viewModel.onEvent(ProductEvent.DeleteProduct(product))
                                scope.launch {
                                    val result = snackBarHostState.showSnackbar(
                                        message = onDeleteMessage,
                                        actionLabel = onDeleteUndoActionString,
                                        duration = SnackbarDuration.Short
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(ProductEvent.RestoreProduct)
                                    }
                                }
                            },
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(
                                        route = Screen.Counter.passId(product.id)
                                    )
                                }
                        )
                    }
                }
            }


        }

    }
}




