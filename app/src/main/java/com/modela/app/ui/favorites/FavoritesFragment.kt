package com.modela.app.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.modela.app.R
import com.modela.app.data.repository.MockDataProvider
import com.modela.app.databinding.FragmentFavoritesBinding
import com.modela.app.ui.search.SearchResultsAdapter
import com.modela.app.util.gone
import com.modela.app.util.visible

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Show some models as "favorited" for demo
        val favorites = MockDataProvider.getFeaturedModels().take(3)
        if (favorites.isEmpty()) {
            binding.emptyState.visible()
            binding.rvFavorites.gone()
        } else {
            binding.emptyState.gone()
            binding.rvFavorites.visible()
            binding.rvFavorites.adapter = SearchResultsAdapter { model ->
                val bundle = Bundle().apply { putString("modelId", model.id) }
                findNavController().navigate(R.id.action_favorites_to_modelProfile, bundle)
            }.also { it.submitList(favorites) }
        }
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
