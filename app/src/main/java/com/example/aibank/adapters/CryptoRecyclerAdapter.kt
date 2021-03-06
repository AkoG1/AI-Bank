package com.example.aibank.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aibank.databinding.CryptoItemLayoutBinding
import com.example.aibank.extensions.setImage
import com.example.aibank.models.CryptoDataItem

class CryptoRecyclerAdapter(private val onCryptoItemClicked: ((model: CryptoDataItem) -> Unit)? = null) : RecyclerView.Adapter<CryptoRecyclerAdapter.ViewHolder>() {

    private val cryptoList = mutableListOf<CryptoDataItem>()

    inner class ViewHolder(private val binding: CryptoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(adapterPosition: Int) {
            with(binding) {
                val model = cryptoList[adapterPosition]
                cryptoIcon.setImage(model.image)
                cryptoShortName.text = model.symbol
                cryptoFullName.text = model.name
                price.text = model.current_price.toString()
                itemView.setOnClickListener {
                    onCryptoItemClicked?.invoke(model)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: CryptoRecyclerAdapter.ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(
        CryptoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = cryptoList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(cryptoList: MutableList<CryptoDataItem>) {
        this.cryptoList.clear()
        this.cryptoList.addAll(cryptoList)
        notifyDataSetChanged()
    }
}