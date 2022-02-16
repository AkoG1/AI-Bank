package com.example.aibank.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aibank.databinding.ConvertDialogFragmentItemBinding
import com.example.aibank.models.Currency

class CurrencyAdapterForDialogFragment(private val onCurrencyClick: ((currencyName: String) -> Unit)? = null) :
    RecyclerView.Adapter<CurrencyAdapterForDialogFragment.ViewHolder>() {

    val currencyList = mutableListOf<Currency.CommercialRates>()

    inner class ViewHolder(private val binding: ConvertDialogFragmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(adapterPosition: Int) {
            val model = currencyList[adapterPosition]
            binding.apply {
                TV.text = model.currency.toString()
                itemView.setOnClickListener {
                    onCurrencyClick?.invoke(model.currency!!)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ConvertDialogFragmentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )


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