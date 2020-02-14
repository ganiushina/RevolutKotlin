package ru.alta.revolutkotlin.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.model.CurrenciesResult

class FireStoreProvider : RemoteDataProvider {

    companion object {
        private const val CURRENCIES_COLLECTION = "currencies"
    }

    private val store: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val currencyReference = store.collection(CURRENCIES_COLLECTION)


    override fun subscribeToAllCurrencies(): LiveData<CurrenciesResult> {
        val result = MutableLiveData<CurrenciesResult>()
        currencyReference.addSnapshotListener { snapshot, e ->
            e?.let {
                result.value = CurrenciesResult.Error(e)
            } ?: let {
                snapshot?.let { snapshot ->
                    val currency = mutableListOf<Currency>()
                    for (doc: QueryDocumentSnapshot in snapshot) {
                        currency.add(doc.toObject(Currency::class.java))
                    }
                    result.value = CurrenciesResult.Success(currency)
                }
            }
        }
        return result
    }

    override fun getCurrencyByName(name: String): LiveData<CurrenciesResult> {
        val result = MutableLiveData<CurrenciesResult>()
        currencyReference.document(name).get()
            .addOnSuccessListener { snapshot ->
                result.value = CurrenciesResult.Success(snapshot.toObject(Currency::class.java))
            }.addOnFailureListener {
                result.value = CurrenciesResult.Error(it)
            }

        return result
    }

    override fun saveCurrency(currency: Currency): LiveData<CurrenciesResult> {
        val result = MutableLiveData<CurrenciesResult>()
        currencyReference.document(currency.title).set(currency)
            .addOnSuccessListener {
                result.value = CurrenciesResult.Success(currency)
            }.addOnFailureListener {
                result.value = CurrenciesResult.Error(it)
            }

        return result
    }
}