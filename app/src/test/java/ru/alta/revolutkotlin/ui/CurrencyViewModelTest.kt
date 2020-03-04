package ru.geekbrains.gb_kotlin.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentSnapshot
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
import ru.alta.revolutkotlin.model.CurrencyViewModel
import ru.alta.revolutkotlin.ui.currency.CurrencyData
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

class CurrencyViewModelTest {

//    @get:Rule
//    val taskExecutorRule = InstantTaskExecutorRule()
//
//    private val mockRepository = mockk<CurrenciesRepository>()
//    private val noteLiveData = MutableLiveData<CurrenciesResult>()
//
//    lateinit var mockContinuation: Continuation<DocumentSnapshot>
//
//    private val testCurrency = Currency("1", "title")
//
//    private lateinit var viewModel: CurrencyViewModel
//
//
//    @Before
//    fun setup() {
//        clearMocks(mockRepository)
////        every { mockRepository.getCurrencyByName(testCurrency.title) } returns
////        every { mockRepository.deleteCurrency(testCurrency.title) } returns noteLiveData
////        every { mockRepository.saveCurrency(testCurrency) } returns  noteLiveData
//        viewModel = CurrencyViewModel(mockRepository)
//    }
//
//
//    @Test
//    fun `loadNote should return NoteViewState Data`() {
//        var result: CurrencyData.Data? = null
//        val testData = CurrencyData.Data(false, testCurrency)
//        viewModel.getViewState().observeForever {
//            result = it.data
//        }
//        viewModel.loadCurrency(testCurrency.title)
//        noteLiveData.value = CurrenciesResult.Success(testCurrency)
//        assertEquals(testData, result)
//    }
//
//    @Test
//    fun `loadNote should return error`() {
//        var result: Throwable? = null
//        val testData = Throwable("error")
//        viewModel.getViewState().observeForever {
//            result = it.error
//        }
//        viewModel.loadCurrency(testCurrency.title)
//        noteLiveData.value = CurrenciesResult.Error(error = testData)
//        assertEquals(testData, result)
//    }
//
//    @Test
//    fun `deleteNote should return NoteViewState Data wish isDeleted`() {
//        var result: CurrencyData.Data? = null
//        val testData = CurrencyData.Data(true, null)
//        viewModel.getViewState().observeForever {
//            result = it.data
//        }
//        viewModel.save(testCurrency)
//        viewModel.deleteCurrency()
//        noteLiveData.value = CurrenciesResult.Success(null)
//        assertEquals(testData, result)
//    }
//
//    @Test
//    fun `deleteNote should return error`() {
//        var result: Throwable? = null
//        val testData = Throwable("error")
//        viewModel.getViewState().observeForever {
//            result = it.error
//        }
//        viewModel.save(testCurrency)
//        viewModel.deleteCurrency()
//        noteLiveData.value = CurrenciesResult.Error(error = testData)
//        assertEquals(testData, result)
//    }
//
//    @Test
//    fun `should save changes`() {
//        viewModel.save(testCurrency)
//        viewModel.onCleared()
//        verify { mockRepository.saveCurrency(testCurrency) }
//    }
}