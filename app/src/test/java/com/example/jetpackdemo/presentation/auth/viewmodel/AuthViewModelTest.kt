package com.example.jetpackdemo.presentation.auth.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.jetpackdemo.R
import com.example.jetpackdemo.domain.model.*
import com.example.jetpackdemo.domain.usecase.ChangePasswordUseCase
import com.example.jetpackdemo.domain.usecase.ForgotPasswordUseCase
import com.example.jetpackdemo.domain.usecase.LoginUseCase
import com.example.jetpackdemo.domain.usecase.RegisterUseCase
import com.example.jetpackdemo.presentation.auth.state.AuthUiState
import com.example.jetpackdemo.utils.UserPreferences
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/*#
coVerify is a function provided by MockK that's used to verify that a suspending function was called during a test.
*/

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {



    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private val testDispatcher = StandardTestDispatcher()

    private lateinit var loginUseCase: LoginUseCase
    private lateinit var registerUseCase: RegisterUseCase
    private lateinit var forgotPasswordUseCase: ForgotPasswordUseCase
    private lateinit var changePasswordUseCase: ChangePasswordUseCase
    private lateinit var userPreferences: UserPreferences
    private lateinit var viewModel: AuthViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)


        loginUseCase = mockk()
        registerUseCase = mockk()
        forgotPasswordUseCase = mockk()
        changePasswordUseCase = mockk()
        userPreferences = mockk(relaxed = true)

        every { userPreferences.isLoggedIn } returns MutableStateFlow(false)

        viewModel = AuthViewModel(
            loginUseCase,
            registerUseCase,
            forgotPasswordUseCase,
            changePasswordUseCase,
            userPreferences,testDispatcher,testDispatcher// inject test dispatcher here
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    // region --- onLogin Tests ---

    @Test
    fun `onLogin with empty email emits email_error`() = runTest {
        viewModel.onLogin("", "password")
        assertEquals(AuthUiState.ErrorWithId(R.string.email_error), viewModel.uiState.first())
    }

    @Test
    fun `onLogin with invalid email emits valid_email`() = runTest {
        viewModel.onLogin("invalid-email", "password")
        assertEquals(AuthUiState.ErrorWithId(R.string.valid_email), viewModel.uiState.first())
    }

    @Test
    fun `onLogin with empty password emits password_error`() = runTest {
        viewModel.onLogin("test@example.com", "")
        assertEquals(AuthUiState.ErrorWithId(R.string.password_error), viewModel.uiState.first())
    }

    @Test
    fun `onLogin with correct credentials emits Loading then Success`() = runTest(testDispatcher) {
        // Arrange
        val email = "test@example.com"
        val password = "password"
        val user = User(
            email = email,
            password = password, // must match
            username = "Test User",
            phoneNumber = "1234567890",
            imageUrl = ""
        )
        coEvery { loginUseCase(email, password) } returns user

        val results = mutableListOf<AuthUiState>()

        // ✅ Start collecting before calling onLogin()
        val job = launch {
            viewModel.uiState.take(2).toList(results)
        }

        // Act
        viewModel.onLogin(email, password)

        advanceUntilIdle() // Run all coroutines

        // Assert
        assertEquals(AuthUiState.Loading, results[0])
        assertEquals(AuthUiState.Success, results.drop(1).first())

        coVerify { userPreferences.saveProfile(user) }
        coVerify { userPreferences.setLoggedIn(true) }

        job.cancel()
    }

    @Test
    fun `onLogin with valid input but wrong password emits enter_correct_password`() = runTest {

        // Arrange
        val email = "test@example.com"
        val inputPassword = "password"
        val wrongStoredPassword = "wrong_pass"

        val user = User(
            email = email,
            password = wrongStoredPassword, // 👈 Wrong password
            username = "Test User",
            phoneNumber = "1234567890",
            imageUrl = ""
        )
        coEvery { loginUseCase(email, inputPassword) } returns user
        val results = mutableListOf<AuthUiState>()
        val job = launch {
            viewModel.uiState.take(2).toList(results)
        }
        viewModel.onLogin(email,inputPassword)

        advanceUntilIdle()
        // Assert
        assertEquals(AuthUiState.Loading, results[0])
        assertEquals(AuthUiState.ErrorWithId(R.string.enter_correct_password), results.drop(1).first())

        job.cancel()
    }

    @Test
    fun `onLogin with no user returned emits valid_credential_error`() = runTest {
        // Arrange
        val email = "test@example.com"
        val password = "password"

        coEvery { loginUseCase(email, password) } returns null

        val results = mutableListOf<AuthUiState>()
        val job = launch {
            viewModel.uiState.take(2).toList(results)
        }

        // Act
        viewModel.onLogin(email, password)
        advanceUntilIdle()

        // Assert
        assertEquals(AuthUiState.Loading, results.first())
        assertEquals(AuthUiState.ErrorWithId(R.string.valid_credential_error), results.drop(1).first())

        job.cancel()
    }

    // endregion

    // region --- onRegister Tests ---

    @Test
    fun `onRegister with empty name emits name_error`() = runTest {
        viewModel.onRegister("a@gmail.com", "pass", "", "123", "pass")
        assertEquals(AuthUiState.ErrorWithId(R.string.name_error), viewModel.uiState.first())
    }

    @Test
    fun `onRegister with invalid email emits valid_email`() = runTest {
        viewModel.onRegister("name", "pass", "bademail", "123", "pass")
        assertEquals(AuthUiState.ErrorWithId(R.string.valid_email), viewModel.uiState.first())
    }

    @Test
    fun `onRegister with phone empty emits phone_error`() = runTest {
        viewModel.onRegister("name@gmail.com", "pass", "name", "", "pass")
        assertEquals(AuthUiState.ErrorWithId(R.string.phone_error), viewModel.uiState.first())
    }

    @Test
    fun `onRegister with password empty emits password_error`() = runTest {
        viewModel.onRegister("test@example.com", "", "name", "123", "pass")
        assertEquals(AuthUiState.ErrorWithId(R.string.password_error), viewModel.uiState.first())
    }

    @Test
    fun `onRegister with confirm password empty emits confirm_password_error`() = runTest {
        viewModel.onRegister("test@example.com", "pass", "name", "123", "")
        assertEquals(AuthUiState.ErrorWithId(R.string.confirm_password_error), viewModel.uiState.first())
    }

    @Test
    fun `onRegister with mismatch passwords emits password_same_error`() = runTest {
        viewModel.onRegister("test@example.com", "pass1", "name", "123", "pass2")
        assertEquals(AuthUiState.ErrorWithId(R.string.password_same_error), viewModel.uiState.first())
    }

    @Test
    fun `onRegister success emits Success and saves user`() = runTest(testDispatcher) {
        val user = User("ankit", "test@example.com", "pass", "123", "")
        coEvery { registerUseCase(user) } returns true


        val results = mutableListOf<AuthUiState>()
        val job = launch {
            viewModel.uiState.take(2).toList(results)
        }
        viewModel.onRegister("test@example.com", "pass", "ankit", "123", "pass")
        advanceUntilIdle()

        assertEquals(AuthUiState.Success, results.drop(1).first()) // Skip Loading
        coVerify { userPreferences.saveProfile(user) }
        coVerify { userPreferences.setLoggedIn(true) }
        job.cancel()
    }

    @Test
    fun `onRegister failure emits valid_credential_error`() = runTest {
        val user = User("name", "test@example.com", "pass", "123", "")
        coEvery { registerUseCase(user) } returns false
        val results = mutableListOf<AuthUiState>()
        val job = launch {
            viewModel.uiState.take(2).toList(results)
        }

        viewModel.onRegister("test@example.com", "pass", "name", "123", "pass")

        advanceUntilIdle()

        assertEquals(AuthUiState.ErrorWithId(R.string.valid_credential_error), results.drop(1).first())
        job.cancel()
    }

    // endregion

    @Test
    fun `findUserDetailByEmail with valid email emits Loading then Result`() = runTest {
        // Arrange
        val email = "test@example.com"
        val user = User(email, "pass", "Test", "1234567890", "")
        coEvery { forgotPasswordUseCase(email) } returns user

        val results = mutableListOf<AuthUiState>()
        val job = launch {
            viewModel.uiState.take(2).toList(results)
        }

        // Act
        viewModel.findUserDetailByEmail(email)
        advanceUntilIdle()

        // Assert
        assertEquals(AuthUiState.Loading, results.first())
        assertEquals(AuthUiState.Result(user), results.drop(1).first())

        job.cancel()
    }

    @Test
    fun `findUserDetailByEmail with no user emits Loading then ErrorWithId(valid_email)`() = runTest {
        // Arrange
        val email = "notfound@example.com"
        coEvery { forgotPasswordUseCase(email) } returns null

        val results = mutableListOf<AuthUiState>()
        val job = launch {
            viewModel.uiState.take(2).toList(results)
        }

        // Act
        viewModel.findUserDetailByEmail(email)
        advanceUntilIdle()

        // Assert
        assertEquals(AuthUiState.Loading, results[0])
        assertEquals(AuthUiState.ErrorWithId(R.string.valid_email), results[1])

        job.cancel()
    }

//Change Password
    @Test
    fun `onChangePassword with valid input emits Loading then Success`() = runTest {
        coEvery { changePasswordUseCase.changePassword(password = "newPass", email = "test@example.com") } returns true

        val results = mutableListOf<AuthUiState>()
        val job = launch {
            viewModel.uiState.take(2).toList(results)
        }

        viewModel.onChangePassword("currentPass", "newPass", "newPass", "test@example.com")
        advanceUntilIdle()

        assertEquals(AuthUiState.Loading, results[0])
        assertEquals(AuthUiState.Success, results[1])
        job.cancel()
    }


    @Test
    fun `onChangePassword with valid input but failure emits Error`() = runTest {
        coEvery { changePasswordUseCase.changePassword(password = "newPass", email = "test@example.com") } returns false

        val results = mutableListOf<AuthUiState>()
        val job = launch {
            viewModel.uiState.take(2).toList(results)
        }

        viewModel.onChangePassword("currentPass", "newPass", "newPass", "test@example.com")
        advanceUntilIdle()

        assertEquals(AuthUiState.Loading, results[0])
        assertEquals(AuthUiState.Error("Something went wrong"), results[1])
        job.cancel()
    }
}