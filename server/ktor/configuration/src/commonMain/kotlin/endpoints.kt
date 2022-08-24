package com.exawizards.multiplatform_template.server.ktor.configuration

interface HttpRequest<T, S> {
    val path: String
}
interface Get<T : Model> : HttpRequest<Unit, T>
interface Post<T : Model, S: Model> : HttpRequest<T, S>
