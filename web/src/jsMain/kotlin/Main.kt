import androidx.compose.runtime.*
import com.exawizards.multiplatform_template.platform_utils.getPlatformName
import com.exawizards.multiplatform_template.server.ktor.client.js.client
import com.exawizards.multiplatform_template.server.ktor.configuration.TodoItem
import com.exawizards.multiplatform_template.server.ktor.configuration.TodoList
import com.exawizards.multiplatform_template.server.ktor.configuration.client_utils.invoke
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        App()
    }
}

@Composable
fun App() {
    var rootResponse: String by remember {
        mutableStateOf("Requesting...")
    }
    var todoList: TodoList by remember {
        mutableStateOf(TodoList(emptyList()))
    }
    var itemToAdd: String by remember {
        mutableStateOf("")
    }

    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        rootResponse = client.root().content
    }
    coroutineScope.launch {
        todoList = client.todoList()
    }

    Div({ style { padding(25.px) } }) {
        Text("Running on ${getPlatformName()}")
    }
    Div({ style { padding(25.px) } }) {
        Text("Server says: $rootResponse")
    }
    Div({ style { padding(25.px) } }) {
        Text("List from the server:")
        todoList.items.forEach { listItem ->
            Div({ style { padding(5.px) } }) {
                Text(listItem.title)
            }
        }
    }
    Div({ style { padding(25.px) } }) {
        Text("Add new item:")
        Input(type = InputType.Text) {
            value(itemToAdd)
            onInput { event -> itemToAdd = event.value }
        }
        Button(attrs = {
            onClick {
                coroutineScope.launch {
                    todoList = client.addItem(TodoItem(itemToAdd))
                    itemToAdd = ""
                }
            }
        }) {
            Text("Add")
        }
    }
}
