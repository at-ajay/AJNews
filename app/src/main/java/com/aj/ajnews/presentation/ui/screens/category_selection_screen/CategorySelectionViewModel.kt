package com.aj.ajnews.presentation.ui.screens.category_selection_screen

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aj.ajnews.data.local.datastore.DataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.N)
@HiltViewModel
class CategorySelectionViewModel @Inject constructor(
    private val context: Application,
    private val dataStore: DataStore
): ViewModel() {

    init {
        saveCountryCode()
    }

    var categories = mutableListOf(
        CategoryItem(label = "Business", apiName = "business"),
        CategoryItem(label = "Entertainment", apiName = "entertainment"),
        CategoryItem(label = "General", apiName = "general"),
        CategoryItem(label = "Health", apiName = "health"),
        CategoryItem(label = "Science", apiName = "science"),
        CategoryItem(label = "Sports", apiName = "sports"),
        CategoryItem(label = "Technology", apiName = "technology"),
    )
        private set

    private var isSingleCategorySelected by mutableStateOf(false)

    var showErrorMsg by mutableStateOf(false)
        private set

    private val _uiEvents = Channel<CategorySelectionUIEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()

    fun onEvent(event: CategorySelectionEvent) {
        when(event) {
            is CategorySelectionEvent.OnChipSelected -> {
                categories[event.chipIndex].isSelected.value = !categories[event.chipIndex].isSelected.value
            }

            is CategorySelectionEvent.OnSavePrefsClicked -> {
                val selectedCategories: MutableSet<String> = mutableSetOf()

                for (item: CategoryItem in categories) {
                    if (item.isSelected.value) {
                        isSingleCategorySelected = true
                        selectedCategories += selectedCategories.plus(item.apiName)
                    }
                }

                showErrorMsg = !isSingleCategorySelected

                if (isSingleCategorySelected) {
                    viewModelScope.launch {
                        dataStore.saveCategoryPreference(selectedCategories)
                        sendUIEvents(CategorySelectionUIEvent.OnCategorySavingProcessed)
                    }
                }

            }
        }
    }

    private fun sendUIEvents(event: CategorySelectionUIEvent) = viewModelScope.launch {
        _uiEvents.send(event)
    }

    /** Function to save country code to datastore **/
    private fun saveCountryCode() = viewModelScope.launch {
        val localeInfo = context.resources.configuration.locales.toLanguageTags()
        val countryCode = localeInfo.substring(startIndex = 3, endIndex = 5).lowercase()
        dataStore.saveCountryCode(countryCode)
    }

}