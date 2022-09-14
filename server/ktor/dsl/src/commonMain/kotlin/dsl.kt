package com.exawizards.multiplatform_template.server.ktor.dsl

import com.exawizards.multiplatform_template.configuration.dsl.*
import com.exawizards.multiplatform_template.configuration.models.*
import io.ktor.http.HttpMethod
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

interface RouteWrapper<IN, OUT> { val routeBuilder: Route }

class RouteConfigurationScope(val scope: Route) {
    inline fun <reified OUTPUT : Model> RouteWrapper<Unit, OUTPUT>.doFirst(
        crossinline block: suspend PipelineContext<Unit, ApplicationCall>.() -> Unit
    ) = apply { routeBuilder.apply { handle { block() } } }

    inline fun <
        reified INPUT : Model,
        reified OUTPUT : Model
    > RouteWrapper<INPUT, OUTPUT>.doFirst(
        crossinline body: suspend PipelineContext<Unit, ApplicationCall>.(INPUT) -> Unit
    ) = apply {
        routeBuilder.apply {
            handle {
                val input = call.receive(INPUT::class)
                body(input)
            }
        }
    }

    inline fun <reified OUTPUT : Model> RouteWrapper<*, OUTPUT>.respondWith(
        crossinline responseProvider: suspend () -> OUTPUT
    ) = apply {
        routeBuilder.apply {
            handle {
                call.respond(responseProvider())
            }
        }
    }

    inline operator fun <reified INPUT, reified OUTPUT> HttpRequest<INPUT, OUTPUT>.invoke(
        crossinline block: RouteWrapper<INPUT, OUTPUT>.() -> Unit
    ) {
        val newRoute = scope.route(path, method) {}
        val context = object : RouteWrapper<INPUT, OUTPUT> { override val routeBuilder = newRoute }
        block(context)
    }
}

private fun Route.routingScope() = RouteConfigurationScope(this)

fun Application.configureRoutes(block: RouteConfigurationScope.() -> Unit) {
    routing {
        block(routingScope())
    }
}

val HttpRequest<*, *>.method: HttpMethod
    get() = when (this) {
        is Get<*> -> HttpMethod.Get
        is Post<*, *> -> HttpMethod.Post
        else -> throw IllegalArgumentException("Unrecognized HttpMethod in server configuration.")
    }
