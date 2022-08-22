package com.exawizards.multiplatform_template.compose_ui

import androidx.compose.foundation.layout.Column
import com.exawizards.multiplatform_template.platform_utils.getPlatformName
import com.exawizards.multiplatform_template.server.ktor.client.cio.client
import com.exawizards.multiplatform_template.server.ktor.configuration.TodoItem
import com.exawizards.multiplatform_template.server.ktor.configuration.TodoList
import com.exawizards.multiplatform_template.server.ktor.configuration.client_utils.invoke
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext

@Composable
fun App() {
    Scaffold(
        topBar = { PlatformInfo() },
        bottomBar = { ServerInfo() }
    ) {
        val coroutineScope = rememberCoroutineScope()
        var todoList: TodoList by remember {
            mutableStateOf(TodoList(emptyList()))
        }
        LaunchedEffect(Unit) {
            todoList = client.todoList()
        }

        Column {
            TodoList(todoList)
            AddNewItem { itemText ->
                coroutineScope.launch(EmptyCoroutineContext) {
                    todoList = client.addItem(TodoItem(itemText))
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
    LaunchedEffect(Unit) {
        rootResponse = client.root().content
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
