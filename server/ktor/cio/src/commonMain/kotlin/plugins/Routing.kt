package com.exawizards.multiplatform_template.server.ktor.cio.plugins

import com.exawizards.multiplatform_template.platform_utils.getPlatformName
import com.exawizards.multiplatform_template.server.ktor.configuration.*
import com.exawizards.multiplatform_template.server.ktor.configuration.server_utils.configureRoutes
import io.ktor.server.application.*

fun Application.mainModule() {
    val storage = Storage()
    suspend fun addItem(item: TodoItem) = suspend { storage.add(item) }()
    suspend fun getList(): TodoList = suspend { storage.getTodoList() }()

    configureRoutes {
        Routes.root {
            respondWith { PlainText("Hello World from ${getPlatformName()}!") }
        }

        Routes.todoList {
            respondWith { getList() }
        }

        Routes.addItem {
            doFirst { input ->
                addItem(input)
            }.respondWith { getList() }
        }
    }
}
