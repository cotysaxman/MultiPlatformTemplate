package com.exawizards.multiplatform_template.server.ktor.cio

import com.exawizards.multiplatform_template.server.ktor.cio.plugins.configureRouting
import io.ktor.server.engine.*
import io.ktor.server.cio.*

fun main() {
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}
