package com.exawizards.multiplatform_template.server.ktor.configuration.server_utils

import com.exawizards.multiplatform_template.server.ktor.configuration.*
import io.ktor.http.HttpMethod
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*

interface RouteWrapper { val routeBuilder: Route }
interface Fun2Route<IN : Model, OUT : Model> : Fun0Route<OUT>, Receiver<IN>
interface Fun1Route<OUT : Model> : Fun0Route<OUT>, NonReceiver
interface Fun0Route<OUT : Model> : RouteWrapper, Provider<OUT>

class RouteConfigurationScope(val scope: Route) {
    inline fun <reified OUTPUT : Model> Fun1Route<OUTPUT>.doFirst(
        crossinline block: PipelineInterceptor<Unit, ApplicationCall>
    ) = apply { routeBuilder.apply { handle { block(Unit) } } }

    inline fun <
        reified INPUT : Model,
        reified OUTPUT : Model
    > Fun2Route<INPUT, OUTPUT>.doFirst(
        crossinline body: suspend PipelineContext<Unit, ApplicationCall>.(INPUT) -> Unit
    ) = apply {
        routeBuilder.apply {
            handle {
                val input = call.receive(INPUT::class)
                body(input)
            }
        }
    }

    inline fun <reified OUTPUT : Model> Fun0Route<OUTPUT>.respondWith(
        crossinline responseProvider: suspend () -> OUTPUT
    ) = apply {
        routeBuilder.apply {
            handle {
                call.respond(responseProvider())
            }
        }
    }

    inline operator fun <reified INPUT : Model, reified OUTPUT : Model> Post<INPUT, OUTPUT>.invoke(
        crossinline block: Fun2Route<INPUT, OUTPUT>.() -> Unit
    ) {
        val newRoute = scope.route(path, method) {}
        val context = object : Fun2Route<INPUT, OUTPUT> { override val routeBuilder = newRoute }
        block(context)
    }

    inline operator fun <reified OUTPUT : Model> Get<OUTPUT>.invoke(
        crossinline block: Fun1Route<OUTPUT>.() -> Unit
    ) {
        val newRoute = scope.route(path, method) {}
        val context = object : Fun1Route<OUTPUT> { override val routeBuilder = newRoute }
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
