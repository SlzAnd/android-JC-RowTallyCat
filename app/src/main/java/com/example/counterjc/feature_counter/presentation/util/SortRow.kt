package com.example.counterjc.feature_counter.presentation.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Title
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.material3.ToggleButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.counterjc.R
import com.example.counterjc.feature_counter.domain.util.SortType

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SortRow(
    modifier: Modifier = Modifier,
    selectedSortType: SortType,
    onChangeSortType: (SortType) -> Unit
) {
    val options = listOf(
        stringResource(id = R.string.sorting_area_item_by_name),
        stringResource(id = R.string.sorting_area_item_by_modification_time)
    )
    val unCheckedIcons =
        listOf(Icons.Outlined.Title, Icons.Outlined.AccessTime)
    val checkedIcons = listOf(Icons.Filled.Title, Icons.Filled.AccessTimeFilled)
    var selectedIndex by remember { mutableIntStateOf(selectedSortType.ordinal) }

    FlowRow(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(ButtonGroupDefaults.ConnectedSpaceBetween),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        val modifiers = listOf(Modifier.weight(1f), Modifier.weight(1.5f), Modifier.weight(1f))

        options.forEachIndexed { index, label ->
            ToggleButton(
                checked = selectedIndex == index,
                onCheckedChange = {
                    selectedIndex = index
                    onChangeSortType(SortType.entries[index])
                },
                modifier = modifiers[index].semantics { role = Role.RadioButton },
                shapes =
                when (index) {
                    0 -> ButtonGroupDefaults.connectedLeadingButtonShapes()
                    options.lastIndex -> ButtonGroupDefaults.connectedTrailingButtonShapes()
                    else -> ButtonGroupDefaults.connectedMiddleButtonShapes()
                }
            ) {
                Icon(
                    if (selectedIndex == index) checkedIcons[index] else unCheckedIcons[index],
                    contentDescription = "Localized description"
                )
                Spacer(Modifier.size(ToggleButtonDefaults.IconSpacing))
                Text(label)
            }
        }
    }
}