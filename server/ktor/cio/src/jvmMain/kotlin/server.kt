package com.exawizards.multiplatform_template.server.ktor.cio

import com.exawizards.multiplatform_template.server.ktor.cio.plugins.configureRouting
import io.ktor.server.engine.*
import io.ktor.server.cio.*

actual fun startServer(port: Int, host: String) {
    embeddedServer(CIO, port = port, host = host) {
        configureRouting()
    }.start(wait = true)
}
