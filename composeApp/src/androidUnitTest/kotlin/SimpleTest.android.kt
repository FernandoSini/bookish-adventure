package br.com.fernandosini.bookishadventure

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertTrue
import kotlin.test.expect

class SimpleTest {

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
        assert(expected.isNotEmpty(), { "it should be excpected to not be empty" })

    }
}