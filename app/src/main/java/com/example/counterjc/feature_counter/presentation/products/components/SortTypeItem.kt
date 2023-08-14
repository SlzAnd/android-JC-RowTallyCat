package com.example.counterjc.feature_counter.presentation.products.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.counterjc.R
import com.example.counterjc.feature_counter.domain.util.SortType
import com.example.counterjc.feature_counter.presentation.products.ProductEvent
import com.example.counterjc.feature_counter.presentation.products.ProductState

@Composable
fun SortTypeItem(
    onClick: (ProductEvent) -> Unit,
    sortType: SortType,
    state: ProductState,
    modifier: Modifier = Modifier
) {
    val sortTypeName = when (sortType) {
        SortType.NAME -> {
            stringResource(id = R.string.sorting_area_item_by_name)
        }
        SortType.MODIFIED_TIME -> {
            stringResource(id = R.string.sorting_area_item_by_modification_time)
        }
    }

    Row (
        modifier = modifier
            .clickable {
                onClick(ProductEvent.SortProducts(sortType))
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = state.sortType == sortType,
            onClick = {
                onClick(ProductEvent.SortProducts(sortType))
            }
        )
        Text(
            text = sortTypeName,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}