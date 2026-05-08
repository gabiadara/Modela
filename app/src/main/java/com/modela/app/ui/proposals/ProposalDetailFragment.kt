package com.modela.app.ui.proposals

import android.Manifest
import android.animation.ValueAnimator
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.modela.app.R
import com.modela.app.data.model.AcceptedProposalStage
import com.modela.app.data.model.Proposal
import com.modela.app.data.model.ProposalStatus
import com.modela.app.databinding.FragmentProposalDetailBinding
import com.modela.app.util.loadImage
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class ProposalDetailFragment : Fragment() {

    private var _binding: FragmentProposalDetailBinding? = null
    private val binding get() = _binding!!
    private var modelFunnelStep = 0
    private var currentProposal: Proposal? = null
    private var approvalCelebrationShown = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProposalDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val proposalId = arguments?.getString("proposalId") ?: return
        val proposal = ProposalWorkflowStore.findProposal(proposalId) ?: return
        currentProposal = proposal

        binding.btnBack.setOnClickListener { findNavController().popBackStack() }

        modelFunnelStep = initialFunnelStep(proposal)
        populateJobInfo(proposal)
        buildTimeline()
        setupTimelineDemo()
        populatePayment(proposal)
        setupActions(proposal)

        binding.btnOpenMap.setOnClickListener {
            Toast.makeText(requireContext(), "Abrindo localizacao...", Toast.LENGTH_SHORT).show()
        }

        binding.btnEmergency.setOnClickListener {
            pulse(it)
            Toast.makeText(
                binding.root.context,
                "Alerta enviado para contato de seguranca e equipe do job",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun pulse(view: View) {
        view.animate()
            .scaleX(0.96f)
            .scaleY(0.96f)
            .setDuration(80L)
            .withEndAction {
                view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(120L)
                    .start()
            }
            .start()
    }

    private fun populateJobInfo(p: Proposal) {
        binding.ivDetailPhoto.loadImage(p.modelImageUrl)
        binding.tvDetailName.text = p.modelName
        binding.tvDetailCategory.text = p.category
        binding.tvDetailStatus.text = p.status.label
        binding.tvDetailStatus.setTextColor(Color.parseColor(p.status.colorHex))
        binding.tvDetailJobTitle.text = p.jobTitle
        binding.tvDetailJobDesc.text = p.jobDescription
        binding.tvDetailDate.text = p.date
        binding.tvDetailBudget.text = p.budget
        binding.tvDetailLocation.text = p.location
    }

    private fun initialFunnelStep(p: Proposal): Int {
        return when (p.status) {
            ProposalStatus.PENDING -> 1
            ProposalStatus.ACCEPTED -> 2
            ProposalStatus.COMPLETED -> 3
            ProposalStatus.REJECTED -> 0
        }
    }

    private fun setupTimelineDemo() {
        binding.btnTimelineBack.setOnClickListener {
            if (modelFunnelStep > 0) {
                modelFunnelStep--
                animateTimeline()
            }
        }

        binding.btnTimelineNext.setOnClickListener {
            if (modelFunnelStep < 3) {
                modelFunnelStep++
                maybeApproveFromCasting()
                animateTimeline()
            }
        }

        updateTimelineButtons()
    }

    private fun animateTimeline() {
        binding.timelineContainer.animate()
            .alpha(0f)
            .translationY(12.dp().toFloat())
            .setDuration(130L)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                buildTimeline()
                binding.timelineContainer.translationY = (-8).dp().toFloat()
                binding.timelineContainer.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(220L)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .start()
            }
            .start()
    }

    private fun buildTimeline() {
        data class Step(val title: String, val subtitle: String, val accent: String)

        val steps = listOf(
            Step("Inscrito", "Sua candidatura foi recebida pela empresa.", "#1E1E1E"),
            Step("Shortlist", "Seu perfil entrou nos favoritos da marca.", "#8B6F3D"),
            Step("Casting", "Teste, entrevista ou prova de roupa em andamento.", "#3B6A8C"),
            Step("Aprovado", "Contratado para o job e pronto para briefing final.", "#4F8A6B")
        )

        val container = binding.timelineContainer
        container.removeAllViews()

        steps.forEachIndexed { index, step ->
            val state = when {
                index < modelFunnelStep -> 0
                index == modelFunnelStep -> 1
                else -> 2
            }
            val accentColor = Color.parseColor(step.accent)

            val row = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.TOP
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            val iconCol = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(32.dp(), LinearLayout.LayoutParams.WRAP_CONTENT)
            }

            val icon = ImageView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(24.dp(), 24.dp())
                setImageResource(
                    when (state) {
                        0 -> R.drawable.ic_check_circle
                        1 -> R.drawable.ic_pending_circle
                        else -> R.drawable.ic_inactive_circle
                    }
                )
                setColorFilter(if (state == 2) Color.parseColor("#B8B8B8") else accentColor)
            }
            iconCol.addView(icon)

            if (index < steps.size - 1) {
                val line = View(requireContext()).apply {
                    layoutParams = LinearLayout.LayoutParams(2.dp(), 38.dp()).apply {
                        topMargin = 4.dp()
                        bottomMargin = 4.dp()
                    }
                    setBackgroundColor(if (state == 0) accentColor else Color.parseColor("#D9D9D9"))
                }
                iconCol.addView(line)
            }
            row.addView(iconCol)

            val textCol = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
                    marginStart = 12.dp()
                }
            }

            val title = TextView(requireContext()).apply {
                text = step.title
                setTextColor(
                    when (state) {
                        0 -> Color.parseColor("#0E0E0E")
                        1 -> accentColor
                        else -> Color.parseColor("#8B8B8B")
                    }
                )
                textSize = 15f
                if (state <= 1) setTypeface(null, Typeface.BOLD)
            }
            textCol.addView(title)

            val subtitle = TextView(requireContext()).apply {
                text = step.subtitle
                setTextColor(Color.parseColor("#8B8B8B"))
                textSize = 12f
                setPadding(0, 2.dp(), 0, if (index < steps.size - 1) 8.dp() else 0)
            }
            textCol.addView(subtitle)

            row.addView(textCol)
            container.addView(row)
        }

        updateTimelineButtons()
    }

    private fun updateTimelineButtons() {
        binding.btnTimelineBack.isEnabled = modelFunnelStep > 0
        binding.btnTimelineBack.alpha = if (modelFunnelStep > 0) 1f else 0.36f
        binding.btnTimelineNext.isEnabled = modelFunnelStep < 3
        binding.btnTimelineNext.alpha = if (modelFunnelStep < 3) 1f else 0.36f
    }

    private fun maybeApproveFromCasting() {
        val proposal = currentProposal ?: return
        if (
            modelFunnelStep == 2 &&
            proposal.status == ProposalStatus.PENDING &&
            !approvalCelebrationShown
        ) {
            val updated = ProposalWorkflowStore.updateStatus(
                proposal.id,
                ProposalStatus.ACCEPTED,
                AcceptedProposalStage.SCOUTING
            ) ?: return
            currentProposal = updated
            approvalCelebrationShown = true
            populateJobInfo(updated)
            setupActions(updated)
            showApprovalNotification(updated)
            showApprovalCelebration(updated)
        }
    }

    private fun showApprovalNotification(proposal: Proposal) {
        val title = "Voce foi aprovado"
        val message = "Voce foi aprovado na proposta ${proposal.jobTitle}"
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

        val manager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "proposal_approvals"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    "Aprovacoes de propostas",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }

        val canNotify = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

        if (canNotify) {
            val notification = NotificationCompat.Builder(requireContext(), channelId)
                .setSmallIcon(R.drawable.ic_check_circle)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()
            manager.notify(proposal.id.hashCode(), notification)
        }
    }

    private fun showApprovalCelebration(proposal: Proposal) {
        val dialog = Dialog(requireContext())
        val root = FrameLayout(requireContext()).apply {
            setPadding(24.dp(), 24.dp(), 24.dp(), 24.dp())
            background = ColorDrawable(Color.TRANSPARENT)
        }

        val fireworks = FireworksView(requireContext())
        root.addView(
            fireworks,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                320.dp()
            )
        )

        val card = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            background = GradientDrawable().apply {
                setColor(Color.WHITE)
                cornerRadius = 24.dp().toFloat()
                setStroke(1.dp(), Color.parseColor("#22000000"))
            }
            elevation = 18.dp().toFloat()
            setPadding(24.dp(), 28.dp(), 24.dp(), 24.dp())
        }
        root.addView(
            card,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            ).apply { marginStart = 8.dp(); marginEnd = 8.dp() }
        )

        card.addView(TextView(requireContext()).apply {
            text = "Aprovado!"
            gravity = Gravity.CENTER
            setTextColor(Color.parseColor("#0E0E0E"))
            textSize = 28f
            setTypeface(null, Typeface.BOLD)
        })

        card.addView(TextView(requireContext()).apply {
            text = "Voce foi aprovado na proposta ${proposal.jobTitle}"
            gravity = Gravity.CENTER
            setTextColor(Color.parseColor("#5E5E5E"))
            textSize = 14f
            setPadding(0, 10.dp(), 0, 0)
        })

        card.addView(TextView(requireContext()).apply {
            text = "Agora ela esta em Aceitas como Scouting"
            gravity = Gravity.CENTER
            setTextColor(Color.parseColor("#8B6F3D"))
            textSize = 13f
            setTypeface(null, Typeface.BOLD)
            setPadding(0, 12.dp(), 0, 18.dp())
        })

        card.addView(TextView(requireContext()).apply {
            text = "Continuar"
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            textSize = 14f
            setTypeface(null, Typeface.BOLD)
            background = GradientDrawable().apply {
                setColor(Color.parseColor("#0E0E0E"))
                cornerRadius = 14.dp().toFloat()
            }
            setPadding(22.dp(), 12.dp(), 22.dp(), 12.dp())
            setOnClickListener { dialog.dismiss() }
        })

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(root)
        dialog.setOnShowListener { fireworks.start() }
        dialog.show()
    }

    private fun populatePayment(p: Proposal) {
        val budgetStr = p.budget.replace("R$ ", "").replace(".", "").replace(",", ".")
        val budget = budgetStr.toDoubleOrNull() ?: 0.0
        val fee = budget * 0.10
        val total = budget + fee

        binding.tvPayCache.text = p.budget
        binding.tvPayFee.text = "R$ ${String.format("%.0f", fee)}"
        binding.tvPayTotal.text = "R$ ${String.format("%.0f", total)}"
    }

    private fun setupActions(p: Proposal) {
        binding.btnDetailPrimary.visibility = View.VISIBLE
        binding.btnDetailPrimary.isEnabled = true
        binding.btnDetailPrimary.setTextColor(Color.WHITE)
        binding.btnDetailPrimary.setBackgroundResource(R.drawable.bg_button_gold)

        when (p.status) {
            ProposalStatus.PENDING -> {
                binding.btnDetailPrimary.text = "Em analise"
                binding.btnDetailPrimary.setBackgroundResource(R.drawable.bg_proposal_action_secondary)
                binding.btnDetailPrimary.setTextColor(Color.parseColor("#0E0E0E"))
                binding.btnDetailPrimary.isEnabled = false
                binding.btnDetailSecondary.text = "Acompanhar funil"
            }
            ProposalStatus.ACCEPTED -> {
                binding.btnDetailPrimary.text = "Enviar Mensagem"
                binding.btnDetailSecondary.text = "Ver Perfil"
            }
            ProposalStatus.COMPLETED -> {
                binding.btnDetailPrimary.text = "Avaliar Experiencia"
                binding.btnDetailSecondary.text = "Enviar Mensagem"
            }
            ProposalStatus.REJECTED -> {
                binding.btnDetailPrimary.visibility = View.GONE
                binding.btnDetailSecondary.text = "Voltar as Propostas"
            }
        }

        binding.btnDetailPrimary.setOnClickListener {
            Toast.makeText(requireContext(), "Acao registrada com sucesso!", Toast.LENGTH_SHORT).show()
        }
        binding.btnDetailSecondary.setOnClickListener {
            Toast.makeText(requireContext(), "Em breve", Toast.LENGTH_SHORT).show()
        }
    }

    private fun Int.dp(): Int = (this * resources.displayMetrics.density).toInt()

    private class FireworksView(context: Context) : View(context) {
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        private val particles = mutableListOf<Particle>()
        private var progress = 0f
        private var animator: ValueAnimator? = null

        fun start() {
            if (particles.isEmpty()) createParticles()
            animator?.cancel()
            animator = ValueAnimator.ofFloat(0f, 1f).apply {
                duration = 1800L
                interpolator = DecelerateInterpolator()
                addUpdateListener {
                    progress = it.animatedValue as Float
                    invalidate()
                }
                start()
            }
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            if (particles.isEmpty()) createParticles()
            particles.forEach { particle ->
                paint.color = particle.color
                paint.alpha = ((1f - progress) * 255).toInt().coerceIn(0, 255)
                val distance = particle.distance * progress
                val x = particle.centerX + cos(particle.angle) * distance
                val y = particle.centerY + sin(particle.angle) * distance + 80f * progress
                canvas.drawCircle(x, y, particle.radius * (1.2f - progress * 0.4f), paint)
            }
        }

        override fun onDetachedFromWindow() {
            animator?.cancel()
            super.onDetachedFromWindow()
        }

        private fun createParticles() {
            val colors = listOf(
                Color.parseColor("#F5C542"),
                Color.parseColor("#D64B5F"),
                Color.parseColor("#3B6A8C"),
                Color.parseColor("#4F8A6B"),
                Color.parseColor("#FFFFFF")
            )
            val safeWidth = width.takeIf { it > 0 } ?: 720
            val safeHeight = height.takeIf { it > 0 } ?: 320
            particles.clear()
            repeat(90) {
                val burst = it % 3
                val centerX = safeWidth * when (burst) {
                    0 -> 0.25f
                    1 -> 0.5f
                    else -> 0.76f
                }
                val centerY = safeHeight * if (burst == 1) 0.35f else 0.48f
                particles += Particle(
                    centerX = centerX,
                    centerY = centerY,
                    angle = Random.nextFloat() * Math.PI.toFloat() * 2f,
                    distance = Random.nextInt(80, 190).toFloat(),
                    radius = Random.nextInt(4, 10).toFloat(),
                    color = colors.random()
                )
            }
        }

        private data class Particle(
            val centerX: Float,
            val centerY: Float,
            val angle: Float,
            val distance: Float,
            val radius: Float,
            val color: Int
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
