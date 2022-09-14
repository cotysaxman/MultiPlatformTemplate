package com.exawizards.multiplatform_template.compose_ui

import androidx.compose.foundation.layout.Column
import com.exawizards.multiplatform_template.platform_utils.getPlatformName
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.exawizards.multiplatform_template.configuration.Routes.addItem
import com.exawizards.multiplatform_template.configuration.Routes.root
import com.exawizards.multiplatform_template.configuration.Routes.todoList
import com.exawizards.multiplatform_template.configuration.models.TodoItem
import com.exawizards.multiplatform_template.configuration.models.TodoList
import com.exawizards.multiplatform_template.ktor_client.cio.client

@Composable
fun App() {
    val appState = getAppState()

    Scaffold(
        topBar = { PlatformInfo() },
        bottomBar = { ServerInfo() }
    ) {
        client.launch {
            appState.value = todoList()
        }

        Column {
            TodoList(appState::value)
            AddNewItem { itemText ->
                client.launch {
                    appState.value = addItem(TodoItem(itemText))
                }
            }
        }
    }
}

@Composable
private fun getAppState(
    list: TodoList = TodoList(emptyList())
) = mutableStateOf(list)

@Composable
private fun PlatformInfo() {
    val platformName = getPlatformName()
    Text("Running on $platformName")
}

@Composable
private fun ServerInfo() {
    var rootResponse: String by remember {
        mutableStateOf("Requesting...")
    }
    client.launch {
        rootResponse = root().content
    }

    Text("Server says: $rootResponse")
}

@Composable
private fun TodoList(provider: () -> TodoList) {
    Column {
        provider().items.forEach { listItem ->
            Text(listItem.title)
        }
    }
}
