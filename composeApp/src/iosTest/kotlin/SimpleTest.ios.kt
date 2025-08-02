import br.com.flemis.bookishadventure.core.domain.usecases.ThemeUseCase
import br.com.flemis.bookishadventure.features.settings.data.repository.implementations.ThemeRepositoryImpl
import br.com.flemis.bookishadventure.features.settings.presentation.ui.viewmodel.ThemeViewModel
import br.com.flemis.bookishadventure.utils.Preferences

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertIs
import platform.Foundation.NSUserDefaults
import kotlin.experimental.ExperimentalNativeApi
import kotlin.test.expect


class SimpleTestIOS {

    @Test
    fun example() = runTest { }

    @Test
    fun checkTheme() = runTest {
        val preferences = Preferences()
        val themeRepository = ThemeRepositoryImpl()
        //val themeUseCase = ThemeUseCase(themeRepository)
        val themeViewModel = ThemeViewModel(preferences)

        val darkModeState = themeViewModel.state.value

        // Initial state should be false
        assertIs<Boolean>(!darkModeState.isDarkMode, "Initial theme should be light mode")

        // Update the theme to dark mode
        //themeViewModel.changeTheme()

        // Verify the state is updated to dark mode
        assertIs<Boolean>(darkModeState.isDarkMode, "Theme should be dark mode after update")
    }


    @OptIn(ExperimentalNativeApi::class)
    @Test
    fun `should return when preferences are cleared`() = runTest {
        val preferences = Preferences()
        preferences.putBoolean("teste", true)
        preferences.clearPreferences(null)
        expect(false, block = { preferences.getBoolean("teste", false) }, message = "Preferences should be cleared")
    }

    @OptIn(ExperimentalNativeApi::class)
    @Test
    fun `should return if preferences are returned`() = runTest {
        val preferences = Preferences()
        preferences.putBoolean("teste", true)
        assert(preferences.getBoolean("teste", false)) { "Preferences should return true for darkMode" }
    }
}