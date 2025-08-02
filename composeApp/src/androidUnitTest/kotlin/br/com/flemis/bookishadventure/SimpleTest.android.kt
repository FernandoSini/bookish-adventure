package br.com.flemis.bookishadventure

import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.flemis.bookishadventure.utils.Preferences
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(AndroidJUnit4::class)
class SimpleTestAndroid{

    @Test
    fun example() = runTest {

        // assertTrue("".isEmpty(), "it should be excpected to be empty")
        /*     assertContains(
                 iterable = MutableList(5, init = { mutableStateOf("a").value }),
                 element = "a",
                 message = "it should contain a"
             )*/


        val expected = "jakshdjkas"
        val b = ""
        //with(""){}

        //assertTrue("", )
        //  expect(expected, { expected })
        assert(expected.isNotEmpty(), { "it should be expected to not be empty" })

    }

    /*  @get:Rule
      val composeTestRule = createComposeRule()

      @Test
      fun checkTheme() = runTest {


              val themeViewModel =  ThemeViewModel()

              val darkModeState = themeViewModel.state.value

             // composeTestRule.onNodeWithTag("initial_scaffold").assertExists("Is is not dark")

              // Initial state should be false
              assertTrue(!darkModeState.isDarkMode, "Initial theme should be light mode")

              // Wait for the state to update
              //composeTestRule.waitForIdle()
              // Update the theme to dark mode
              themeViewModel.changeTheme()


              // Verify the state is updated to dark mode
              assertTrue(darkModeState.isDarkMode, "Theme should be dark mode after update")





      }*/



  /*  @get:Rule
    val composeTestRule = createComposeRule()


    @OptIn(ExperimentalTestApi::class)
    @Test
    fun checkTheme() = runComposeUiTest {
        val context: Context = ApplicationProvider.getApplicationContext()
        SettingsInitializer().create(context)
        setContent {
            val settings = Settings()
            val themeRepository = ThemeRepositoryImpl()
        val themeUseCase = ThemeUseCase(themeRepository)
        val themeViewModel = viewModel<ThemeViewModel>{ ThemeViewModel(settings = settings, themeUseCase = themeUseCase)}

        val darkModeState by themeViewModel.state.collectAsState()

        // Initial state should be false
            assertEquals(false,darkModeState.isDarkMode, "Initial theme should be light mode")

        // Update the theme to dark mode
        themeViewModel.changeTheme()

        // Verify the state is updated to dark mode
        assertEquals(true,darkModeState.isDarkMode, "Theme should be dark mode after update")
}

    }*/

   /* @Test
    fun `should return true when string is not empty`() = runTest {
        val str = "Hello, World!"
        assert(str.isNotEmpty()) { "String should not be empty" }
    }*/

    @Test
    fun `should return when preferences are cleared`() = runTest {
        //val context: Context = ApplicationProvider.getApplicationContext()
        val preferences = Preferences()
        preferences.putBoolean("teste", true)
        preferences.clearPreferences(null)
        assert(!preferences.getBoolean("teste", false)) { "Preferences should be cleared" }
    }

    @Test
    fun `should return if preferences are returned`() = runTest {
        val preferences = Preferences()
        preferences.putBoolean("teste", true)
        assert(preferences.getBoolean("teste", false)) { "Preferences should return true for darkMode" }
    }


}