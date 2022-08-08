import androidx.compose.runtime.*
import com.exawizards.multiplatform_template.platform_utils.getPlatformName
import com.exawizards.multiplatform_template.server.ktor.client.js.Client.from
import com.exawizards.multiplatform_template.server.ktor.configuration.Configuration
import kotlinx.coroutines.launch
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
    var count: Int by mutableStateOf(0)
    val rootResponse = remember {
        mutableStateOf("Requesting...")
    }
    val listResponse = remember {
        mutableStateOf(emptyList<String>())
    }

    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        rootResponse.value = from(Configuration.Routes.Root)
    }
    coroutineScope.launch {
        listResponse.value = from(Configuration.Routes.List).split("\n")
    }

    Div({ style { padding(25.px) } }) {
        Button(attrs = {
            onClick { count -= 1 }
        }) {
            Text("-")
        }

        Span({ style { padding(15.px) } }) {
            Text("$count")
        }

        Button(attrs = {
            onClick { count += 1 }
        }) {
            Text("+")
        }
    }
    Div({ style { padding(25.px) } }) {
        Text("Running on ${getPlatformName()}")
    }
    Div({ style { padding(25.px) } }) {
        Text("Server says: ${rootResponse.value}")
    }
    Div({ style { padding(25.px) } }) {
        Text("List from the server:")
        listResponse.value.forEach { listItem ->
            Div({ style { padding(5.px) } }) {
                Text(listItem)
            }
        }
    }
}
