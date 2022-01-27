package com.example.aibank.ui.currencyFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aibank.models.ConvertJson
import com.example.aibank.models.Currency
import com.example.aibank.ui.others.CurrenciesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val repository: CurrenciesRepository) : ViewModel() {

    var currencyList = mutableListOf<Currency.CommercialRates>()
    private var _Response = MutableStateFlow<MutableList<Currency.CommercialRates>>(currencyList)
    var Response : StateFlow<MutableList<Currency.CommercialRates>> = _Response



    fun loadCurrencies() {
        viewModelScope.launch {
            val value = repository.getCurrencies()
            _Response.value = value.commercialRatesList as MutableList<Currency.CommercialRates>
            Log.d("body", "start: ${value} ")
        }
    }

    var mainCurrencyList = mutableListOf<Currency.CommercialRates>()
    private var _Responsemain = MutableStateFlow<MutableList<Currency.CommercialRates>>(mainCurrencyList)
    var Responsemain : StateFlow<MutableList<Currency.CommercialRates>> = _Responsemain



    fun loadMainCurrencies() {
        viewModelScope.launch {
            val value = repository.getMainCurrencies().commercialRatesList as MutableList<Currency.CommercialRates>
            val sortedValue = mutableListOf<Currency.CommercialRates>(value[2],value[0],value[1])
            _Responsemain.value = sortedValue
            Log.d("body", "start: ${sortedValue} ")
        }
    }

    private var _Responseconvert = MutableStateFlow<ConvertJson>(ConvertJson("0","eur","usd","0"))
    var responseconvert : StateFlow<ConvertJson> = _Responseconvert



    fun convertCurrencies(amount: String, from:String, to:String) {
        viewModelScope.launch {
            val value = repository.convertPreview(amount, from, to)
            _Responseconvert.value = value
            Log.d("body1", "start: ${value} ")
        }
    }

}