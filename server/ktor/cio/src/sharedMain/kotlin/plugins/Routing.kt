package com.exawizards.multiplatform_template.server.ktor.cio.plugins

import com.exawizards.multiplatform_template.platform_utils.getPlatformName
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World from ${getPlatformName()}!")
        }
    }
}
