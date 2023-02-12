package com.alfikri.rizky.core.data.local.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.securepreferences.SecurePreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version SessionManager, v 0.1 12/26/2022 8:09 AM by Rizky Alfikri Rachmat
 */
@Singleton
class SessionManager @Inject constructor(private val context: Context) {
    private val pref: SharedPreferences by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val spec = KeyGenParameterSpec.Builder(
                MasterKey.DEFAULT_MASTER_KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
            val masterKey = MasterKey.Builder(context)
                .setKeyGenParameterSpec(spec)
                .build()
            EncryptedSharedPreferences
                .create(
                    context,
                    "Session",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
        } else {
            SecurePreferences(context, "alfikri_secret", "Session")
        }
    }

    fun logout() {
        pref.edit().clear().apply()
    }

    fun isLogin(): Boolean = pref.getString(KEY_TOKEN, "").isNullOrEmpty().not()

    fun saveToPreference(key: String, value: String) =  pref.edit().putString(key, value).commit()

    fun getFromPreference(key: String) = pref.getString(key, "")

    companion object {
        const val KEY_USERNAME = "username"
        const val KEY_EMAIL = "email"
        const val KEY_TOKEN = "token"
    }
}