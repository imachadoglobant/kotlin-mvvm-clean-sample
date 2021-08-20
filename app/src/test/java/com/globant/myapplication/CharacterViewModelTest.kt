package com.globant.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.globant.domain.entities.MarvelCharacter
import com.globant.domain.usecases.GetCharacterByIdUseCase
import com.globant.domain.utils.Result
import com.globant.utils.Status.ERROR
import com.globant.utils.Status.LOADING
import com.globant.utils.Status.SUCCESSFUL
import com.globant.viewmodels.CharacterViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class CharacterViewModelTest {

    companion object {
        private const val VALID_ID = 1017100
        private const val INVALID_ID = -1
    }

    class TestObserver<T> : Observer<T> {
        val observedValues = mutableListOf<T?>()
        override fun onChanged(value: T?) {
            observedValues.add(value)
        }
    }

    private fun <T> LiveData<T>.testObserver() = TestObserver<T>().also {
        observeForever(it)
    }

    @ObsoleteCoroutinesApi
    private var mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var subject: CharacterViewModel
    @Mock lateinit var marvelCharacterValidResult: Result.Success<MarvelCharacter>
    @Mock lateinit var marvelCharacterInvalidResult: Result.Failure
    @Mock lateinit var marvelCharacter: MarvelCharacter
    @Mock lateinit var exception: Exception
    @Mock lateinit var getCharacterByIdUseCase: GetCharacterByIdUseCase

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockitoAnnotations.openMocks(this)
        subject = CharacterViewModel(getCharacterByIdUseCase)
    }

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @After
    fun after() {
        mainThreadSurrogate.close()
        Dispatchers.resetMain()
    }

    @Test
    fun onSearchRemoteTestSuccessful() {
        val liveDataUnderTest = subject.mainState.testObserver()
        whenever(getCharacterByIdUseCase.invoke(VALID_ID, true)).thenReturn(marvelCharacterValidResult)
        whenever(marvelCharacterValidResult.data).thenReturn(marvelCharacter)
        runBlocking {
            subject.onSearchRemoteClicked(VALID_ID).join()
        }
        liveDataUnderTest.observedValues.run {
            assertEquals(LOADING, first()?.peekContent()?.responseType)
            assertNotNull(last()?.peekContent())
            last()?.peekContent()?.run {
                assertEquals(SUCCESSFUL, responseType)
                assertEquals(marvelCharacter, data)
            }
        }
    }

    @Test
    fun onSearchRemoteTestError() {
        val liveDataUnderTest = subject.mainState.testObserver()
        whenever(getCharacterByIdUseCase.invoke(INVALID_ID, true)).thenReturn(marvelCharacterInvalidResult)
        whenever(marvelCharacterInvalidResult.exception).thenReturn(exception)

        runBlocking {
            subject.onSearchRemoteClicked(INVALID_ID).join()
        }

        liveDataUnderTest.observedValues.run {
            assertEquals(LOADING, first()?.peekContent()?.responseType)
            assertNotNull(last()?.peekContent())
            last()?.peekContent()?.run {
                assertEquals(ERROR, responseType)
                assertEquals(exception, error)
            }
        }
    }

    @Test
    fun onSearchLocalSuccessful() {
        val liveDataUnderTest = subject.mainState.testObserver()
        whenever(getCharacterByIdUseCase.invoke(VALID_ID, false)).thenReturn(marvelCharacterValidResult)
        whenever(marvelCharacterValidResult.data).thenReturn(marvelCharacter)

        runBlocking {
            subject.onSearchLocalClicked(VALID_ID).join()
        }

        liveDataUnderTest.observedValues.run {
            assertEquals(LOADING, first()?.peekContent()?.responseType)
            assertNotNull(last()?.peekContent())
            last()?.peekContent()?.run {
                assertEquals(SUCCESSFUL, responseType)
                assertEquals(marvelCharacter, data)
            }
        }
    }

    @Test
    fun onSearchLocalTestError() {
        val liveDataUnderTest = subject.mainState.testObserver()
        whenever(getCharacterByIdUseCase.invoke(INVALID_ID, true)).thenReturn(marvelCharacterInvalidResult)
        whenever(marvelCharacterInvalidResult.exception).thenReturn(exception)

        runBlocking {
            subject.onSearchRemoteClicked(INVALID_ID).join()
        }

        liveDataUnderTest.observedValues.run {
            assertEquals(LOADING, first()?.peekContent()?.responseType)
            assertNotNull(last()?.peekContent())
            last()?.peekContent()?.run {
                assertEquals(ERROR, responseType)
                assertEquals(exception, error)
            }
        }
    }

}
