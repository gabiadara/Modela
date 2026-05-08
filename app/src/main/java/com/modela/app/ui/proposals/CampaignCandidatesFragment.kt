package com.modela.app.ui.proposals

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.modela.app.R
import com.modela.app.data.repository.MockDataProvider
import com.modela.app.databinding.FragmentCampaignCandidatesBinding

class CampaignCandidatesFragment : Fragment() {

    private var _binding: FragmentCampaignCandidatesBinding? = null
    private val binding get() = _binding!!

    private val pipelineItems = mutableListOf<CampaignCandidateUi>()
    private var selectedStage = CastingStage.INSCRITO

    private val candidateAdapter = CampaignCandidateAdapter(
        onMoveForward = { candidate, card ->
            candidate.stage.next()?.let { target ->
                animateCandidateMove(candidate, target, 1f, card)
            }
        },
        onMoveBack = { candidate, card ->
            candidate.stage.previous()?.let { target ->
                animateCandidateMove(candidate, target, -1f, card)
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCampaignCandidatesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val campaignId = arguments?.getString("campaignId")
        val campaign = MockDataProvider.getCompanyCampaigns().find { it.id == campaignId }

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        binding.tvCampaignName.text = campaign?.title ?: "Campanha"
        binding.tvCampaignSummary.text =
            "${campaign?.type ?: "Campanha"} - funil de casting em tempo real"

        buildPipeline()
        setupList()
        setupDemoButtons()
        renderStage(CastingStage.INSCRITO)
        setupStepperClicks()
    }

    private fun setupStepperClicks() {
        binding.stepInscrito.setOnClickListener {
            renderStage(CastingStage.INSCRITO)
        }

        binding.stepFavoritos.setOnClickListener {
            renderStage(CastingStage.FAVORITOS)
        }

        binding.stepCasting.setOnClickListener {
            renderStage(CastingStage.CASTING)
        }

        binding.stepAprovado.setOnClickListener {
            renderStage(CastingStage.APROVADO)
        }
    }

    private fun buildPipeline() {
        val source = (MockDataProvider.getFeaturedModels() + MockDataProvider.getTrendingModels()).take(8)
        pipelineItems.clear()
        pipelineItems.addAll(
            source.mapIndexed { index, profile ->
                val stage = when {
                    index < 4 -> CastingStage.INSCRITO
                    index < 6 -> CastingStage.FAVORITOS
                    index < 7 -> CastingStage.CASTING
                    else -> CastingStage.APROVADO
                }
                CampaignCandidateUi(profile, stage)
            }
        )
    }
    private fun setupList() {
        binding.rvCandidates.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCandidates.adapter = candidateAdapter
        binding.rvCandidates.itemAnimator?.changeDuration = 180L
        binding.rvCandidates.itemAnimator?.moveDuration = 240L
    }

    private fun setupDemoButtons() {
        binding.btnDemoAdvance.setOnClickListener { moveFirstVisible(forward = true) }
        binding.btnDemoBack.setOnClickListener { moveFirstVisible(forward = false) }
    }

    private fun moveFirstVisible(forward: Boolean) {
        val candidate = pipelineItems.firstOrNull { it.stage == selectedStage }
        val target = if (forward) candidate?.stage?.next() else candidate?.stage?.previous()

        if (candidate == null || target == null) {
            Toast.makeText(requireContext(), "Sem movimentos disponiveis nesta etapa", Toast.LENGTH_SHORT).show()
            return
        }

        animateCandidateMove(candidate, target, if (forward) 1f else -1f, null)
    }

    private fun animateCandidateMove(
        candidate: CampaignCandidateUi,
        targetStage: CastingStage,
        direction: Float,
        sourceView: View?
    ) {
        if (sourceView != null) {
            sourceView.animate()
                .translationX(sourceView.width * direction)
                .alpha(0f)
                .scaleX(0.96f)
                .scaleY(0.96f)
                .setDuration(230L)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .withEndAction { completeMove(candidate, targetStage, direction) }
                .start()
        } else {
            binding.rvCandidates.animate()
                .translationX(binding.rvCandidates.width * direction)
                .alpha(0f)
                .setDuration(180L)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .withEndAction { completeMove(candidate, targetStage, direction) }
                .start()
        }
    }

    private fun completeMove(
        candidate: CampaignCandidateUi,
        targetStage: CastingStage,
        direction: Float
    ) {
        candidate.stage = targetStage

        renderStage(targetStage)

        binding.rvCandidates.translationX = -32.dp() * direction
        binding.rvCandidates.alpha = 0f
        binding.rvCandidates.animate()
            .translationX(0f)
            .alpha(1f)
            .setDuration(260L)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()

        Toast.makeText(
            requireContext(),
            "${candidate.profile.name} agora esta em ${targetStage.label}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun renderStage(stage: CastingStage) {
        selectedStage = stage
        val visibleItems = pipelineItems.filter { it.stage == stage }

        candidateAdapter.submitCandidates(visibleItems)
        binding.tvStageTitle.text = stage.label
        binding.tvStageCount.text = visibleItems.size.toString()
        binding.tvPipelineCount.text = "${pipelineItems.size} perfis no funil"

        binding.tvEmptyList.visibility = if (visibleItems.isEmpty()) View.VISIBLE else View.GONE
        binding.rvCandidates.visibility = if (visibleItems.isEmpty()) View.GONE else View.VISIBLE
        binding.tvEmptyList.text = "Nenhum perfil em ${stage.label.lowercase()}."

        updateStepper(stage)
        updateDemoButtonState(visibleItems.isNotEmpty())
    }

    private fun updateStepper(stage: CastingStage) {
        val views = listOf(
            binding.stepInscrito,
            binding.stepFavoritos,
            binding.stepCasting,
            binding.stepAprovado
        )

        views.forEachIndexed { index, view ->
            val stepStage = CastingStage.values()[index]
            val active = stepStage == stage
            val completed = index < stage.ordinal
            val stroke = Color.parseColor(stepStage.accentHex)
            val fill = when {
                active -> stroke
                completed -> ContextCompat.getColor(requireContext(), R.color.match_surface_elevated)
                else -> Color.TRANSPARENT
            }

            view.background = roundedStep(fill, stroke, active || completed)
            view.setTextColor(
                if (active) Color.WHITE else ContextCompat.getColor(requireContext(), R.color.match_text_primary)
            )
            view.setTypeface(null, if (active) Typeface.BOLD else Typeface.NORMAL)
        }
    }

    private fun updateDemoButtonState(hasItems: Boolean) {
        val canBack = hasItems && selectedStage.previous() != null
        val canAdvance = hasItems && selectedStage.next() != null

        binding.btnDemoBack.isEnabled = canBack
        binding.btnDemoBack.alpha = if (canBack) 1f else 0.36f
        binding.btnDemoAdvance.isEnabled = canAdvance
        binding.btnDemoAdvance.alpha = if (canAdvance) 1f else 0.36f
    }

    private fun roundedStep(fillColor: Int, strokeColor: Int, hasStroke: Boolean): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 18.dp().toFloat()
            setColor(fillColor)
            if (hasStroke) setStroke(1.dp(), strokeColor)
        }
    }

    private fun Int.dp(): Int = (this * resources.displayMetrics.density).toInt()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
