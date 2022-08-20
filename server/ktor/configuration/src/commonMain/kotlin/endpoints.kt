package com.exawizards.multiplatform_template.server.ktor.configuration

sealed interface HttpRequest<T, S> {
    val path: String
}

interface Get<T: Model>: HttpRequest<None, T>
interface Post<T: Model, S: Model>: HttpRequest<T, S>

interface RouteContract<T> {
    val root: T
    val todoList: T
    val addItem: T
}
