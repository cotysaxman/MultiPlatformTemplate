package com.exawizards.multiplatform_template.configuration.models

import kotlinx.serialization.Serializable


sealed class Model
@Serializable
data class TodoItem(val title: String) : Model()
@Serializable
data class TodoList(val items: List<TodoItem>) : Model()
@Serializable
data class PlainText(val content: String) : Model()
