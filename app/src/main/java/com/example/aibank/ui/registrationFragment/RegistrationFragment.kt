package com.example.aibank.ui.registrationFragment



import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.aibank.R
import com.example.aibank.databinding.RegistrationFragmentBinding
import com.example.aibankv10.ui.BaseFragment
import com.example.aibankv10.ui.others.AuthStates
import com.example.aibankv10.ui.registrationFragment.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<RegistrationFragmentBinding>(RegistrationFragmentBinding::inflate) {

    private val viewModel: RegistrationViewModel by viewModels()

    override fun start() {
        checkFieldsBeforeRegistration()
        checkPassword()
        checkEmailPattern()
        backButtonAction()
        sendSMS()
    }

    private fun sendSMS() {
            val phoneNumber = binding.phoneNumber.text.toString()
            binding.sendSMS.setOnClickListener {
                Log.d("12345", "sendSMS: 11")
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.sendSMS(phoneNumber)
                    Log.d("12345", "sendSMS: 1")
                }
        }
    }

    private fun backButtonAction() {
        binding.backButton.setOnClickListener {
            val action = RegistrationFragmentDirections.actionRegistrationFragmentToLogInFragment()
            findNavController().navigate(action)
        }
    }

    private fun checkPassword() {
        binding.repeatPassword.addTextChangedListener {
            val password = binding.password.text.toString()
            binding.repeatPassword.setBackgroundResource(R.drawable.ic_erroredittextsbackground)
            val repeatPassword = binding.repeatPassword.text.toString()
            if (repeatPassword == password || binding.repeatPassword.text!!.isEmpty()) {
                binding.repeatPassword.setBackgroundResource(R.drawable.ic_edittextbackground)
            }
        }
    }

    private fun checkEmailPattern() {
        binding.email.addTextChangedListener {
            binding.email.setBackgroundResource(R.drawable.ic_erroredittextsbackground)
            if (Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString()).matches() || binding.email.text!!.isEmpty()){
                binding.email.setBackgroundResource(R.drawable.ic_edittextbackground)
            }
        }
    }



    private fun checkFieldsBeforeRegistration() {
        val username = binding.username.text
        val email = binding.email.text
        val password = binding.password.text
        val repeatPassword = binding.repeatPassword.text
        val phoneNumber = binding.phoneNumber.text

        binding.createAccount.setOnClickListener {
            if (username.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty() || repeatPassword.isNullOrEmpty() || phoneNumber.isNullOrEmpty() || password != repeatPassword && !binding.checkBox.isChecked) {
                Toast.makeText(
                    requireContext(),
                    "Please check all fields requirements",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.registerUser(
                        email.toString(),
                        password.toString(),
                        username.toString(),
                        phoneNumber.toString(),
                        requireContext()
                    )
                    viewModel.authStateFlow.collect {
                        when (it) {
                            is AuthStates.AuthSuccess -> {binding.progressbar.isVisible = false
                                setFragmentResult("email", bundleOf("bundleKey1" to email.toString()))
                                setFragmentResult("password", bundleOf("bundleKey2" to password.toString()))
                                val action = RegistrationFragmentDirections.actionRegistrationFragmentToSuccesfullRegistrationFragment(email.toString(), password.toString())
                                findNavController().navigate(action)
                            }
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


