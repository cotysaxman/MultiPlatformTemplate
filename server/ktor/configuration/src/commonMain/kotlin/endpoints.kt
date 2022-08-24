package com.exawizards.multiplatform_template.server.ktor.configuration

interface HttpRequest<T : Model, S : Model> {
    val path: String
}

interface Provider<T : Model>
interface Receiver<T : Model>
interface NonReceiver
interface Get<T : Model> : HttpRequest<None, T>, Provider<T>, NonReceiver
interface Post<T : Model, S: Model> : HttpRequest<T, S>, Provider<S>, Receiver<T>

interface RouteContract<T> {
    val root: T
    val todoList: T
    val addItem: T
}
