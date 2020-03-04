package ru.alta.revolutkotlin.ui.currency

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.test.rule.ActivityTestRule
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.model.CurrencyViewModel


class CurrencyActivityTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(CurrencyActivity::class.java, true, false)


    private val model: CurrencyViewModel = spyk(CurrencyViewModel(mockk(relaxed = true)))
    private val viewStateLiveData = MutableLiveData<CurrencyData>()
    val testCurrency: Currency = Currency("1", "123")
    var testColor = Currency.Color.WHITE


    @Before
    fun setUp() {
        StandAloneContext.loadKoinModules(
            listOf(
                module {
                    viewModel(override = true) { model }
                }
            )
        )

        every { model.getViewState() } returns viewStateLiveData
        every { model.loadCurrency(any()) } just runs
        every { model.save(any()) } just runs
        every { model.deleteCurrency() } just runs

        activityTestRule.launchActivity(null)
        viewStateLiveData.postValue(CurrencyData(CurrencyData.Data(false), null))

        Intent().apply {
            putExtra(CurrencyActivity::class.java.name + "extra.Currency_title", testCurrency.title)
        }.let {
            activityTestRule.launchActivity(it)
        }

    }

    @After
    fun tearDown(){
        StandAloneContext.stopKoin()
    }

    @Test
    fun setColor() {
//        onView(withId(R.id.palette)).perform(click())
//    //    onView(withTagValue(`is` (testColor))).perform(click())
//        val colorInt = Currency.Color.BLUE.getColorInt(activityTestRule.activity)
//
//        onView(withId(R.id.toolbar)).check { view, _ ->
//            assertTrue("toolbar background color does not match",
//                (view.background as? ColorDrawable)?.color == colorInt)
//        }
    }

}