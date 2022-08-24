package com.exawizards.multiplatform_template.server.ktor.configuration

import kotlinx.serialization.Serializable

object Configuration {
    const val port: Int = 8081
    const val host: String = "0.0.0.0"
//    const val host: String = "10.0.2.2" // required for Android runs!
}

object Routes : RoutesDsl {
    val root = get<PlainText>("/")
    val todoList = get<TodoList>("/items")
    val addItem = post<TodoItem, TodoList>("/items")
}

sealed class Model
@Serializable
data class TodoItem(val title: String) : Model()
@Serializable
data class TodoList(val items: List<TodoItem>) : Model()
@Serializable
data class PlainText(val content: String) : Model()
