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

    private lateinit var adapter: CurrencyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentConvertDialogListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentListener()
    }

    private fun setFragmentListener() {
        setFragmentResultListener("commercialList") { requestKey, bundle ->
            if (bundle.getParcelableArrayList<Currency.CommercialRates>("bundle3") != null) {
                initRecyclerView(bundle.getParcelableArrayList<Currency.CommercialRates>("bundle3") as ArrayList<Currency.CommercialRates>)
            }
        }
    }

    private fun initRecyclerView(list: ArrayList<Currency.CommercialRates>) {
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CurrencyAdapterForDialogFragment(::onCurrencyClick)
        binding.recycler.adapter = adapter
        adapter.setData(list as MutableList<Currency.CommercialRates>)
    }

    private fun onCurrencyClick(currency: String) {
        setFragmentResult("currencyName", bundleOf("bundle4" to currency))
        dialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}