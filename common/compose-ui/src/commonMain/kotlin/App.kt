package com.exawizards.multiplatform_template.compose_ui

import androidx.compose.foundation.layout.Column
import com.exawizards.multiplatform_template.platform_utils.getPlatformName
import com.exawizards.multiplatform_template.server.ktor.client.cio.client
import com.exawizards.multiplatform_template.server.ktor.configuration.TodoItem
import com.exawizards.multiplatform_template.server.ktor.configuration.TodoList
import com.exawizards.multiplatform_template.server.ktor.configuration.Routes.addItem
import com.exawizards.multiplatform_template.server.ktor.configuration.Routes.root
import com.exawizards.multiplatform_template.server.ktor.configuration.Routes.todoList
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun App() {
    Scaffold(
        topBar = { PlatformInfo() },
        bottomBar = { ServerInfo() }
    ) {
        var todoList: TodoList by remember {
            mutableStateOf(TodoList(emptyList()))
        }
        client.launch {
            todoList = todoList()
        }

        Column {
            TodoList(todoList)
            AddNewItem { itemText ->
                client.launch {
                    todoList = addItem(TodoItem(itemText))
                }
            }
        }
    }
}

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
private fun TodoList(state: TodoList) {
    Column {
        state.items.forEach { listItem ->
            Text(listItem.title)
        }
    }
}
