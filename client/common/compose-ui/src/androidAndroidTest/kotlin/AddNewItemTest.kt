import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.exawizards.multiplatform_template.compose_ui.AddNewItem
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddNewItemTest {
    @Rule
    @JvmField
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    var textState: String? = null

    @Before
    fun before() {
        textState = null

        composeTestRule.setContent {
            AddNewItem {
                textState = it
            }
        }
    }

    @Test
    fun addItemButton_calls_passed_lambda_with_text_in_addItemTextField() {
        composeTestRule.onNodeWithTag("addItemTextField")
            .performTextInput("new value")
        composeTestRule.onNodeWithTag("addItemButton")
            .performClick()

        assertEquals("new value", textState)
    }
}
