package com.example.counterjc.feature_counter.presentation.util

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.counterjc.feature_counter.presentation.navigation.Screen

data class NavItem(
    val label: String,
    val topBarTitle: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val navigationRoute: Screen
)

@Composable
fun Navbar(
    selectedItem: Int,
    items: List<NavItem>,
    onItemSelected: (index: Int) -> Unit,
) {
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedItem == index) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                selected = selectedItem == index,
                onClick = {
                    onItemSelected(index)
                }
            )
        }
    }
}