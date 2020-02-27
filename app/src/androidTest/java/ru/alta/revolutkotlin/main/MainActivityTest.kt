package ru.alta.revolutkotlin.main

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.stopKoin
import ru.alta.revolutkotlin.R
import ru.alta.revolutkotlin.adapter.CurrenciesRvAdapter
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.ui.main.MainActivity
import ru.alta.revolutkotlin.ui.main.MainViewModel
import ru.alta.revolutkotlin.ui.main.MainViewState

class MainActivityTest {

    @get:Rule
    val activityTestRule = IntentsTestRule(MainActivity::class.java, true, false)

    private val model: MainViewModel = mockk(relaxed = true)
    private val viewStateLiveData = MutableLiveData<MainViewState>()

    private val testCurrencies = listOf(
        Currency("1", "title1"),
        Currency("2", "title2"),
        Currency("3", "title3")
    )

    @Before
    fun setUp() {
        loadKoinModules(
            listOf(
                module {
                    viewModel(override = true) { model }
                }
            )
        )

        every { model.getViewState() } returns viewStateLiveData
        activityTestRule.launchActivity(null)
        viewStateLiveData.postValue(MainViewState(currencies = testCurrencies))
    }

    @After
    fun tearDown(){
        stopKoin()
    }

    @Test
    fun check_data_is_displayed(){
        onView(withId(R.id.rv_currency)).perform(scrollToPosition<CurrenciesRvAdapter.ViewHolder>(1))
        onView(withText(testCurrencies[1].text)).check(matches(isDisplayed()))
    }
}