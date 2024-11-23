package com.example.pregunta4_login.utils

import android.content.Context
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

private const val TAG = "TokenStorage"
private const val SECURE_PREFS_FILE = "secure_app_prefs"
private const val FALLBACK_PREFS_FILE = "fallback_prefs"
private const val TOKEN_KEY = "auth_token"

fun saveTokenSecurely(context: Context, token: String) {
    try {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPreferences = EncryptedSharedPreferences.create(
            SECURE_PREFS_FILE,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        sharedPreferences.edit().apply {
            putString(TOKEN_KEY, token)
            apply()
        }
        Log.d(TAG, "Token guardado de forma segura")
    } catch (e: Exception) {
        Log.e(TAG, "Error al guardar token de forma segura, usando fallback", e)
        // Fallback a SharedPreferences normal
        context.getSharedPreferences(FALLBACK_PREFS_FILE, Context.MODE_PRIVATE)
            .edit()
            .putString(TOKEN_KEY, token)
            .apply()
    }
}

fun getTokenSecurely(context: Context): String? {
    return try {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPreferences = EncryptedSharedPreferences.create(
            SECURE_PREFS_FILE,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        sharedPreferences.getString(TOKEN_KEY, null)?.also {
            Log.d(TAG, "Token recuperado de almacenamiento seguro")
        }
    } catch (e: Exception) {
        Log.e(TAG, "Error al recuperar token seguro, intentando fallback", e)
        // Intentar recuperar del fallback
        context.getSharedPreferences(FALLBACK_PREFS_FILE, Context.MODE_PRIVATE)
            .getString(TOKEN_KEY, null)?.also {
                Log.d(TAG, "Token recuperado de fallback")
            }
    }
}

fun clearTokens(context: Context) {
    try {
        // Limpiar EncryptedSharedPreferences
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val securePrefs = EncryptedSharedPreferences.create(
            SECURE_PREFS_FILE,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        securePrefs.edit().clear().apply()
    } catch (e: Exception) {
        Log.e(TAG, "Error al limpiar tokens seguros", e)
    }

    // Limpiar fallback
    context.getSharedPreferences(FALLBACK_PREFS_FILE, Context.MODE_PRIVATE)
        .edit()
        .clear()
        .apply()

    Log.d(TAG, "Tokens limpiados")
}
