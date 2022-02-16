package com.example.aibank.ui.cryptoFragment

import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aibank.adapters.CryptoRecyclerAdapter
import com.example.aibank.databinding.CryptoFragmentBinding
import com.example.aibank.models.CryptoData
import com.example.aibank.ui.BaseFragment
import com.example.aibank.ui.currencyFragment.CurrencyFragment
import com.example.aibank.ui.network.NetworkConnection
import com.example.aibank.ui.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CryptoFragment : BaseFragment<CryptoFragmentBinding>(CryptoFragmentBinding::inflate) {

    private val viewModel: CryptoViewModel by viewModels()

    private lateinit var adapter: CryptoRecyclerAdapter

    override fun start() {
        checkConnection()
        viewModel.loadCryptoData()
        collect()
        swipeRefreshListener()
        initListeners()
    }

    private fun swipeRefreshListener() {
        binding.swipeRefresh.setOnRefreshListener {
            start()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun initListeners() {
        with(binding) {
            search.setOnClickListener {
                searchButtonTodo()
            }

            cancel.setOnClickListener {
                cancelButtonTodo()
            }

            searchBar.addTextChangedListener { id ->
                searchBarLogic(id.toString())
            }
        }
    }

    private fun collect() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.response.collect {
                    when (it) {
                        is Resource.Loading -> {
                            binding.swipeRefresh.isRefreshing = true
                        }
                        is Resource.Success -> {
                            initRecyclerView(it.data!!)
                            binding.swipeRefresh.isRefreshing = false
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            binding.swipeRefresh.isRefreshing = false
                        }
                        else -> {
                            Toast.makeText(requireContext(), UNKNOWN_ERROR, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun searchById(text: String) {
        viewModel.getCryptoById(text)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.searched.collect {
                    when (it) {
                        is Resource.Loading -> binding.swipeRefresh.isRefreshing = true
                        is Resource.Success -> {
                            if (it.data!!.isNotEmpty()) {
                                initRecyclerView(it.data)
                                binding.swipeRefresh.isRefreshing = false
                            } else {
                                binding.swipeRefresh.isRefreshing = false
                            }
                        }
                        is Resource.Error -> {
                            delay(2000)
                            binding.swipeRefresh.isRefreshing = false
                        }
                        else -> {
                            Toast.makeText(requireContext(), UNKNOWN_ERROR, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun initRecyclerView(cryptoList: MutableList<CryptoData.CryptoDataItem>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CryptoRecyclerAdapter()
        binding.recyclerView.adapter = adapter
        adapter.setData(cryptoList)
    }

    private fun cancelButtonTodo() {
        with(binding) {
            searchBar.visibility = View.GONE
            cancel.visibility = View.INVISIBLE
            start()
        }
    }

    private fun searchButtonTodo() {
        with(binding) {
            searchBar.visibility = View.VISIBLE
            cancel.visibility = View.VISIBLE
        }
    }

    private fun searchBarLogic(id: String) {
        if (id != EMPTY_STRING) {
            searchById(id)
        }
    }

    private fun checkConnection() {
        //check network conn
        val networkConnection = NetworkConnection(requireContext())
        networkConnection.observe(this, Observer { isConnected ->
            if (isConnected) {
                binding.lostConnection.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = false
            } else {
                binding.lostConnection.visibility = View.VISIBLE
                binding.swipeRefresh.isRefreshing = true
            }
        })
    }

    companion object {
        private const val EMPTY_STRING = ""
        private const val UNKNOWN_ERROR = "Unknown Error"
        fun newInstance(): CryptoFragment {
            return CryptoFragment()
        }
    }
}