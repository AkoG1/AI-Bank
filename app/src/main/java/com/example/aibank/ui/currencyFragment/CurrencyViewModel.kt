package com.example.aibank.ui.currencyFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aibank.models.ConvertModel
import com.example.aibank.models.Currency
import com.example.aibank.repository.Repository
import com.example.aibank.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var _response =
        MutableStateFlow<Resource<MutableList<Currency.CommercialRates>>>(Resource.Idle)
    var response: StateFlow<Resource<MutableList<Currency.CommercialRates>>> = _response

    fun loadCurrencies() {
        viewModelScope.launch {
            _response.emit(Resource.Loading)
            val value = repository.getCurrencies()
            _response.emit(value)
        }
    }

    private var _responsemain =
        MutableStateFlow<Resource<MutableList<Currency.CommercialRates>>>(Resource.Idle)
    var responsemain: StateFlow<Resource<MutableList<Currency.CommercialRates>>> = _responsemain


    fun loadMainCurrencies() {
        viewModelScope.launch {
            _responsemain.emit(Resource.Loading)
            val value = repository.getMainCurrencies()
            _responsemain.emit(value)
        }
    }

    private var _responseconvert =
        MutableStateFlow<ConvertModel>(ConvertModel(ZERO, USD, GEL, ZERO))
    var responseconvert: StateFlow<ConvertModel> = _responseconvert


    fun convertCurrencies(amount: String, from: String, to: String) {
        viewModelScope.launch {
            val value = repository.convertPreview(amount, from, to)
            _responseconvert.emit(value)
        }
    }

    val passedDataFromDialog = MutableStateFlow(USD_UPPER)

    companion object {
        private const val ZERO = "0"
        private const val USD = "usd"
        private const val GEL = "gel"
        private const val USD_UPPER = "USD"
    }
}