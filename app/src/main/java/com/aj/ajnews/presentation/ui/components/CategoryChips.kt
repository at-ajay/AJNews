package com.aj.ajnews.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aj.ajnews.presentation.ui.screens.category_selection_screen.CategoryItem
import com.aj.ajnews.util.Constants

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryChips(
    categories: MutableList<CategoryItem>,
    modifier: Modifier,
    onClick: (Int) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        repeat(categories.size) {
            val item = categories[it]
            ChipItem(label = item.label, isSelected = item.isSelected.value, onClick = { onClick(it) })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Text(
                text = label,
                fontFamily = Constants.Rubik,
                fontWeight = FontWeight.Medium,
            )
        },
        trailingIcon = {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    )
}