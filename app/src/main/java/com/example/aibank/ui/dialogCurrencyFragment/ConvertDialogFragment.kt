package com.example.aibank.ui.dialogCurrencyFragment

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.aibank.adapters.CurrencyAdapterForDialogFragment
import com.example.aibank.adapters.CurrencyListAdapter
import com.example.aibank.databinding.FragmentConvertDialogListDialogBinding
import com.example.aibank.models.Currency


class ConvertDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentConvertDialogListDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: CurrencyAdapterForDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentConvertDialogListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setFragmentListener()
    }

    private fun setFragmentListener() {
        setFragmentResultListener(COMMERCIAL_LIST) { _, bundle ->
            if (bundle.getParcelableArrayList<Currency.CommercialRates>(BUNDLE_3) != null) {
                initRecyclerView(bundle.getParcelableArrayList<Currency.CommercialRates>(BUNDLE_4) as ArrayList<Currency.CommercialRates>)
            }
        }
    }

    private fun initRecyclerView(list: ArrayList<Currency.CommercialRates>) {
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = CurrencyAdapterForDialogFragment(::onCurrencyClick)
        binding.recycler.adapter = adapter
        adapter.setData(list as MutableList<Currency.CommercialRates>)
    }

    private fun onCurrencyClick(currency: String) {
        setFragmentResult(CURRENCY_NAME, bundleOf(BUNDLE_4 to currency))
        dialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val COMMERCIAL_LIST = "commercialList"
        private const val BUNDLE_3 = "bundle3"
        private const val BUNDLE_4 = "bundle4"
        private const val CURRENCY_NAME = "currencyName"
    }
}