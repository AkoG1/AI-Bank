package com.example.aibank.ui.oneTimeCodeFragment

import androidx.navigation.fragment.findNavController
import com.example.aibank.databinding.OneTimeCodeFragmentBinding
import com.example.aibankv10.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OneTimeCodeFragment : BaseFragment<OneTimeCodeFragmentBinding>(OneTimeCodeFragmentBinding::inflate) {

    override fun start() {
        setListeners()
    }

    private fun setListeners() {
        binding.backButton.setOnClickListener {
            val action = OneTimeCodeFragmentDirections.actionOneTimeCodeFragmentToLogInFragment()
            findNavController().navigate(action)
        }

        binding.continueToYourAccount.setOnClickListener {
            val action = OneTimeCodeFragmentDirections.actionOneTimeCodeFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }


}