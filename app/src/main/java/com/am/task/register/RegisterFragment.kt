package com.am.task.register

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
import com.am.task.databinding.FragmentRegisterBinding
import com.am.task.remote.network.Status
import com.am.task.remote.network.coroutines.toResult
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val viewModel: RegisterViewModel by viewModels()
    lateinit var binding :FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupInputTextValidators()
    }


    private fun setupClickListeners() {

        binding.registerBtn.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                if (viewModel.isInputValid.get()){
                    register()
                }else{
                    Snackbar.make(binding.root, getString(R.string.please_make_sure_all_the_data_is_correct), Snackbar.LENGTH_LONG).show()
                }
            }
        }

        binding.backImageView.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupInputTextValidators() {
        binding.emailEditText.setOnFocusChangeListener { _, hasFocus ->
            val isValidEmail = viewModel.isEmailValid()
            val errorMessage = getString(R.string.invalid_email_address)
            binding.emailInputLayout.error =  if (!hasFocus && !isValidEmail) errorMessage else null
        }
        binding.passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            val isValidPassword = viewModel.isPasswordValid()
            val errorMessage = getString(R.string.invalid_password)
            binding.passwordInputLayout.error =  if (!hasFocus && !isValidPassword) errorMessage else null
        }
        binding.ageEditText.setOnFocusChangeListener { _, hasFocus ->
            val isValidAge = viewModel.isValidAge()
            val errorMessage = getString(R.string.invalid_age)
            binding.ageInputLayout.error =  if (!hasFocus && !isValidAge) errorMessage else null
        }
    }

    private suspend fun register() {
        viewModel.showProgress.set(true)
        val result = viewModel.register().toResult(viewLifecycleOwner)
        viewModel.showProgress.set(false)
        if (result.status == Status.SUCCESS) {
            goToHomeFragment()
        } else if (result.status == Status.ERROR) {
            goToHomeFragment()
        }
    }

    private fun goToHomeFragment() {
        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
    }

}