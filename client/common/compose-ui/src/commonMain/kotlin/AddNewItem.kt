package com.exawizards.multiplatform_template.compose_ui

import androidx.compose.foundation.Image
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.testTag

@Composable
fun AddNewItem(addItem: (String) -> Unit) {
    var itemToAdd: String by remember {
        mutableStateOf("")
    }
    TextField(
        modifier = Modifier.testTag("addItemTextField"),
        value = itemToAdd,
        label = { AddItemLabel() },
        onValueChange = { itemToAdd = it },
        trailingIcon = {
            AddItemButton(itemToAdd) {
                addItem(itemToAdd)
                itemToAdd = ""
            }
        }
    )
}

@Composable
private fun AddItemLabel() {
    Text("Add new item")
}

@Composable
private fun AddItemButton(
    itemText: String,
    onClickedButton: () -> Unit
) {
    val enabled = itemText.isNotBlank()
    val icon = Icons.Filled.Add
    IconButton(
        modifier = Modifier.testTag("addItemButton"),
        onClick = onClickedButton,
        enabled = enabled
    ) {
        Image(
            icon,
            icon.name,
            Modifier.alpha(if (enabled) 1f else 0.3f)
        )
    }
}