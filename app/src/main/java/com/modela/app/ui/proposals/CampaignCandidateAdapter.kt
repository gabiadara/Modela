package com.modela.app.ui.proposals

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.modela.app.R
import com.modela.app.data.model.ModelProfile
import com.modela.app.databinding.ItemCampaignCandidateBinding
import com.modela.app.util.loadImage

enum class CastingStage(
    val label: String,
    val tabTitle: String,
    val subtitle: String,
    val accentHex: String,
    val nextAction: String
) {
    INSCRITO(
        "Inscrito",
        "Inscritos",
        "Candidatos que aplicaram para a campanha e aguardam curadoria.",
        "#1E1E1E",
        "Favoritos"
    ),
    FAVORITOS(
        "Favoritos",
        "Favoritos",
        "Favoritos da empresa, separados para decisao criativa.",
        "#8B6F3D",
        "Casting"
    ),
    CASTING(
        "Casting",
        "Casting",
        "Fase de teste, entrevista, prova de roupa ou chamada com direcao.",
        "#3B6A8C",
        "Aprovar"
    ),
    APROVADO(
        "Aprovado",
        "Aprovados",
        "Modelos contratados para o job, prontos para briefing final.",
        "#4F8A6B",
        "Contratado"
    );

    fun next(): CastingStage? = values().getOrNull(ordinal + 1)
    fun previous(): CastingStage? = values().getOrNull(ordinal - 1)
}

data class CampaignCandidateUi(
    val profile: ModelProfile,
    var stage: CastingStage
)

class CampaignCandidateAdapter(
    private val onMoveForward: (CampaignCandidateUi, View) -> Unit,
    private val onMoveBack: (CampaignCandidateUi, View) -> Unit
) : RecyclerView.Adapter<CampaignCandidateAdapter.ViewHolder>() {

    private val items = mutableListOf<CampaignCandidateUi>()

    inner class ViewHolder(private val binding: ItemCampaignCandidateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(candidate: CampaignCandidateUi) {
            val model = candidate.profile
            binding.rootCard.translationX = 0f
            binding.rootCard.alpha = 1f
            binding.rootCard.scaleX = 1f
            binding.rootCard.scaleY = 1f

            binding.ivCandidatePhoto.loadImage(model.profileImageUrl)
            binding.tvCandidateName.text = model.name
            binding.tvCandidateMeta.text = "${model.category} - ${model.location}"
            binding.tvCandidateScore.text = "${model.rating}/5"
            binding.tvCandidateStage.text = candidate.stage.label
            binding.tvCandidateStage.background = roundedFill(candidate.stage.accentHex)

            val canMoveBack = candidate.stage.previous() != null
            binding.btnMoveBack.alpha = if (canMoveBack) 1f else 0.32f
            binding.btnMoveBack.isEnabled = canMoveBack

            val nextStage = candidate.stage.next()
            binding.btnMoveForward.text = candidate.stage.nextAction
            binding.btnMoveForward.alpha = if (nextStage != null) 1f else 0.42f
            binding.btnMoveForward.isEnabled = nextStage != null

            binding.btnMoveBack.setOnClickListener {
                onMoveBack(candidate, binding.rootCard)
            }
            binding.btnMoveForward.setOnClickListener {
                onMoveForward(candidate, binding.rootCard)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCampaignCandidateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun submitCandidates(candidates: List<CampaignCandidateUi>) {
        items.clear()
        items.addAll(candidates)
        notifyDataSetChanged()
    }

    private fun roundedFill(hex: String): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 50f
            setColor(Color.parseColor(hex))
            setStroke(1, Color.parseColor("#22000000"))
        }
    }
}
