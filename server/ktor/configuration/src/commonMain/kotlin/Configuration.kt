package com.exawizards.multiplatform_template.server.ktor.configuration

import kotlinx.serialization.Serializable

object Configuration {
    const val port: Int = 8081
    const val host: String = "0.0.0.0"
}

sealed class Model
@Serializable
data class TodoItem(val title: String) : Model()
@Serializable
data class TodoList(val items: List<TodoItem>) : Model()
@Serializable
data class PlainText(val content: String) : Model()
object None : Model()
