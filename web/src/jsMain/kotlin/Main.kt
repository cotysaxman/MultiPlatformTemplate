import androidx.compose.runtime.*
import com.exawizards.multiplatform_template.platform_utils.getPlatformName
import com.exawizards.multiplatform_template.server.ktor.client.js.Client.get
import com.exawizards.multiplatform_template.server.ktor.client.js.Client.post
import com.exawizards.multiplatform_template.server.ktor.configuration.Configuration
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
    var listResponse: List<String> by remember {
        mutableStateOf(emptyList())
    }
    var itemToAdd: String by remember {
        mutableStateOf("")
    }

    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        rootResponse = get(Configuration.Routes.Root)
    }
    coroutineScope.launch {
        listResponse = get(Configuration.Routes.List).split("\n")
    }

    Div({ style { padding(25.px) } }) {
        Text("Running on ${getPlatformName()}")
    }
    Div({ style { padding(25.px) } }) {
        Text("Server says: $rootResponse")
    }
    Div({ style { padding(25.px) } }) {
        Text("List from the server:")
        listResponse.forEach { listItem ->
            Div({ style { padding(5.px) } }) {
                Text(listItem)
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
                    listResponse = post(Configuration.Routes.AddItem,
                        listOf("content" to itemToAdd)
                    ).split("\n")
                }
            }
        }) {
            Text("Add")
        }
    }
}
