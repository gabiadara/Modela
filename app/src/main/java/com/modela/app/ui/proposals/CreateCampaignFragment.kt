package com.modela.app.ui.proposals

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.modela.app.R
import com.modela.app.databinding.FragmentCreateCampaignBinding
import com.modela.app.util.loadImage

class CreateCampaignFragment : Fragment() {

    private var _binding: FragmentCreateCampaignBinding? = null
    private val binding get() = _binding!!
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateCampaignBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val type = arguments?.getString("campaignType") ?: "Job"
        binding.tvCampaignType.text = type
        binding.tvCreateTitle.text = if (type.equals("Casting", ignoreCase = true)) {
            "Criar casting"
        } else {
            "Criar job"
        }
        binding.btnCreateCampaign.text = if (type.equals("Casting", ignoreCase = true)) {
            "Criar casting"
        } else {
            "Criar job"
        }
        binding.tvSuccessTitle.text = if (type.equals("Casting", ignoreCase = true)) {
            "Casting criado"
        } else {
            "Job criado"
        }
        binding.ivCampaignCoverPreview.loadImage("file:///android_asset/campaigns/editorial_summer.jpg")

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.btnChooseCover.setOnClickListener {
            Toast.makeText(requireContext(), "Preview visual: capa selecionada", Toast.LENGTH_SHORT).show()
        }
        binding.btnAddTag.setOnClickListener { addTagFromInput() }
        binding.btnCreateCampaign.setOnClickListener { showSuccessAnimation() }
    }

    private fun addTagFromInput() {
        val tag = binding.etNewTag.text.toString().trim()
        if (tag.isBlank()) {
            Toast.makeText(requireContext(), "Digite uma tag", Toast.LENGTH_SHORT).show()
            return
        }

        binding.tvEmptyTags.visibility = View.GONE
        binding.createTagContainer.addView(buildTag(tag))
        binding.etNewTag.text?.clear()
        hideKeyboard()
    }

    private fun buildTag(tag: String): TextView {
        return TextView(requireContext()).apply {
            text = tag
            background = requireContext().getDrawable(R.drawable.bg_home_filter_chip_active)
            setTextColor(resources.getColor(R.color.match_white, null))
            textSize = 13f
            gravity = android.view.Gravity.CENTER
            setPadding(18.dp(), 0, 18.dp(), 0)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                38.dp()
            ).apply {
                marginEnd = 8.dp()
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = requireContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.etNewTag.windowToken, 0)
        binding.etNewTag.clearFocus()
    }

    private fun showSuccessAnimation() {
        binding.successOverlay.visibility = View.VISIBLE
        binding.successOverlay.animate()
            .alpha(1f)
            .setDuration(180)
            .start()

        binding.successCard.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(360)
            .withEndAction {
                handler.postDelayed({
                    if (_binding != null) {
                        findNavController().popBackStack()
                    }
                }, 950)
            }
            .start()
    }

    override fun onDestroyView() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroyView()
        _binding = null
    }

    private fun Int.dp(): Int = (this * resources.displayMetrics.density).toInt()
}
