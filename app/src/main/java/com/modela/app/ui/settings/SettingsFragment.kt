package com.modela.app.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.modela.app.databinding.FragmentSettingsBinding
import com.modela.app.ui.auth.AuthActivity
import com.modela.app.util.Constants

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = requireContext().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)
        val userName = prefs.getString(Constants.KEY_USER_NAME, "User") ?: "User"
        val userEmail = prefs.getString(Constants.KEY_USER_EMAIL, "user@modela.com") ?: ""

        binding.tvUserName.text = userName
        binding.tvUserEmail.text = userEmail

        binding.btnLogout.setOnClickListener {
            prefs.edit().clear().apply()
            startActivity(Intent(requireContext(), AuthActivity::class.java))
            requireActivity().finishAffinity()
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
