package com.modela.app.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.modela.app.R
import com.modela.app.databinding.FragmentSearchBinding
import com.modela.app.ui.home.CategoryAdapter
import com.modela.app.util.gone
import com.modela.app.util.visible

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var resultsAdapter: SearchResultsAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultsAdapter = SearchResultsAdapter { model ->
            val bundle = Bundle().apply { putString("modelId", model.id) }
            findNavController().navigate(R.id.action_search_to_modelProfile, bundle)
        }
        binding.rvResults.adapter = resultsAdapter
        categoryAdapter = CategoryAdapter { }
        binding.rvCategories.adapter = categoryAdapter

        viewModel.results.observe(viewLifecycleOwner) { models ->
            resultsAdapter.submitList(models)
            if (models.isEmpty() && binding.etSearch.text.toString().isNotEmpty()) {
                binding.emptyState.visible()
                binding.rvResults.gone()
            } else {
                binding.emptyState.gone()
                binding.rvResults.visible()
            }
        }
        viewModel.categories.observe(viewLifecycleOwner) { categoryAdapter.submitList(it) }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.search(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}
