package com.example.aibank.ui.currencyFragment

import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aibank.R
import com.example.aibank.adapters.CurrencyListAdapter
import com.example.aibank.adapters.MainCurrenciesAdapter
import com.example.aibank.databinding.CurrencyFragmentBinding
import com.example.aibank.models.Currency
import com.example.aibank.ui.BaseFragment
import com.example.aibank.ui.network.NetworkConnection
import com.example.aibank.ui.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrencyFragment : BaseFragment<CurrencyFragmentBinding>(CurrencyFragmentBinding::inflate) {

    private lateinit var adapter: MainCurrenciesAdapter

    private val viewModel: CurrencyViewModel by activityViewModels()

    private var fromOrTo = false

    override fun start() {
        viewModel.loadCurrencies()
        viewModel.loadMainCurrencies()
        collect()
        initMainCurrenciesRecycler()
        loadLayoutData()
        convertPreview()
        setListeners()
        checkConnection()
        collectPassedData()
    }

    private fun collect() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.response.collect {
                    when (it) {
                        is Resource.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                        is Resource.Success -> {
                            initRecyclerView(it.data!!)
                            binding.swipeRefreshLayout.isRefreshing = false
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            binding.swipeRefreshLayout.isRefreshing = false
                        }
                    }
                }
            }
        }
    }

    private fun collectPassedData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.passedDataFromDialog.collect {
                    if (fromOrTo) {
                        binding.fromTV.text = it
                    } else {
                        binding.toTV.text = it
                    }
                }
            }
        }
    }

    private fun initRecyclerView(commercialList: MutableList<Currency.CommercialRates>) {
        binding.recyclerForOthers.layoutManager = LinearLayoutManager(context)
        val adapter = CurrencyListAdapter()
        binding.recyclerForOthers.adapter = adapter
        adapter.setData(commercialList)
    }

    private fun loadLayoutData() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            start()
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
                viewModel.responsemain.collect {
                    when (it) {
                        is Resource.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                        is Resource.Success -> {
                            adapter.setData(it.data!!)
                            binding.swipeRefreshLayout.isRefreshing = false
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            binding.swipeRefreshLayout.isRefreshing = false
                        }
                    }
                }
            }
        }
    }

    private fun convertPreview() {
        binding.fromET.addTextChangedListener { text ->

            if (text.toString() != EMPTY) {
                viewModel.convertCurrencies(
                    binding.fromET.text.toString(),
                    binding.fromTV.text.toString().lowercase(),
                    binding.toTV.text.toString().lowercase()
                )
                viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        viewModel.responseconvert.collect {
                            binding.toET.text = it.value
                        }
                    }
                }
            } else if (text.toString() == EMPTY) {
                binding.toET.text = EMPTY
            }
        }
    }

    private fun setListeners() {
        binding.fromTV.setOnClickListener {
            fromOrTo = true
            binding.fromET.text?.clear()
            binding.toET.text = EMPTY
            openBottomSheetDialogFragment()
        }

        binding.toTV.setOnClickListener {
            fromOrTo = false
            binding.fromET.text?.clear()
            binding.toET.text = EMPTY
            openBottomSheetDialogFragment()
        }

    }

    private fun openBottomSheetDialogFragment() {
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController.navigate(R.id.action_homeFragment_to_convertDialogFragment2)
    }

    private fun checkConnection() {
        //check network conn
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(this, Observer { isConnected ->
            if (isConnected) {
                binding.lostConnection.visibility = View.GONE
                binding.swipeRefreshLayout.isRefreshing = false
            } else {
                binding.lostConnection.visibility = View.VISIBLE
                binding.swipeRefreshLayout.isRefreshing = true
            }
        })
    }

    companion object {
        fun newInstance(): CurrencyFragment {
            return CurrencyFragment()
        }

        private const val EMPTY = ""
    }

}











