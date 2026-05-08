package com.modela.app.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.modela.app.data.model.JobOpening
import com.modela.app.data.repository.MockDataProvider
import com.modela.app.databinding.FragmentJobOpeningDetailBinding
import com.modela.app.util.CompanyVisualHelper
import com.modela.app.util.JobTagHelper
import com.modela.app.util.loadImage

class JobOpeningDetailFragment : Fragment() {

    private var _binding: FragmentJobOpeningDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobOpeningDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val jobId = arguments?.getString("jobId") ?: return
        val job = MockDataProvider.getJobOpenings().find { it.id == jobId } ?: return

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        populateHeader(job)
        JobTagHelper.addTagsTo(binding.detailTagContainer, job.tags)
        populateDescription(job)
        buildRequirements(job)
        setupActions(job)
    }

    private fun populateHeader(job: JobOpening) {
        binding.ivDetailCampaignImage.loadImage(job.campaignImageUrl)
        binding.ivDetailCompanyLogo.setImageResource(CompanyVisualHelper.logoForJob(job.id))
        binding.tvDetailCompanyName.text = job.companyName
        binding.tvDetailCompanyLocation.text = job.companyLocation
        binding.tvDetailJobTitle.text = job.jobTitle
        binding.tvDetailBudget.text = job.budget
        binding.tvDetailDate.text = "Data: ${job.date}"
        binding.tvDetailLocation.text = "Local: ${job.location}"
        binding.tvDetailApplicants.text = "${job.applicants} candidatos"
    }

    private fun populateDescription(job: JobOpening) {
        binding.tvDetailFullDescription.text = job.fullDescription.ifBlank {
            job.jobDescription
        }
    }

    private fun buildRequirements(job: JobOpening) {
        val container = binding.requirementsContainer
        container.removeAllViews()
        job.requirements.forEach { requirement ->
            val row = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { bottomMargin = 10.dp() }
            }

            val dot = TextView(requireContext()).apply {
                text = "-"
                textSize = 15f
                setTextColor(Color.parseColor("#5E5E5E"))
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { marginEnd = 10.dp() }
            }

            val text = TextView(requireContext()).apply {
                text = requirement
                textSize = 13f
                setTextColor(Color.parseColor("#5E5E5E"))
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
            }

            row.addView(dot)
            row.addView(text)
            container.addView(row)
        }
    }

    private fun setupActions(job: JobOpening) {
        binding.btnApplyNow.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Candidatura enviada para ${job.companyName}",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.btnSendMessage.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Em breve: chat com a empresa",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun Int.dp(): Int = (this * resources.displayMetrics.density).toInt()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
