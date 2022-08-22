import androidx.compose.runtime.*
import com.exawizards.multiplatform_template.platform_utils.getPlatformName
import com.exawizards.multiplatform_template.server.ktor.client.js.client
import com.exawizards.multiplatform_template.server.ktor.configuration.TodoItem
import com.exawizards.multiplatform_template.server.ktor.configuration.TodoList
import com.exawizards.multiplatform_template.server.ktor.configuration.client_utils.invoke
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Text

@Composable
fun App() {
    var todoList: TodoList by remember {
        mutableStateOf(TodoList(emptyList()))
    }

    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        todoList = client.todoList()
    }

    PlatformInfo()
    ServerInfo()
    TodoList(todoList)

    AddNewItem {
        coroutineScope.launch {
            todoList = client.addItem(TodoItem(it))
        }
    }
}

@Composable
fun PlatformInfo() {
    Div({ style { padding(25.px) } }) {
        Text("Running on ${getPlatformName()}")
    }
}

@Composable
fun TodoList(state: TodoList) {
    Div({ style { padding(25.px) } }) {
        Text("List from the server:")
        state.items.forEach { listItem ->
            Div({ style { padding(5.px) } }) {
                Text(listItem.title)
            }
        }
    }
}

@Composable
fun ServerInfo() {
    val coroutineScope = rememberCoroutineScope()
    var rootResponse: String by remember {
        mutableStateOf("Requesting...")
    }
    coroutineScope.launch {
        rootResponse = client.root().content
    }

    Div({ style { padding(25.px) } }) {
        Text("Server says: $rootResponse")
    }
}

@Composable
fun AddNewItem(addItem: (String) -> Unit) {
    var itemToAdd: String by remember {
        mutableStateOf("")
    }

    Div({ style { padding(25.px) } }) {
        Text("Add new item:")
        Input(type = InputType.Text) {
            value(itemToAdd)
            onInput { event -> itemToAdd = event.value }
        }
        Button(attrs = {
            onClick {
                addItem(itemToAdd)
                itemToAdd = ""
            }
        }) {
            Text("Add")
        }
    }
}