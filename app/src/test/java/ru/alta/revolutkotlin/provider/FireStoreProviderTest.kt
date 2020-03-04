package ru.alta.revolutkotlin.data.provider

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.selects.SelectClause1
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.junit.*
import org.junit.Assert.*
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.data.provider.FireStoreProvider
import ru.alta.revolutkotlin.data.errors.NoAuthException
import ru.alta.revolutkotlin.model.CurrenciesResult
import ru.alta.revolutkotlin.ui.main.LogoutDialog.Companion.TAG
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FireStoreProviderTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockDb = mockk<FirebaseFirestore>()
    private val mockAuth = mockk<FirebaseAuth>()
    private val mockResultCollection = mockk<CollectionReference>()
    private val mockUser = mockk<FirebaseUser>()

    private val mockDocument1 = mockk<DocumentSnapshot>()
    private val mockDocument2 = mockk<DocumentSnapshot>()
    private val mockDocument3 = mockk<DocumentSnapshot>()

    private val testCurrencies = listOf(Currency("1"), Currency("2"), Currency("3"))

    private val provider = FireStoreProvider(mockAuth, mockDb)

    companion object {
        @BeforeClass
        fun setupClass() {

        }

        @AfterClass
        fun tearDownClass() {

        }
    }

    @Before
    fun setup() {
        clearAllMocks()
        every { mockAuth.currentUser } returns mockUser
        every { mockUser.uid } returns ""
        every { mockDb.collection(any()).document(any()).collection(any()) } returns mockResultCollection

        every { mockDocument1.toObject(Currency::class.java) } returns testCurrencies[0]
        every { mockDocument2.toObject(Currency::class.java) } returns testCurrencies[1]
        every { mockDocument3.toObject(Currency::class.java) } returns testCurrencies[2]
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `should throw NoAuthException if no auth`() {
//        var result: Any? = null
//        every { mockAuth.currentUser } returns null
//        provider.subscribeToAllCurrencies().observeForever {
//            result = (it as? CurrenciesResult.Error)?.error
//        }
//        assertTrue(result is NoAuthException)
    }

    @Test
    suspend fun `saveCurrency calls set`() {
//        val mockDocumentReference = mockk<DocumentReference>()
//        every { mockResultCollection.document(testCurrencies[0].title) } returns mockDocumentReference
//        provider.saveCurrency(testCurrencies[0])
//        verify(exactly = 1) { mockDocumentReference.set(testCurrencies[0]) }
    }

    @Test
    suspend fun `subscribe to all notes returns notes`() {

        var result: ReceiveChannel<CurrenciesResult>? = null
        val mockSnapshot = mockk<QuerySnapshot>()
        val slot = slot<EventListener<QuerySnapshot>>()

        var registration: ListenerRegistration? = null

        every { mockSnapshot.documents } returns listOf(mockDocument1, mockDocument2, mockDocument3)
        every { mockResultCollection.addSnapshotListener(capture(slot)) } returns mockk()

        slot.captured.onEvent(mockSnapshot, null)
        assertEquals(testCurrencies, result)
    }

    @Test
    suspend fun `deleteNote calls document delete`() {
//        val mockDocumentReference = mockk<DocumentReference>()
//        every { mockResultCollection.document(testCurrencies[0].title) } returns mockDocumentReference
//        provider.deleteCurrency(testCurrencies[0].title)
//        verify(exactly = 1) { mockDocumentReference.delete() }
    }

    @Test
    suspend fun `get currency by name returns currency`(){
        var result: Currency? = null
        var mockContinuation: Continuation<DocumentSnapshot>? = null
        val slot = slot<OnSuccessListener<DocumentSnapshot>>()

        val slot2 = slot<Continuation<DocumentSnapshot>>()


        every {mockContinuation?.resume(mockDocument1) }  returns mockk()

        every {  mockResultCollection.document(testCurrencies[0].title).get().addOnSuccessListener(capture(slot))  } returns mockk()

        provider.getCurrencyByName(testCurrencies[0].title).doAsync {
            mockContinuation?.resume(mockDocument1)
        }
        slot.captured.onSuccess(mockDocument1)
        assertEquals(testCurrencies[0], result)

    }

//    var result: Currency? = null
//    val slot = slot<OnSuccessListener<DocumentSnapshot>>()
//    val mockDocumentReference = mockk<DocumentReference>()
//
//    every { mockResultCollection.document(testCurrencies[0].title) } returns mockDocumentReference
//
//    every {  mockResultCollection.document(testCurrencies[0].title).get().addOnSuccessListener(capture(slot))  } returns mockk()
//
//    provider.getCurrencyByName(testCurrencies[0].title). observeForever{
//        result = (it as?  CurrenciesResult.Success<Currency>)?.data
//    }
//    slot.captured.onSuccess(mockDocument1)
//    Assert.assertEquals(testCurrencies[0], result)
}
