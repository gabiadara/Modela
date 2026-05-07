package com.modela.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.modela.app.R
import com.modela.app.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var featuredAdapter: FeaturedModelsAdapter
    private lateinit var trendingAdapter: TrendingModelsAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var recommendedAdapter: TrendingModelsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        observeData()

        binding.searchBarContainer.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
    }

    private fun setupAdapters() {
        featuredAdapter = FeaturedModelsAdapter { model ->
            val bundle = Bundle().apply { putString("modelId", model.id) }
            findNavController().navigate(R.id.action_home_to_modelProfile, bundle)
        }
        binding.rvFeatured.adapter = featuredAdapter

        trendingAdapter = TrendingModelsAdapter { model ->
            val bundle = Bundle().apply { putString("modelId", model.id) }
            findNavController().navigate(R.id.action_home_to_modelProfile, bundle)
        }
        binding.rvTrending.adapter = trendingAdapter

        categoryAdapter = CategoryAdapter { }
        binding.rvCategories.adapter = categoryAdapter

        recommendedAdapter = TrendingModelsAdapter { model ->
            val bundle = Bundle().apply { putString("modelId", model.id) }
            findNavController().navigate(R.id.action_home_to_modelProfile, bundle)
        }
        binding.rvRecommended.adapter = recommendedAdapter
    }

    private fun observeData() {
        viewModel.featured.observe(viewLifecycleOwner) { featuredAdapter.submitList(it) }
        viewModel.trending.observe(viewLifecycleOwner) { trendingAdapter.submitList(it) }
        viewModel.categories.observe(viewLifecycleOwner) { categoryAdapter.submitList(it) }
        viewModel.recommended.observe(viewLifecycleOwner) { recommendedAdapter.submitList(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
