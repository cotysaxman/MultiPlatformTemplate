package com.exawizards.multiplatform_template.configuration

import com.exawizards.multiplatform_template.configuration.dsl.RoutesDsl
import com.exawizards.multiplatform_template.configuration.dsl.get
import com.exawizards.multiplatform_template.configuration.dsl.post
import com.exawizards.multiplatform_template.configuration.models.PlainText
import com.exawizards.multiplatform_template.configuration.models.TodoItem
import com.exawizards.multiplatform_template.configuration.models.TodoList

object Configuration {
    const val port: Int = 8081
    const val host: String = "0.0.0.0"
//    const val host: String = "10.0.2.2" // required for Android runs!
}

object Routes : RoutesDsl {
    val root = get<PlainText>("/")
    val todoList = get<TodoList>("/items")
    val addItem = post<TodoItem, TodoList>("/items")
}
