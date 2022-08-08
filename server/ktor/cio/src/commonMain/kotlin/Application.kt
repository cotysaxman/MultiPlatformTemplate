package com.exawizards.multiplatform_template.server.ktor.cio

import com.exawizards.multiplatform_template.server.ktor.configuration.Configuration
import com.exawizards.multiplatform_template.server.ktor.cio.plugins.configureRouting
import io.ktor.http.HttpHeaders
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.cors.routing.CORS

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
        configureRouting()
    }.start(wait = true)
}
