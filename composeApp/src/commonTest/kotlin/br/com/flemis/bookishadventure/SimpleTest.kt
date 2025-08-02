@file:OptIn(ExperimentalTestApi::class)

package br.com.flemis.bookishadventure

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.runComposeUiTest
import br.com.flemis.bookishadventure.utils.Preferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.expect


class SimpleTest {
    @Test
    fun example() = runTest {


/*assertContains(
            iterable = MutableList(5, init = { mutableStateOf("a").value }),
            element = "a",
            message = "it should contain a"
        )
        val expected = ""
        val b = ""
        //with(""){}

        //assertTrue("", )
        expect("", { expected })*/

    }


   /* @Test
    fun checkTheme() = runComposeUiTest {
        *//*  setContent {
              val settings = Settings()
              val themeRepository = ThemeRepositoryImpl()
              val viewModel = koinViewModel<ThemeViewModel>()


              val darkModeState = viewModel.state.value

              // Initial state should be false
              assertEquals(false, darkModeState.isDarkMode, "Initial theme should be light mode")

              // Update the theme to dark mode
              // onNodeWithTag("ChangeThemeButton").performClick().run { viewModel.changeTheme() }
              viewModel.changeTheme()
              // Verify the state is updated to dark mode
              assertEquals(
                  expected = true,
                  actual = darkModeState.isDarkMode,
                  "Theme should be dark mode after update"
              )
          }*//*
    }*/

    @Test
   // fun `should return when preferences are cleared`() = runTest {
    fun testClearPreferences() = runTest {
        val preferences = Preferences()
        preferences.putBoolean("teste", true)
        preferences.clearPreferences("teste")
        expect(
            false,
            block = { preferences.getBoolean("teste", false) },
            message = "Preferences should be cleared and return false"
        )

    }

    @Test
   // fun `should return if preferences are returned`() = runTest {
    fun checkIfPreferencesIsReturned() = runTest {
        val preferences = Preferences()
        preferences.putBoolean("teste", true)
        expect(true, message = "Preferences should return true", block = { preferences.getBoolean("teste", false) })
    }
}
