package com.example.aibank.ui.succesfullRegistrationFragment

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aibank.databinding.FragmentSuccesfullRegistrationBinding
import com.example.aibankv10.ui.BaseFragment


class SuccesfullRegistrationFragment : BaseFragment<FragmentSuccesfullRegistrationBinding>(FragmentSuccesfullRegistrationBinding::inflate) {

    private val args: SuccesfullRegistrationFragmentArgs by navArgs()

    override fun start() {
        collectArgs()
        setListeners()

    }

    private fun collectArgs() {
        val emailArg : String? = args.email?: null
        val passwordArgs: String? = args.password?: null
        setFragmentResult("email", bundleOf("bundle1" to emailArg))
        setFragmentResult("password", bundleOf("bundle2" to passwordArgs))
    }

    private fun setListeners() {
        binding.continueButton.setOnClickListener {
            val action = SuccesfullRegistrationFragmentDirections.actionSuccesfullRegistrationFragmentToLogInFragment()
            findNavController().navigate(action)
        }
    }

}