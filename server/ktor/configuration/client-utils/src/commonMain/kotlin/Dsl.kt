package com.exawizards.multiplatform_template.server.ktor.configuration.client_utils

import com.exawizards.multiplatform_template.server.ktor.configuration.*
import com.exawizards.multiplatform_template.server.ktor.configuration.HttpRequest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.*
import io.ktor.serialization.kotlinx.json.*

data class Endpoint<INPUT, OUTPUT>(
    val route: HttpRequest<INPUT, OUTPUT>,
    val client: HttpClient
) {
    suspend inline operator fun <reified T : INPUT, reified R : OUTPUT> invoke(
        input: T
    ): R {
        val response = when (route) {
            is Get<*> -> client.get(route.fullPath)
            is Post<*, *> -> client.post(route.fullPath) {
                contentType(ContentType.Application.Json)
                setBody(input)
            }
            else -> throw IllegalArgumentException("Unrecognized HttpMethod in client configuration.")
        }

        return response.body()
    }
}

val HttpRequest<*, *>.fullPath: String
    get() = "http://${Configuration.host}:${Configuration.port}${path}"

suspend inline operator fun <reified OUTPUT : Model> Endpoint<None, OUTPUT>.invoke(): OUTPUT =
    invoke(None)

fun <T : HttpClientEngineConfig> getClient(engine: HttpClientEngineFactory<T>) = WrappedClient(
    HttpClient(engine) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }
)

class WrappedClient(
    instance: HttpClient
) : RouteContract<Endpoint<out Model, out Model>> {
    override val root = Endpoint(Routes.root, instance)
    override val todoList = Endpoint(Routes.todoList, instance)
    override val addItem = Endpoint(Routes.addItem, instance)
}
