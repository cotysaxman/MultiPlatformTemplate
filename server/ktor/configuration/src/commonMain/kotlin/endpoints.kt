package com.exawizards.multiplatform_template.server.ktor.configuration

sealed interface HttpRequest<T, S> {
    val path: String
}

interface Get<T: Model>: HttpRequest<None, T>
interface Post<T: Model, S: Model>: HttpRequest<T, S>

interface RouteMap<T> {
    val root: T
    val todoList: T
    val addItem: T
}

object Routes : RouteMap<HttpRequest<out Model, out Model>> {
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
