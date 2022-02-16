package com.example.aibank.ui.succesfullRegistrationFragment

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aibank.databinding.FragmentSuccesfullRegistrationBinding
import com.example.aibank.ui.BaseFragment


class SuccesfullRegistrationFragment :
    BaseFragment<FragmentSuccesfullRegistrationBinding>(FragmentSuccesfullRegistrationBinding::inflate) {

    private val args: SuccesfullRegistrationFragmentArgs by navArgs()

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
                SuccesfullRegistrationFragmentDirections.actionSuccesfullRegistrationFragmentToLogInFragment()
            findNavController().navigate(action)
        }
    }

    companion object {
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
        private const val BUNDLE_1 = "bundle1"
        private const val BUNDLE_2 = "bundle2"
    }

}