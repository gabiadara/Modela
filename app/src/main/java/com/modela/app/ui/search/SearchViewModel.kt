package com.modela.app.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.modela.app.data.model.Category
import com.modela.app.data.model.ModelProfile
import com.modela.app.data.repository.MockDataProvider

class SearchViewModel : ViewModel() {
    private val allModels = MockDataProvider.getFeaturedModels() + MockDataProvider.getTrendingModels()
    private val _results = MutableLiveData(allModels)
    val results: LiveData<List<ModelProfile>> = _results
    private val _categories = MutableLiveData(MockDataProvider.getCategories())
    val categories: LiveData<List<Category>> = _categories

    fun search(query: String) {
        _results.value = if (query.isEmpty()) allModels
        else allModels.filter {
            it.name.contains(query, ignoreCase = true) || it.category.contains(query, ignoreCase = true)
        }
    }
}
