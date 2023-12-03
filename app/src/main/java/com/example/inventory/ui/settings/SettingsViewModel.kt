package com.example.inventory.ui.settings

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventory.data.Settings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SettingsUiState(
    val supplierName: String = "",
    val supplierEmail: String = "",
    val supplierPhone: String = "",

    val enableDefaultSettings: Boolean = false,
    val hideSensitiveData: Boolean = false,
    val enableDataSharing: Boolean = false,
)
class SettingsViewModel: ViewModel() {
    fun init() {
        m_uiState.value = SettingsUiState(
            supplierName = Settings.defaultShipperName,
            supplierEmail = Settings.defaultShipperEmail,
            supplierPhone = Settings.defaultShipperPhone,
            enableDefaultSettings = Settings.enableDefaultFields,
            hideSensitiveData = Settings.hideSensitiveData,
            enableDataSharing = Settings.enableSharing,
        )
    }

    private val m_uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> get() = m_uiState.asStateFlow()

    fun onNameChange(value: String) {
        m_uiState.value = m_uiState.value.copy(supplierName = value)
    }

    fun onPhoneChange(value: String) {
        m_uiState.value = m_uiState.value.copy(supplierPhone = value)
    }

    fun onEmailChange(value: String) {
        m_uiState.value = m_uiState.value.copy(supplierEmail = value)
    }

    fun onEnableDefaultSettingsChage(value: Boolean) {
        m_uiState.value = m_uiState.value.copy(enableDefaultSettings = value)
    }

    fun onHideSensitiveDataChange(value: Boolean) {
        m_uiState.value = m_uiState.value.copy(hideSensitiveData = value)
    }

    fun onDisableSharingChange(value: Boolean) {
        m_uiState.value = m_uiState.value.copy(enableDataSharing = value)
    }

    fun save() {
        Settings.defaultShipperName = uiState.value.supplierName
        Settings.defaultShipperEmail = uiState.value.supplierEmail
        Settings.defaultShipperPhone = uiState.value.supplierPhone

        Settings.enableDefaultFields = uiState.value.enableDefaultSettings
        Settings.hideSensitiveData = uiState.value.hideSensitiveData
        Settings.enableSharing = uiState.value.enableDataSharing
    }
}