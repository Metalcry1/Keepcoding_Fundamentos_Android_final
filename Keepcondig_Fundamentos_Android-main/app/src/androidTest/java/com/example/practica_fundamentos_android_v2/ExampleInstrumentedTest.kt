import com.example.practica_fundamentos_android_v2.UI.Login.LoginActivityViewModel
import com.example.practica_fundamentos_android_v2.UI.Login.LoginActivityViewModel.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginActivityViewModelTest {

    private lateinit var viewModel: LoginActivityViewModel

    @Before
    fun setup() {
        viewModel = LoginActivityViewModel()
    }

    @Test
    fun loginFunction() = runBlocking {
        // Arrange
        val email = "metalcry1@gmail.com"
        val password = "4312"
        val expectedToken =
            "eyJ0eXAiOiJKV1QiLCJraWQiOiJwcml2YXRlIiwiYWxnIjoiSFMyNTYifQ.eyJleHBpcmF0aW9uIjo2NDA5MjIxMTIwMCwiaWRlbnRpZnkiOiJCRjFBMDE5Qy1CRkFGLTQzOEYtOTBGRC03OEQ2N0JEOTNEQjciLCJlbWFpbCI6Im1ldGFsY3J5MUBnbWFpbC5jb20ifQ.Hp0hmOav5nvYng9MyAxDgU0mGsZFlxuhptWqnOAolUs"

        // Act
        viewModel.lauchLogin(email, password)

        // Assert
        var actualState: State? = null
        viewModel.uiState.collect {
            actualState = it
        }
        assertEquals(State.SucessLogin(expectedToken), actualState)
    }
}
