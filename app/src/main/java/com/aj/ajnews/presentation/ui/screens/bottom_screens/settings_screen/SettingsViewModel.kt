package com.aj.ajnews.presentation.ui.screens.bottom_screens.settings_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aj.ajnews.data.local.datastore.DataStore
import com.aj.ajnews.presentation.ui.MainActivity.Companion.isAppInDarkTheme
import com.aj.ajnews.presentation.ui.screens.category_selection_screen.CategoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStore: DataStore
) : ViewModel() {

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

    var countries = mapOf(
        "ar" to "Argentina",
        "au" to "Australia",
        "at" to "Austria",
        "be" to "Belgium",
        "br" to "Brazil",
        "gb" to "Britain",
        "bg" to "Bulgaria",
        "ca" to "Canada",
        "cn" to "China",
        "co" to "Colombia",
        "cu" to "Cuba",
        "cz" to "Czechia",
        "eg" to "Egypt",
        "fr" to "France",
        "de" to "Germany",
        "gr" to "Greece",
        "hk" to "Hong Kong",
        "hu" to "Hungary",
        "in" to "India",
        "id" to "Indonesia",
        "ie" to "Ireland",
        "il" to "Israel",
        "it" to "Italy",
        "jp" to "Japan",
        "kr" to "Korea",
        "lv" to "Latvia",
        "lt" to "Lithuania",
        "my" to "Malaysia",
        "mx" to "Mexico",
        "ma" to "Morocco",
        "nl" to "Netherlands",
        "nz" to "New Zealand",
        "ng" to "Nigeria",
        "no" to "Norway",
        "ph" to "Philippines",
        "pl" to "Poland",
        "pt" to "Portugal",
        "ro" to "Romania",
        "ru" to "Russia",
        "sa" to "Saudi Arabia",
        "rs" to "Serbia",
        "sg" to "Singapore",
        "sk" to "Slovakia",
        "si" to "Slovenia",
        "za" to "South Africa",
        "se" to "Sweden",
        "ch" to "Switzerland",
        "tw" to "Taiwan",
        "th" to "Thailand",
        "tr" to "Türkiye",
        "us" to "Ukraine",
        "ae" to "United Arab Emirates",
        "us" to "United States of America",
        "ve" to "Venezuela",
    )
        private set

    private var selectedCategories = mutableSetOf<String>()

    var showErrorMsg by mutableStateOf(false)
        private set

    var isExpanded by mutableStateOf(false)
    var selectedCountry by mutableStateOf("")

    init {
        viewModelScope.launch {
            selectedCategories = dataStore.getUserInterest()?.toMutableSet()!!
            selectedCountry = countries[dataStore.getCountryCode()!!].toString()

            selectedCategories.forEach { categoryName ->
                categories.forEach { category ->
                    if (categoryName == category.apiName) {
                        category.isSelected.value = true
                    }
                }
            }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.OnThemeStateChanged -> viewModelScope.launch {
                isAppInDarkTheme = event.isEnabled
                dataStore.saveThemePreference(event.isEnabled)
            }

            is SettingsEvent.OnChipSelected -> viewModelScope.launch {
                val index = event.index

                categories[index].isSelected.value = !categories[index].isSelected.value
                if (categories[index].isSelected.value) {
                    selectedCategories.add(categories[index].apiName)
                } else {
                    selectedCategories.remove(categories[index].apiName)
                }

                if (selectedCategories.isNotEmpty()) {
                    dataStore.saveCategoryPreference(selectedCategories.toSet())
                } else {
                    categories[index].isSelected.value = !categories[index].isSelected.value
                    selectedCategories.add(categories[index].apiName)
                    showErrorMsg = true
                    delay(1500)
                    showErrorMsg = false
                }
            }

            is SettingsEvent.OnCountrySelected -> viewModelScope.launch {
                selectedCountry = event.value
                dataStore.saveCountryCode(event.key)
                isExpanded = false
            }
        }
    }

}