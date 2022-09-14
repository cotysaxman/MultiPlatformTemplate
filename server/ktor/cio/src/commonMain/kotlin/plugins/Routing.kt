package com.exawizards.multiplatform_template.server.ktor.cio.plugins

import com.exawizards.multiplatform_template.configuration.Routes.root
import com.exawizards.multiplatform_template.configuration.Routes.todoList
import com.exawizards.multiplatform_template.configuration.Routes.addItem
import com.exawizards.multiplatform_template.configuration.models.PlainText
import com.exawizards.multiplatform_template.configuration.models.TodoItem
import com.exawizards.multiplatform_template.configuration.models.TodoList
import com.exawizards.multiplatform_template.platform_utils.getPlatformName
import com.exawizards.multiplatform_template.server.ktor.dsl.*
import io.ktor.server.application.*

fun Application.mainModule(
    storage: Storage = Storage()
) {
    suspend fun addItem(item: TodoItem) = suspend { storage.add(item) }()
    suspend fun getList(): TodoList = suspend { storage.getTodoList() }()

    configureRoutes {
        root {
            respondWith { PlainText("Hello World from ${getPlatformName()}!") }
        }

        todoList {
            respondWith { getList() }
        }

        addItem {
            doFirst { input ->
                addItem(input)
            }.respondWith { getList() }
        }
    }
}
