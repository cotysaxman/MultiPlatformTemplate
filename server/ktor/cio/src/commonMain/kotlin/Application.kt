package com.exawizards.multiplatform_template.server.ktor.cio

import com.exawizards.multiplatform_template.server.ktor.configuration.Configuration
import com.exawizards.multiplatform_template.server.ktor.cio.plugins.mainModule
import io.ktor.http.*
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(
        CIO,
        Configuration.port,
        Configuration.host
    ) {
        install(CORS) {
            anyHost()
            allowHeader(HttpHeaders.ContentType)
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        mainModule()
    }.start(wait = true)
}
