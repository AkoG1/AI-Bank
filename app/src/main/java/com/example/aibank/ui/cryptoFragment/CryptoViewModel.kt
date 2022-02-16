package com.example.aibank.ui.cryptoFragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aibank.models.CryptoData
import com.example.aibank.repository.Repository
import com.example.aibank.ui.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var _response =
        MutableStateFlow<Resource<MutableList<CryptoData.CryptoDataItem>>>(Resource.Idle)
    val response: StateFlow<Resource<MutableList<CryptoData.CryptoDataItem>>> = _response


    fun loadCryptoData() {
        viewModelScope.launch {
            _response.emit(Resource.Loading)
            val value = repository.getCryptos()
            Log.d("12345", "getCryptoBId: ${value.data}")
            _response.emit(value)
        }
    }

    private var _searched =
        MutableStateFlow<Resource<MutableList<CryptoData.CryptoDataItem>>>(Resource.Idle)
    val searched: StateFlow<Resource<MutableList<CryptoData.CryptoDataItem>>> get() = _searched
    fun getCryptoById(id: String) {
        viewModelScope.launch {
            _searched.emit(Resource.Loading)
            val value = repository.getCryptoById(id)
            Log.d("1234", "getCryptoById: ${value.data}")
            _searched.emit(value)
        }
    }

}