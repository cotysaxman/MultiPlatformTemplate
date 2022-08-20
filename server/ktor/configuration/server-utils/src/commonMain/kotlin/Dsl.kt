package com.exawizards.multiplatform_template.server.ktor.configuration.server_utils

import com.exawizards.multiplatform_template.server.ktor.configuration.*
import io.ktor.http.HttpMethod
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import kotlin.reflect.KClass

sealed class WrappedRoute<INPUT : Model, OUTPUT : Model>(
    val route: Route
) {
    inline fun <reified R : OUTPUT> respondWith(
        crossinline responseProvider: suspend () -> R
    ) = route.apply {
        handle {
            call.respond(responseProvider())
        }
    }

    class Get<OUTPUT : Model>(
        route: Route
    ) : WrappedRoute<None, OUTPUT>(route) {
        fun doFirst(
            body: PipelineInterceptor<Unit, ApplicationCall>
        ): Get<in OUTPUT> = Get(
            route.apply {
                handle(body)
            }
        )
    }

    class Post<INPUT : Model, OUTPUT : Model>(
        route: Route,
        val inputClass: KClass<INPUT>
    ) : WrappedRoute<INPUT, OUTPUT>(route) {
        inline fun doFirst(
            crossinline body: suspend PipelineContext<Unit, ApplicationCall>.(INPUT) -> Unit
        ) = Post<INPUT, OUTPUT>(
            route.apply {
                handle {
                    val input = call.receive(inputClass)
                    body(input)
                }
            }, inputClass
        )
    }
}

inline fun <reified INPUT : Model, reified OUTPUT : Model> Route.handleRequest(
    request: Post<out INPUT, out OUTPUT>
) = WrappedRoute.Post<INPUT, OUTPUT>(route(request.path, HttpMethod.Post) {}, INPUT::class)

inline fun <reified OUTPUT : Model> Route.handleRequest(
    request: Get<out OUTPUT>
) = WrappedRoute.Get<OUTPUT>(route(request.path, HttpMethod.Get) {})


val HttpRequest<*, *>.method: HttpMethod
    get() = when (this) {
        is Get<*> -> HttpMethod.Get
        is Post<*, *> -> HttpMethod.Post
        else -> throw IllegalArgumentException("Unrecognized HttpMethod in server configuration.")
    }
