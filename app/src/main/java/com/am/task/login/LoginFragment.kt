package com.am.task.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.am.task.R
import com.am.task.databinding.FragmentLoginBinding
import com.am.task.remote.network.Status
import com.am.task.remote.network.coroutines.toResult
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        setupOnClickListeners()
        setupInputTextValidators()
    }

    private fun setupOnClickListeners() {
        binding.loginBtn.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                if (viewModel.isInputValid.value == true){
                    login()
                }else{
                    Snackbar.make(binding.root, getString(R.string.please_make_sure_all_the_data_is_correct), Snackbar.LENGTH_LONG).show()
                }
            }
        }
        binding.registerBtn.setOnClickListener {
            navigateToRegisterFragment()
        }
    }




    private fun setupInputTextValidators() {
        binding.emailEditText.setOnFocusChangeListener { _, hasFocus ->
            val isValidEmail = viewModel.isEmailValid()
            val errorMessage = getString(R.string.invalid_email_address)
            binding.emailInputLayout.error =  if (!hasFocus && !isValidEmail) errorMessage else null
            viewModel.isInputValid.value = viewModel.validateEmailAndPassword()
        }
        binding.passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            val isValidPassword = viewModel.isPasswordValid()
            val errorMessage = getString(R.string.invalid_password)
            binding.passwordInputLayout.error =  if (!hasFocus && !isValidPassword) errorMessage else null
            viewModel.isInputValid.value = viewModel.validateEmailAndPassword()
        }
    }


    private suspend fun login() {
        viewModel.showProgressBar.value = true
        val result = viewModel.login().toResult(viewLifecycleOwner)
        viewModel.showProgressBar.value = false
        if (result.status == Status.SUCCESS) {
            goToHomeFragment()
        } else if (result.status == Status.ERROR) {
            goToHomeFragment()
        }
    }

    private fun goToHomeFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }
    private fun navigateToRegisterFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

}