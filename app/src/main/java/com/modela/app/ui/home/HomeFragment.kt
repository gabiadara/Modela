package com.modela.app.ui.home

import android.graphics.Typeface
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.modela.app.R
import com.modela.app.data.model.JobOpening
import com.modela.app.databinding.FragmentHomeBinding
import com.modela.app.util.Constants
import com.modela.app.util.UserTypeHelper

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    // Company feed adapters
    private lateinit var featuredAdapter: FeaturedModelsAdapter
    private lateinit var trendingAdapter: TrendingModelsAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var recommendedAdapter: TrendingModelsAdapter

    // Model feed adapter
    private lateinit var jobOpeningAdapter: JobOpeningAdapter
    private lateinit var companySpotlightAdapter: CompanySpotlightAdapter

    private var isModel = false
    private var selectedOpportunityChip: TextView? = null
    private var selectedOpportunityFilter: String? = null
    private var allJobOpenings: List<JobOpening> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Detect user type
        val prefs = requireContext().getSharedPreferences(
            Constants.PREF_NAME, AppCompatActivity.MODE_PRIVATE
        )
        val userType = prefs.getString(Constants.KEY_USER_TYPE, UserTypeHelper.CONTRACTOR)
        isModel = UserTypeHelper.isModel(userType)

        switchLayout()
        setupAdapters()
        setupSearch()
        setupOpportunityFilters()
        observeData()
    }

    /** Shows the correct layout section based on user type */
    private fun switchLayout() {
        if (isModel) {
            binding.layoutCompanyFeed.visibility = View.GONE
            binding.layoutModelFeed.visibility = View.VISIBLE
            binding.tvHomeGreeting.text = "Oportunidades"
            binding.tvSearchHint.text = "Buscar vagas, marcas ou castings"
            binding.tvHomeSubtitle.text = "Castings e vagas selecionados para voce"
        } else {
            binding.layoutCompanyFeed.visibility = View.VISIBLE
            binding.layoutModelFeed.visibility = View.GONE
            binding.tvHomeGreeting.text = "Modela"
            binding.tvHomeSubtitle.text = "Encontre o modelo ideal"
            binding.tvSearchHint.text = getString(R.string.search_models)
        }
    }

    private fun setupAdapters() {
        if (isModel) {
            companySpotlightAdapter = CompanySpotlightAdapter { job ->
                val bundle = Bundle().apply { putString("jobId", job.id) }
                findNavController().navigate(R.id.action_home_to_jobOpeningDetail, bundle)
            }
            binding.rvCompanySpotlight.adapter = companySpotlightAdapter

            jobOpeningAdapter = JobOpeningAdapter { job ->
                val bundle = Bundle().apply { putString("jobId", job.id) }
                findNavController().navigate(R.id.action_home_to_jobOpeningDetail, bundle)
            }
            binding.rvJobOpenings.adapter = jobOpeningAdapter
        } else {
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
            val spacing = resources.getDimensionPixelSize(R.dimen.spacing_md)

            binding.rvRecommended.addItemDecoration(
                GridSpacingItemDecoration(2, spacing)
            )
        }
    }

    private fun setupSearch() {
        binding.searchBarContainer.setOnClickListener {
            if (isModel) {
                Toast.makeText(
                    requireContext(),
                    "Busca de vagas em breve",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                findNavController().navigate(R.id.searchFragment)
            }
        }
    }

    private fun setupOpportunityFilters() {
        if (!isModel) return

        val filters = listOf(
            binding.chipOpportunityAll to null,
            binding.chipOpportunityUrgent to "Urgente",
            binding.chipOpportunityEditorial to "Editorial",
            binding.chipOpportunityCommercial to "Comercial",
            binding.chipOpportunityRunway to "Passarela",
            binding.chipOpportunityPremium to "Premium"
        )

        selectedOpportunityChip = binding.chipOpportunityAll
        filters.forEach { (chip, filter) ->
            chip.setOnClickListener {
                selectOpportunityFilter(chip, filter)
            }
        }
    }

    private fun selectOpportunityFilter(chip: TextView, filter: String?) {
        resetOpportunityChip(selectedOpportunityChip)
        highlightOpportunityChip(chip)
        selectedOpportunityChip = chip
        selectedOpportunityFilter = filter
        applyOpportunityFilter()
    }

    private fun highlightOpportunityChip(chip: TextView?) {
        chip?.let {
            it.background = requireContext().getDrawable(R.drawable.bg_home_filter_chip_active)
            it.setTextColor(resources.getColor(R.color.match_white, null))
            it.setTypeface(null, Typeface.BOLD)
        }
    }

    private fun resetOpportunityChip(chip: TextView?) {
        chip?.let {
            it.background = requireContext().getDrawable(R.drawable.bg_home_filter_chip)
            it.setTextColor(resources.getColor(R.color.match_text_secondary, null))
            it.setTypeface(null, Typeface.NORMAL)
        }
    }

    private fun applyOpportunityFilter() {
        val filter = selectedOpportunityFilter
        val filteredJobs = if (filter == null) {
            allJobOpenings
        } else {
            allJobOpenings.filter { job ->
                job.category.equals(filter, ignoreCase = true) ||
                    job.tags.any { it.label.equals(filter, ignoreCase = true) } ||
                    (filter == "Premium" && job.tags.any { it.label.contains("Exclusivo", ignoreCase = true) || it.label.contains("Alta", ignoreCase = true) })
            }
        }

        companySpotlightAdapter.submitList(filteredJobs.take(5))
        jobOpeningAdapter.submitList(filteredJobs)
        binding.tvModelFeedCount.text = "${filteredJobs.size} novas"
    }

    private fun observeData() {
        if (isModel) {
            viewModel.jobOpenings.observe(viewLifecycleOwner) { jobs ->
                allJobOpenings = jobs
                applyOpportunityFilter()
            }
        } else {
            viewModel.featured.observe(viewLifecycleOwner) { featuredAdapter.submitList(it) }
            viewModel.trending.observe(viewLifecycleOwner) { trendingAdapter.submitList(it) }
            viewModel.categories.observe(viewLifecycleOwner) { categoryAdapter.submitList(it) }
            viewModel.recommended.observe(viewLifecycleOwner) { recommendedAdapter.submitList(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    class GridSpacingItemDecoration(
        private val spanCount: Int,
        private val spacing: Int
    ) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {

            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount

            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            // Espaçamento vertical
            if (position >= spanCount) {
                outRect.top = spacing
            }

            outRect.bottom = spacing
        }
    }
}
