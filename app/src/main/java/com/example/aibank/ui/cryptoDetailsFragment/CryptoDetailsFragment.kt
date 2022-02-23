package com.example.aibank.ui.cryptoDetailsFragment

import androidx.navigation.fragment.navArgs
import com.example.aibank.databinding.CryptoDetailsFragmentBinding
import com.example.aibank.extensions.setImage
import com.example.aibank.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoDetailsFragment : BaseFragment<CryptoDetailsFragmentBinding>(CryptoDetailsFragmentBinding:: inflate) {

    private val safeArgs: CryptoDetailsFragmentArgs by navArgs()

    override fun start() {
        setDataToViews()
    }

    private fun setDataToViews() {
        with(binding) {
            val model = safeArgs.model
            cryptoIcon.setImage(model?.image)
            cryptoName.text = model?.name
            cryptoPrice.text = model?.current_price.toString()
            textView2.text = model?.marketCap
            textView4.text = model?.high24h.toString()
            textView6.text = model?.low24h.toString()
            textView8.text = model?.priceChanged24H.toString()
            textView10.text = model?.ath
            textView12.text = model?.atl
            cryptoSymbol.text = model?.symbol?.uppercase()
        }
    }

}