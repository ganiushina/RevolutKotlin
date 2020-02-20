package ru.alta.revolutkotlin.data.provider

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.data.entity.User
import ru.alta.revolutkotlin.data.errors.NoAuthException
import ru.alta.revolutkotlin.model.CurrenciesResult


class FireStoreProvider : RemoteDataProvider {

    companion object {
        private const val CURRENCIES_COLLECTION = "currencies"
        private const val USER_COLLECTION = "users"
    }

    private val store by lazy { FirebaseFirestore.getInstance() }

    private val currentUser
        get() = FirebaseAuth.getInstance().currentUser

       private val userCurrencyCollection: CollectionReference
        get() = currentUser?.let {
            store.collection(USER_COLLECTION).document(it.uid).collection(CURRENCIES_COLLECTION)
        } ?: throw NoAuthException()



    override fun subscribeToAllCurrencies() = MutableLiveData<CurrenciesResult>().apply {
        try {
            userCurrencyCollection.addSnapshotListener { snapshot, e ->
                e?.let {
                    throw it
                } ?: let {
                    snapshot?.let { snapshot ->
                        value =
                            CurrenciesResult.Success(snapshot.map { it.toObject(Currency::class.java) })
                    }
                }
            }
        } catch (e: Throwable) {
            value = CurrenciesResult.Error(e)
        }
    }

    override fun getCurrencyByName(name: String) = MutableLiveData<CurrenciesResult>().apply {
        try {
            userCurrencyCollection.document(name).get()
                .addOnSuccessListener { snapshot ->
                    value = CurrenciesResult.Success(snapshot.toObject(Currency::class.java))
                }.addOnFailureListener {
                    value = CurrenciesResult.Error(it)
                }
        } catch (e: Throwable) {
            value = CurrenciesResult.Error(e)
        }
    }


    override fun saveCurrency(currency: Currency)= MutableLiveData<CurrenciesResult>().apply {
        try {
            userCurrencyCollection.document(currency.title).set(currency)
                .addOnSuccessListener {
                    value = CurrenciesResult.Success(currency)
                }.addOnFailureListener {
                    value = CurrenciesResult.Error(it)
                }
        } catch (e: Throwable) {
            value = CurrenciesResult.Error(e)
        }
    }

    override fun getCurrentUser() = MutableLiveData<User?>().apply {
        value = currentUser?.let { firebaseUser ->
            User(firebaseUser.displayName ?: "", firebaseUser.email ?: "")
        }
    }
}