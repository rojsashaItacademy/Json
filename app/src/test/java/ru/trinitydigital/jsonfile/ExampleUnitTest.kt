package ru.trinitydigital.jsonfile

import android.content.Context
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner
import ru.trinitydigital.jsonfile.ui.main.MainViewModel

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {

    private val ONE_WORD_MESSAGE = "ONE_WORD_MESSAGE"
    private val TWO_WORD_MESSAGE = "ONE WORD_MESSAGE"

    private val TWO_WORD_MESSAGE_RESULT = "WORD_MESSAGE ONE"
    private val TEN_WORD_MESSAGE = "WORD_MESSAGE ONE1 ONE2 ONE3 ONE4 ONE5 ONE6 ONE7 ONE8 ONE9"
    private val TEN_WORD_MESSAGERESULT = "ONE9 ONE8 ONE7 ONE6 ONE5 ONE4 ONE3 ONE2 ONE1 WORD_MESSAGE"

    private val TEN_WORD_MESSAGE_WITH_MORE_SPACES =
        "WORD_MESSAGE     ONE1 ONE2 ONE3 ONE4     ONE5 ONE6 ONE7 \n   ONE8 ONE9"
    private val TEN_WORD_MESSAGE_WITH_MORE_SPACES_RESULT =
        "ONE9 ONE8   \n ONE7 ONE6 ONE5     ONE4 ONE3 ONE2 ONE1     WORD_MESSAGE"


    private val SPACES_MESSAGE_RESULT = "     "
    private val SPACES_MESSAGE = "     "

    private val SPACES_MESSAGE_RESULT1 = "sasha     "
    private val SPACES_MESSAGE1 = "     sasha"


    @Mock
    private lateinit var context: Context

    private lateinit var vm: MainViewModel


    @Before
    fun before() {
        vm = MainViewModel(context)
    }

    @Test
    fun loadString() {
        `when`(context.getString(R.string.app_name1))
            .thenReturn("JsonFile")
        val viewModel = MainViewModel(context)

        val result = viewModel.loadWord()

        Assert.assertEquals("JsonFile", result)
    }

    @Test
    fun one_word_message_return_true() {
        val text = vm.replaceWords(ONE_WORD_MESSAGE)

        Assert.assertEquals(ONE_WORD_MESSAGE, text)
    }

    @Test
    fun two_word_message_return_true() {
        val text = vm.replaceWords(TWO_WORD_MESSAGE)

        Assert.assertEquals(TWO_WORD_MESSAGE_RESULT, text)
    }

    @Test
    fun ten_word_message_return_true() {
        val text = vm.replaceWords(TEN_WORD_MESSAGE)

        Assert.assertEquals(TEN_WORD_MESSAGERESULT, text)
    }

    @Test
    fun ten_word_with_spaces_message_return_true() {
        val text = vm.replaceWords(TEN_WORD_MESSAGE_WITH_MORE_SPACES)

        Assert.assertEquals(TEN_WORD_MESSAGE_WITH_MORE_SPACES_RESULT, text)
    }

    @Test
    fun zero_spaces_message_return_true() {
        val text = vm.replaceWords("")

        Assert.assertEquals("", text)
    }

    @Test
    fun spaces_message_return_true() {
        val text = vm.replaceWords(SPACES_MESSAGE_RESULT)

        Assert.assertEquals(SPACES_MESSAGE, text)
    }

    @Test
    fun spaces1_message_return_true() {
        val text = vm.replaceWords(SPACES_MESSAGE1)

        Assert.assertEquals(SPACES_MESSAGE_RESULT1, text)
    }

    @Test
    fun spaces1_message_return_false() {
        val text = vm.replaceWords("3 2 1")

        Assert.assertNotEquals("1 2 3 4", text)
    }

    @After
    fun after() {

    }

}