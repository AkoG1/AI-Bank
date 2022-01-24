package com.example.aibank.ui.logInFragment

import android.util.Patterns
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.aibank.R
import com.example.aibank.databinding.LogInFragmentBinding
import com.example.aibankv10.ui.BaseFragment
import com.example.aibankv10.ui.logInFragment.LogInViewModel
import com.example.aibankv10.ui.others.AuthStates
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LogInFragment : BaseFragment<LogInFragmentBinding>(LogInFragmentBinding::inflate) {

    private val viewModel : LogInViewModel by viewModels()

    override fun start() {
        goToRegistration()
        checkEmailPattern()
        fragmentResultListener()
        logIn()
    }

    private fun checkEmailPattern() {
        binding.email.addTextChangedListener {
            binding.email.setBackgroundResource(R.drawable.ic_erroredittextsbackground)
            if (Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString()).matches() || binding.email.text!!.isEmpty()){
                binding.email.setBackgroundResource(R.drawable.ic_edittextbackground)
            }
        }
    }


    private fun goToRegistration() {
        binding.register.setOnClickListener {
            val action = LogInFragmentDirections.actionLogInFragmentToRegistrationFragment()
            findNavController().navigate(action)
        }
    }

    private fun logIn() {
        binding.signIn.setOnClickListener {
            if (binding.email.text.isNullOrEmpty() || binding.password.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show()

            }else {
                viewLifecycleOwner.lifecycleScope.launch {
                    with(binding) {
                        val email = binding.email.text.toString()
                        val password = binding.password.text.toString()
                        viewModel.logInUser(email = email, password = password)
                        viewModel.authStateFlow.collect {
                            when (it) {
                                is AuthStates.AuthSuccess -> {binding.progressbar.isVisible = false
                                    val action = LogInFragmentDirections.actionLogInFragmentToOneTimeCodeFragment()
                                    findNavController().navigate(action)}
                                is AuthStates.Loading -> binding.progressbar.isVisible = true
                                is AuthStates.Error -> {binding.progressbar.isVisible = false
                                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()}
                                is AuthStates.Idle -> binding.progressbar.isVisible = false
                            }
                        }

                    }
                }
            }
        }

    }

    private fun fragmentResultListener() {
        setFragmentResultListener("email") {requestKey, bundle ->
            if (bundle.getString("bundle1")!= null){
                binding.email.setText(bundle.getString("bundle1"))
            }
        }

        setFragmentResultListener("password") {requestKey, bundle ->
            if (bundle.getString("bundle2")!= null){
                binding.password.setText(bundle.getString("bundle2"))
            }
        }
    }

    }