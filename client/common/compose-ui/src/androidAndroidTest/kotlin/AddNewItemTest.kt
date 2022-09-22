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

    var textState = "unmodified"

    @Before
    fun before() {
        textState = "unmodified"

        composeTestRule.setContent {
            AddNewItem {
                textState = it
            }
        }
    }

    @Test fun addNewItem() {
        composeTestRule.onNodeWithTag("addItemTextField")
            .performTextInput("new value")
        composeTestRule.onNodeWithTag("addItemButton")
            .performClick()

        assertEquals("new value", textState)
    }
}