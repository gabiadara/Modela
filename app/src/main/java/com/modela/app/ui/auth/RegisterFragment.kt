package com.modela.app.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.modela.app.R
import com.modela.app.databinding.FragmentRegisterBinding
import com.modela.app.ui.main.MainActivity
import com.modela.app.util.Constants
import com.modela.app.util.Resource
import com.modela.app.util.UserTypeHelper
import com.modela.app.util.gone
import com.modela.app.util.visible

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()
    private var selectedUserType = UserTypeHelper.MODEL

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectUserType(UserTypeHelper.MODEL)

        binding.cardModel.setOnClickListener { selectUserType(UserTypeHelper.MODEL) }
        binding.cardContractor.setOnClickListener { selectUserType(UserTypeHelper.CONTRACTOR) }

        binding.btnRegister.setOnClickListener {
            viewModel.register(
                binding.etName.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString(),
                binding.etConfirmPassword.text.toString(),
                selectedUserType
            )
        }

        binding.tvGoToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_register_to_login)
        }

        viewModel.registerState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.progressBar.visible()
                    binding.btnRegister.isEnabled = false
                    binding.tvError.gone()
                }
                is Resource.Success -> {
                    binding.progressBar.gone()
                    val prefs = requireContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
                    prefs.edit()
                        .putBoolean(Constants.KEY_IS_LOGGED_IN, true)
                        .putString(Constants.KEY_USER_TYPE, selectedUserType)
                        .putString(Constants.KEY_USER_NAME, binding.etName.text.toString())
                        .putString(Constants.KEY_USER_EMAIL, binding.etEmail.text.toString())
                        .apply()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }
                is Resource.Error -> {
                    binding.progressBar.gone()
                    binding.btnRegister.isEnabled = true
                    binding.tvError.text = state.message
                    binding.tvError.visible()
                }
            }
        }
    }

    private fun selectUserType(type: String) {
        selectedUserType = type
        val goldBorder = ContextCompat.getDrawable(requireContext(), R.drawable.bg_button_gold)
        val defaultBg = ContextCompat.getDrawable(requireContext(), R.drawable.bg_user_type_card)
        if (UserTypeHelper.isModel(type)) {
            binding.cardModel.background = goldBorder
            binding.cardContractor.background = defaultBg
        } else {
            binding.cardContractor.background = goldBorder
            binding.cardModel.background = defaultBg
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
