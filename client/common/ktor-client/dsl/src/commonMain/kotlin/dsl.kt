package com.exawizards.multiplatform_template.ktor_client.dsl

import com.exawizards.multiplatform_template.configuration.*
import com.exawizards.multiplatform_template.configuration.dsl.Get
import com.exawizards.multiplatform_template.configuration.dsl.Post
import com.exawizards.multiplatform_template.configuration.dsl.HttpRequest
import com.exawizards.multiplatform_template.configuration.models.Model
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch

val HttpRequest<*, *>.fullPath: String
    get() = "http://${Configuration.host}:${Configuration.port}${path}"

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
    val client: HttpClient
) {
    fun launch(block: suspend WrappedClient.() -> Unit) = client.launch {
        block()
    }

    suspend inline operator fun <reified T : Model> Get<T>.invoke(): T =
        client.get(fullPath).body()

    suspend inline operator fun <reified INPUT : Model, reified OUTPUT : Model> Post<INPUT, OUTPUT>.invoke(
        input: INPUT
    ): OUTPUT = client.post(fullPath) {
        contentType(ContentType.Application.Json)
        setBody(input)
    }.body()
}
