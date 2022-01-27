package com.example.aibanktbcapitest.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aibank.R
import com.example.aibank.databinding.MainCurrencyItemsBinding
import com.example.aibank.models.Currency

class MainCurrenciesAdapter: RecyclerView.Adapter<MainCurrenciesAdapter.ViewHolder>() {

    private val mainCurrencies = mutableListOf<Currency.CommercialRates>()

    inner class ViewHolder(private val binding: MainCurrencyItemsBinding): RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("SetTextI18n")
            fun onBind(adapterPosition : Int) {
                val model = mainCurrencies[adapterPosition]
                binding.apply {
                    shortName.setText(model.currency)
                    buyCurrency.setText(model.buy.toString())
                    sellCurrency.setText(model.sell.toString())

                    when (model.currency.toString()) {
                        "USD" -> {fullName.setText("US Dollar")
                            currencyLogo.setImageResource(R.drawable.ic_usdvector)}
                        "EUR" -> {fullName.setText("EURO")
                            currencyLogo.setImageResource(R.drawable.ic_eurosvg)}
                        "GBP" -> {fullName.setText("British Pound")
                            currencyLogo.setImageResource(R.drawable.ic_poundvector)}
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)  = ViewHolder(
        MainCurrencyItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount() = mainCurrencies.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(mainCurrencies: MutableList<Currency.CommercialRates>) {
        this.mainCurrencies.clear()
        this.mainCurrencies.addAll(mainCurrencies)
        notifyDataSetChanged()
    }

}