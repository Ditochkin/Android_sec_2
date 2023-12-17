package com.example.inventory.ui.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsViewModel: ViewModel() {
    fun initUiState() {
        _uiState.value = SettingsUiState(
            shipperName = Settings.defaultShipperName,
            shipperEmail = Settings.defaultShipperEmail,
            shipperPhone = Settings.defaultShipperPhone,
            enableDefaultFields = Settings.enableDefaultFields,
            hideSensitiveData = Settings.hideSensitiveData,
            disableSharing = Settings.disableSharing,
        )
    }

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun onNameChange(value: String) {
        _uiState.value = _uiState.value.copy(shipperName = value)
    }

    fun onPhoneChange(value: String) {
        _uiState.value = _uiState.value.copy(shipperPhone = value)
    }

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(shipperEmail = value)
    }

    fun onEnableDefaultFieldsChange(value: Boolean) {
        _uiState.value = _uiState.value.copy(enableDefaultFields = value)
    }

    fun onHideSensitiveDataChange(value: Boolean) {
        _uiState.value = _uiState.value.copy(hideSensitiveData = value)
    }

    fun onDisableSharingChange(value: Boolean) {
        _uiState.value = _uiState.value.copy(disableSharing = value)
    }

    fun save() {
        Settings.defaultShipperName = uiState.value.shipperName
        Settings.defaultShipperPhone = uiState.value.shipperPhone
        Settings.defaultShipperEmail = uiState.value.shipperEmail
        Settings.enableDefaultFields = uiState.value.enableDefaultFields
        Settings.hideSensitiveData = uiState.value.hideSensitiveData
        Settings.disableSharing = uiState.value.disableSharing
    }
}

data class SettingsUiState(
    val shipperName: String = "",
    val shipperPhone: String = "",
    val shipperEmail: String = "",
    val enableDefaultFields: Boolean = false,
    val hideSensitiveData: Boolean = false,
    val disableSharing: Boolean = false,
)