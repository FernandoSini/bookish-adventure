import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.expect

class SimpleTest {

    @Test
    fun example() = runTest {

        val expected = ""
        val b = ""
        //with(""){}

        //assertTrue("", )
        expect("", { expected })
    }
}