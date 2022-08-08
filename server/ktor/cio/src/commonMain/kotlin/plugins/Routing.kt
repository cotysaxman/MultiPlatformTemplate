package com.exawizards.multiplatform_template.server.ktor.cio.plugins

import com.exawizards.multiplatform_template.platform_utils.getPlatformName
import com.exawizards.multiplatform_template.server.ktor.cio.State
import com.exawizards.multiplatform_template.server.ktor.configuration.server_utils.dsl
import com.exawizards.multiplatform_template.server.ktor.configuration.Configuration.Routes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.configureRouting(state: State) {
    routing {
        Routes.all.forEach { configureRoute(it, state) }
    }
}

private fun Routing.configureRoute(route: Routes, serverState: State) = when (route) {
    Routes.Root -> dsl(route) {
        call.respondText("Hello World from ${getPlatformName()}!")
    }
    Routes.List -> dsl(route) {
        call.respondText(serverState.getList())
    }
    Routes.AddItem -> dsl(route) {
        val content = call.parameters.getOrFail<String>("content")
        serverState.storage.add(content)

        call.respondText(serverState.getList())
    }
    else -> throw IllegalArgumentException("$route does not exist")
}

private fun State.getList() = storage.memoryStorage.map(Record::content).joinToString("\n")
