package ru.alta.revolutkotlin.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.data.entity.User
import ru.alta.revolutkotlin.data.errors.NoAuthException
import ru.alta.revolutkotlin.model.CurrenciesResult
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class FireStoreProvider (private val firebaseAuth: FirebaseAuth, private val store: FirebaseFirestore) : RemoteDataProvider {

    companion object {
        private const val CURRENCIES_COLLECTION = "currencies"
        private const val USER_COLLECTION = "users"
    }

    private val currentUser
        get() = firebaseAuth.currentUser

       private val userCurrencyCollection: CollectionReference
        get() = currentUser?.let {
            store.collection(USER_COLLECTION).document(it.uid).collection(CURRENCIES_COLLECTION)
        } ?: throw NoAuthException()



    override fun subscribeToAllCurrencies(): ReceiveChannel<CurrenciesResult> = Channel<CurrenciesResult>(Channel.CONFLATED).apply {
        var registration: ListenerRegistration? = null
        try {
            registration = userCurrencyCollection.addSnapshotListener { snapshot, e ->
                val value = e?.let {
                    CurrenciesResult.Error(it)
                } ?: snapshot?.let { snapshot ->
                        val currencies = snapshot.documents.map { it.toObject(Currency::class.java) }
                        CurrenciesResult.Success(currencies)
                    }
                value?.let { offer(it) }
            }
        } catch (e: Throwable) {
            offer(CurrenciesResult.Error(e))
        }
        invokeOnClose {
            registration?.remove()
        }
    }

    override suspend fun getCurrencyByName(name: String): Currency? = suspendCoroutine { continuation ->
        try {
            userCurrencyCollection.document(name).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(snapshot.toObject(Currency::class.java))
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }


    override suspend fun saveCurrency(currency: Currency) : Currency = suspendCoroutine { continuation ->
        try {
            userCurrencyCollection.document(currency.title).set(currency)
                .addOnSuccessListener {
                    continuation.resume(currency)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }

    override suspend fun getCurrentUser(): User? = suspendCoroutine { continuation ->
        val user = currentUser?.let { User(it.displayName ?: "", it.email ?: "") }
        continuation.resume(user)
    }

    override suspend fun deleteCurrency(name: String): Unit = suspendCoroutine { continuation ->
        try {
            userCurrencyCollection.document(name).delete()
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        } catch (e: Throwable){
            continuation.resumeWithException(e)
        }
    }
}