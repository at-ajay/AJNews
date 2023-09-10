package com.aj.ajnews.presentation.ui.screens.bottom_screens.settings_screen

sealed class SettingsEvent {
    data class OnThemeStateChanged(val isEnabled: Boolean) : SettingsEvent()
    data class OnChipSelected(val index: Int): SettingsEvent()
    data class OnCountrySelected(val key: String, val value: String): SettingsEvent()
}
