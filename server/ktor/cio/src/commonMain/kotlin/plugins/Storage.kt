package com.exawizards.multiplatform_template.server.ktor.cio.plugins

import com.exawizards.multiplatform_template.configuration.models.TodoItem
import com.exawizards.multiplatform_template.configuration.models.TodoList

class Storage {
    fun getTodoList() = TodoList(memoryStorage)

    private var memoryStorage = listOf(
        TodoItem("first default item"),
        TodoItem("and a second"),
    )

    fun add(item: TodoItem) {
        memoryStorage = memoryStorage + item
    }
}
