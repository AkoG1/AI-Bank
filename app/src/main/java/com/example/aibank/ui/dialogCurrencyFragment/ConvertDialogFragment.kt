package com.example.aibank.ui.dialogCurrencyFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aibank.adapters.CurrencyAdapterForDialogFragment
import com.example.aibank.databinding.FragmentConvertDialogListDialogBinding
import com.example.aibank.models.Currency
import com.example.aibank.ui.currencyFragment.CurrencyViewModel
import com.example.aibank.ui.utils.Resource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConvertDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentConvertDialogListDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CurrencyAdapterForDialogFragment

    private val viewModel: CurrencyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConvertDialogListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadCurrencies()
        collect()
    }

    private fun collect() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.response.collect {
                    when (it) {
                        is Resource.Loading -> binding.swipeRefresh.isRefreshing = true
                        is Resource.Success -> {
                            initRecyclerView(it.data!!)
                            binding.swipeRefresh.isRefreshing = false
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            binding.swipeRefresh.isRefreshing = false
                            dialog?.dismiss()
                        }
                    }
                }
            }
        }
    }

    private fun initRecyclerView(currencyNameList: MutableList<Currency.CommercialRates>) {
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = CurrencyAdapterForDialogFragment(::onCurrencyClick)
        binding.recycler.adapter = adapter
        adapter.setData(currencyNameList)
    }

    private fun onCurrencyClick(currencyName: String) {
        viewModel.passedDataFromDialog.value = currencyName
        dialog?.dismiss()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}