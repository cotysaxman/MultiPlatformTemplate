package com.exawizards.multiplatform_template.server.ktor.cio.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        routes.forEach { (path, verb, response) ->
            when (verb) {
                HttpVerb.GET -> get(path) {
                    when (response) {
                        is HttpResponse.TEXT ->
                            call.respondText(response.text)
                    }
                }
            }
        }
    }
}
