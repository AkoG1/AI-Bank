package com.example.aibank.ui.currencyFragment

import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aibank.R
import com.example.aibank.adapters.CurrencyListAdapter
import com.example.aibank.databinding.CurrencyFragmentBinding
import com.example.aibank.models.Currency
import com.example.aibanktbcapitest.adapters.MainCurrenciesAdapter
import com.example.aibankv10.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrencyFragment : BaseFragment<CurrencyFragmentBinding>(CurrencyFragmentBinding::inflate) {

    private lateinit var adapter: MainCurrenciesAdapter

    private val viewModel: CurrencyViewModel by viewModels()

    private var commercialList = mutableListOf<Currency.CommercialRates>()

    private var fromOrTo = false

    companion object {
        fun newInstance(): CurrencyFragment {
            return CurrencyFragment()
        }
    }

    override fun start() {
        viewModel.loadCurrencies()
        viewModel.loadMainCurrencies()
        collect()
        initMainCurrenciesRecycler()
        loadLayoutData()
        convertPreview()
        setListeners()
        getCurrencyFromDialog()
//        checkConnection()
    }

    private fun getCurrencyFromDialog() {
        setFragmentResultListener("currencyName") { requestKey, bundle ->
            if (fromOrTo) {
                binding.fromTV.text = bundle.getString("bundle4")
            } else {
                binding.toTV.text = bundle.getString("bundle4")
            }
        }
    }

    private fun collect() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.Response.collect {
                    commercialList.addAll(it)
                    initRecyclerView()
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerForOthers.layoutManager = LinearLayoutManager(context)
        val adapter = CurrencyListAdapter()
        binding.recyclerForOthers.adapter = adapter
        adapter.setData(commercialList)
    }

    private fun loadLayoutData() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadMainCurrencies()
            initMainCurrenciesRecycler()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun initMainCurrenciesRecycler() {
        binding.recyclerForMainExchanges.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        adapter = MainCurrenciesAdapter()
        binding.recyclerForMainExchanges.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.Responsemain.collect {
                    adapter.setData(it)
                }
            }
        }
    }

    private fun convertPreview() {
        binding.fromET.addTextChangedListener { text ->

            if (text.toString() != "") {
                viewModel.convertCurrencies(
                    binding.fromET.text.toString(),
                    binding.fromTV.text.toString().lowercase(),
                    binding.toTV.text.toString().lowercase()
                )
                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.responseconvert.collect {
                            binding.toET.setText(it.value)
                        }
                    }
                }
            }
        }


        binding.toET.setOnClickListener {
            binding.fromET.setText("")
            binding.toET.setText("")
            binding.toET.addTextChangedListener { text ->
                if (text.toString() != "") {
                    viewModel.convertCurrencies(
                        binding.toET.text.toString(),
                        binding.toTV.text.toString().lowercase(),
                        binding.fromTV.text.toString().lowercase()
                    )
                    viewLifecycleOwner.lifecycleScope.launch {
                        repeatOnLifecycle(Lifecycle.State.STARTED) {
                            viewModel.responseconvert.collect {
                                binding.fromET.setText(it.value)
                            }
                        }
                    }
                }
            }
        }


    }

    private fun setListeners() {
        binding.fromTV.setOnClickListener {
            setFragmentResult(
                "commercialList",
                bundleOf("bundle3" to commercialList as ArrayList<Currency.CommercialRates>)
            )
            fromOrTo = true
            binding.fromET.text?.clear()
            binding.toET.text?.clear()
            findNavController().navigate(R.id.action_currencyFragment_to_convertDialogFragment)
        }

        binding.toTV.setOnClickListener {
            setFragmentResult(
                "commercialList",
                bundleOf("bundle3" to commercialList as ArrayList<Currency.CommercialRates>)
            )
            fromOrTo = false
            binding.fromET.text?.clear()
            binding.toET.text?.clear()
            findNavController().navigate(R.id.action_currencyFragment_to_convertDialogFragment)
        }

        binding.changeCurrencies.setOnClickListener {
            val firstCurrency = binding.fromTV.text.toString()
            binding.fromTV.text = binding.toTV.text
            binding.toTV.text = firstCurrency

        }

    }
}



//    private fun checkConnection() {
//        //check network conn
//        val networkConnection = NetworkConnection(requireContext())
//        networkConnection.observe(this, Observer { isConnected ->
//            if (isConnected) {
//                binding.lostConnection.visibility = View.GONE
//                binding.swipeRefreshLayout.isRefreshing = false
//            }else {
//                binding.lostConnection.visibility = View.VISIBLE
//                binding.swipeRefreshLayout.isRefreshing = true
//            }
//        })
//    }







