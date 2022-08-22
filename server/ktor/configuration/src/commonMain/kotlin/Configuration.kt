package com.exawizards.multiplatform_template.server.ktor.configuration

import kotlinx.serialization.Serializable

object Configuration {
    const val port: Int = 8081
    const val host: String = "0.0.0.0"
//    const val host: String = "10.0.2.2" // required for Android runs!
}

object Routes : RouteContract<HttpRequest<out Model, out Model>> {
    override val root = get<PlainText>("/")
    override val todoList = get<TodoList>("/items")
    override val addItem = post<TodoItem, TodoList>("/items")

    private inline fun <reified OUTPUT : Model> get(
        path: String
    ) = object : Get<OUTPUT> {
        override val path = path
    }

    private inline fun <reified INPUT : Model, reified  OUTPUT : Model> post(
        path: String
    ) = object : Post<INPUT, OUTPUT> {
        override val path = path
    }
}


sealed class Model
@Serializable
data class TodoItem(val title: String) : Model()
@Serializable
data class TodoList(val items: List<TodoItem>) : Model()
@Serializable
data class PlainText(val content: String) : Model()
object None : Model()
