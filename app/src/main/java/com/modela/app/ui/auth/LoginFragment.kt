package com.modela.app.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.modela.app.R
import com.modela.app.databinding.FragmentLoginBinding
import com.modela.app.ui.main.MainActivity
import com.modela.app.util.Constants
import com.modela.app.util.Resource
import com.modela.app.util.gone
import com.modela.app.util.visible

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.login(email, password)
        }

        binding.tvGoToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }

        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.progressBar.visible()
                    binding.btnLogin.isEnabled = false
                    binding.tvError.gone()
                }
                is Resource.Success -> {
                    binding.progressBar.gone()
                    val prefs = requireContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
                    prefs.edit().putBoolean(Constants.KEY_IS_LOGGED_IN, true)
                        .putString(Constants.KEY_USER_EMAIL, binding.etEmail.text.toString())
                        .apply()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }
                is Resource.Error -> {
                    binding.progressBar.gone()
                    binding.btnLogin.isEnabled = true
                    binding.tvError.text = state.message
                    binding.tvError.visible()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
