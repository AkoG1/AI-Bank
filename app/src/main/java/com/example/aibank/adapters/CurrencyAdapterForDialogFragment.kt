package com.example.aibank.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aibank.databinding.ConvertDialogFragmentItems1Binding
import com.example.aibank.databinding.ConvertDialogFramentItems2Binding
import com.example.aibank.models.Currency

class CurrencyAdapterForDialogFragment(private val onCurrencyClick: ((currencyName:String)-> Unit)? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    companion object {
//        private const val FIRST_ITEM = 1
//        private const val SECOND_ITEM = 2
//    }

    val currencyList = mutableListOf<Currency.CommercialRates>()

    inner class ViewHolderA(private val binding: ConvertDialogFragmentItems1Binding) : RecyclerView.ViewHolder(binding.root) {
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

    inner class ViewHolderB(private val binding: ConvertDialogFramentItems2Binding) : RecyclerView.ViewHolder(binding.root) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder{
//        return if (viewType == FIRST_ITEM) {
            return ViewHolderA(ConvertDialogFragmentItems1Binding.inflate(LayoutInflater.from(parent.context), parent, false))
//        } else
//            ViewHolderB(ConvertDialogFramentItems2Binding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderA -> holder.onBind(position)
            is ViewHolderB -> holder.onBind(position)
        }
    }

    override fun getItemCount() = currencyList.size

//    override fun getItemViewType(position: Int) {
//        if ()
//    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(currencyList: MutableList<Currency.CommercialRates>) {
        this.currencyList.clear()
        this.currencyList.addAll(currencyList)
        notifyDataSetChanged()
    }

}