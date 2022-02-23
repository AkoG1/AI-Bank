package com.example.aibank.ui.succesfullRegistrationFragment

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aibank.databinding.FragmentSuccesfullRegistrationBinding
import com.example.aibank.ui.BaseFragment


class SuccessRegistrationFragment :
    BaseFragment<FragmentSuccesfullRegistrationBinding>(FragmentSuccesfullRegistrationBinding::inflate) {

    private val args: SuccessRegistrationFragmentArgs by navArgs()

    override fun start() {
        collectArgs()
        setListeners()

    }

    private fun collectArgs() {
        val emailArg: String? = args.email ?: null
        val passwordArgs: String? = args.password ?: null
        setFragmentResult(EMAIL, bundleOf(BUNDLE_1 to emailArg))
        setFragmentResult(PASSWORD, bundleOf(BUNDLE_2 to passwordArgs))
    }

    private fun setListeners() {
        binding.continueButton.setOnClickListener {
            val action =
                SuccessRegistrationFragmentDirections.actionSuccesfullRegistrationFragmentToLogInFragment()
            findNavController().navigate(action)
        }
    }

}