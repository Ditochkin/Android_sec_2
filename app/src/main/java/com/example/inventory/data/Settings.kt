package com.example.inventory.data

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.inventory.g_masterKey

object Settings {
    private lateinit var m_sharedPreferences: SharedPreferences

    private const val NAME_KEY = "supplier_name_key"
    private const val PHONE_KEY = "supplier_phone_key"
    private const val EMAIL_KEY = "supplier_email_key"
    private const val DEFAULT_FIELDS_KEY = "default_fields_key"
    private const val SENSITIVE_DATA_KEY = "sensitive_data_key"
    private const val SHARE_KEY = "share_key"

    fun init(context: Application) {
        val masterKeySettings = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        g_masterKey = masterKeySettings

        m_sharedPreferences = EncryptedSharedPreferences.create(
            context,
            "PreferencesFilename",
            masterKeySettings,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    var defaultShipperName: String
        get() = m_sharedPreferences.getString(NAME_KEY, "")!!
        set(value) {
            m_sharedPreferences.edit()
                .putString(NAME_KEY, value)
                .apply()
        }

    var defaultShipperEmail: String
        get() = m_sharedPreferences.getString(EMAIL_KEY, "")!!
        set(value) {
            m_sharedPreferences.edit()
                .putString(EMAIL_KEY, value)
                .apply()
        }

    var defaultShipperPhone: String
        get() = m_sharedPreferences.getString(PHONE_KEY, "")!!
        set(value) {
            m_sharedPreferences.edit()
                .putString(PHONE_KEY, value)
                .apply()
        }

    var enableDefaultFields: Boolean
        get() = m_sharedPreferences.getBoolean(DEFAULT_FIELDS_KEY, false)
        set(value) {
            m_sharedPreferences.edit()
                .putBoolean(DEFAULT_FIELDS_KEY, value)
                .apply()
        }

    var hideSensitiveData: Boolean
        get() = m_sharedPreferences.getBoolean(SENSITIVE_DATA_KEY, false)
        set(value) {
            m_sharedPreferences.edit()
                .putBoolean(SENSITIVE_DATA_KEY, value)
                .apply()
        }

    var enableSharing: Boolean
        get() = m_sharedPreferences.getBoolean(SHARE_KEY, false)
        set(value) {
            m_sharedPreferences.edit()
                .putBoolean(SHARE_KEY, value)
                .apply()
        }
}