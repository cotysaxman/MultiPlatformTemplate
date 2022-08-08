package com.exawizards.multiplatform_template.server.ktor.configuration.server_utils

import com.exawizards.multiplatform_template.server.ktor.configuration.Configuration.Routes
import com.exawizards.multiplatform_template.server.ktor.configuration.Http
import io.ktor.http.HttpMethod
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

fun Routing.dsl(
    route: Routes,
    body: suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit
) = route(route.path, mapMethod(route.method)) { handle(body) }

private fun mapMethod(method: Http): HttpMethod = when (method) {
    Http.DELETE -> HttpMethod.Delete
    Http.GET -> HttpMethod.Get
    Http.HEAD -> HttpMethod.Head
    Http.OPTION -> HttpMethod.Options
    Http.PATCH -> HttpMethod.Patch
    Http.POST -> HttpMethod.Post
    Http.PUT -> HttpMethod.Put
}
