package com.exawizards.multiplatform_template.server.ktor.cio.plugins

import com.exawizards.multiplatform_template.platform_utils.getPlatformName
import com.exawizards.multiplatform_template.server.ktor.configuration.*
import com.exawizards.multiplatform_template.server.ktor.configuration.server_utils.handleRequest
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.mainModule() {
    val storage = Storage()
    suspend fun addItem(item: TodoItem) = suspend { storage.add(item) }()
    suspend fun getList(): TodoList = suspend { storage.getTodoList() }()

    routing {
        object : RouteContract<Route> {
            override val root = handleRequest(Routes.root)
                .respondWith { PlainText("Hello World from ${getPlatformName()}!") }

            override val todoList = handleRequest(Routes.todoList)
                .respondWith { getList() }

            override val addItem = handleRequest(Routes.addItem)
                .doFirst { input ->
                    addItem(input)
                }.respondWith { getList() }
        }

        get("/test") {
            addItem(TodoItem("test"))
            call.respondText("ok")
        }
    }
}
