package com.example.aibank.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aibank.databinding.CurrencyItemsBinding
import com.example.aibank.models.Currency


class CurrencyListAdapter(private val onCurrencyClick: ((currencyName:String)-> Unit)? = null) : RecyclerView.Adapter<CurrencyListAdapter.ViewHolder>(
) {

    private val currencyList = mutableListOf<Currency.CommercialRates>()

    inner class ViewHolder(private val binding: CurrencyItemsBinding): RecyclerView.ViewHolder(binding.root){

        fun onBind(adapterPosition : Int) {
            val model = currencyList[adapterPosition]
            binding.apply {
                name.text = model.currency.toString()
                buy.text = model.buy.toString()
                sell.text = model.sell.toString()
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(
        CurrencyItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount() = currencyList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(currencyList: MutableList<Currency.CommercialRates>) {
        this.currencyList.clear()
        this.currencyList.addAll(currencyList)
        notifyDataSetChanged()
    }



}