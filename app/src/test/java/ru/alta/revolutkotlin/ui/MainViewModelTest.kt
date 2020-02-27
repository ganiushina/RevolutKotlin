package ru.alta.revolutkotlin.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.alta.revolutkotlin.data.CurrenciesRepository
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.model.CurrenciesResult
import ru.alta.revolutkotlin.ui.main.MainViewModel


class MainViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository = mockk<CurrenciesRepository>()
    private val notesLiveData = MutableLiveData<CurrenciesResult>()

    private lateinit var viewModel: MainViewModel


    @Before
    fun setup() {
        clearMocks(mockRepository)
        every { mockRepository.getCurrencies() } returns notesLiveData
        viewModel = MainViewModel(mockRepository)
    }


    @Test
    fun `should call getNotes`() {
        verify(exactly = 1) { mockRepository.getCurrencies() }
    }

    @Test
    fun `should return notes`() {
        var result: List<Currency>? = null
        val testData = listOf(Currency("1"), Currency("2"))
        viewModel.getViewState().observeForever {
            result = it.data
        }
        notesLiveData.value = CurrenciesResult.Success(testData)
        assertEquals(testData, result)
    }

    @Test
    fun `should return error`() {
        var result: Throwable? = null
        val testData = Throwable("error")
        viewModel.getViewState().observeForever {
            result = it?.error
        }
        notesLiveData.value = CurrenciesResult.Error(error = testData)
        assertEquals(testData, result)
    }

    @Test
    fun `should remove observer`() {
        viewModel.onCleared()
        assertFalse(notesLiveData.hasObservers())
    }
}