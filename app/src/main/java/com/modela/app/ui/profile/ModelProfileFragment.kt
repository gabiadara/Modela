package com.modela.app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.modela.app.R
import com.modela.app.data.repository.MockDataProvider
import com.modela.app.databinding.FragmentModelProfileBinding

class ModelProfileFragment : Fragment() {
    private var _binding: FragmentModelProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentModelProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val modelId = arguments?.getString("modelId") ?: return
        val model = MockDataProvider.getModelById(modelId) ?: return

        binding.tvName.text = model.name
        binding.tvCategory.text = "${model.category} • ${model.location}"
        binding.tvJobsCount.text = model.jobsCompleted.toString()
        binding.tvRating.text = model.rating.toString()
        binding.tvBio.text = model.bio

        // Physical characteristics as simple list
        val chars = listOf(
            "Altura" to model.height, "Peso" to model.weight,
            "Olhod" to model.eyeColor, "Cabelo" to model.hairColor,
            "Busto" to model.bust, "Cintura" to model.waist,
            "Quadril" to model.hips, "Calçado" to model.shoeSize
        ).filter { it.second.isNotEmpty() }
        binding.rvCharacteristics.adapter = CharacteristicsAdapter(chars)

        // Gallery placeholder
        binding.rvGallery.adapter = PhotoGalleryAdapter(List(6) { "" })

        // Reviews
        binding.rvReviews.adapter = ReviewsAdapter(MockDataProvider.getReviews())

        binding.ivBack.setOnClickListener { findNavController().popBackStack() }
        binding.btnHire.setOnClickListener {
            Toast.makeText(context, R.string.hire_request_sent, Toast.LENGTH_SHORT).show()
        }
        binding.btnFavorite.setOnClickListener {
            Toast.makeText(context, R.string.add_to_favorites, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
