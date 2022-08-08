package com.exawizards.multiplatform_template.server.ktor.cio.plugins

import com.exawizards.multiplatform_template.platform_utils.getPlatformName
import com.exawizards.multiplatform_template.server.ktor.configuration.server_utils.dsl
import com.exawizards.multiplatform_template.server.ktor.configuration.Configuration.Routes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        Routes.all.forEach(::configureRoute)
    }
}

private fun Routing.configureRoute(route: Routes) = when (route) {
    Routes.Root -> dsl(route) {
        call.respondText("Hello World from ${getPlatformName()}!")
    }
    else -> throw IllegalArgumentException("$route does not exist")
}
