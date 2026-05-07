package com.modela.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.modela.app.data.model.Category
import com.modela.app.data.model.ModelProfile
import com.modela.app.data.repository.MockDataProvider

class HomeViewModel : ViewModel() {
    private val _featured = MutableLiveData<List<ModelProfile>>()
    val featured: LiveData<List<ModelProfile>> = _featured

    private val _trending = MutableLiveData<List<ModelProfile>>()
    val trending: LiveData<List<ModelProfile>> = _trending

    private val _recommended = MutableLiveData<List<ModelProfile>>()
    val recommended: LiveData<List<ModelProfile>> = _recommended

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    init { loadData() }

    private fun loadData() {
        _categories.value = MockDataProvider.getCategories()
        _featured.value = MockDataProvider.getFeaturedModels()
        _trending.value = MockDataProvider.getTrendingModels()
        _recommended.value = MockDataProvider.getRecommendedModels()
    }
}
