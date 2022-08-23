package com.exawizards.multiplatform_template.server.ktor.cio.plugins

import com.exawizards.multiplatform_template.platform_utils.getPlatformName
import com.exawizards.multiplatform_template.server.ktor.configuration.*
import com.exawizards.multiplatform_template.server.ktor.configuration.server_utils.configureRoutes
import com.exawizards.multiplatform_template.server.ktor.configuration.server_utils.handleRequest
import io.ktor.server.application.*

fun Application.mainModule() {
    val storage = Storage()
    suspend fun addItem(item: TodoItem) = suspend { storage.add(item) }()
    suspend fun getList(): TodoList = suspend { storage.getTodoList() }()

    configureRoutes {
        handleRequest(Routes.root)
                .respondWith { PlainText("Hello World from ${getPlatformName()}!") }

        handleRequest(Routes.todoList)
                .respondWith { getList() }

        handleRequest(Routes.addItem)
                .doFirst { input ->
                    addItem(input)
                }.respondWith { getList() }
    }
}
