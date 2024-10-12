package com.example.pregunta4_login.utils

import org.json.JSONObject

fun extractMessage(responseBody: String?, isSuccess: Boolean): String {
    return try {
        val jsonObject = JSONObject(responseBody ?: "")
        if (isSuccess) {
            jsonObject.getString("Mensaje")
        } else {
            val errorObject = jsonObject.getJSONObject("error")
            val errorMessages = StringBuilder()
            errorObject.keys().forEach { key ->
                val messages = errorObject.getJSONArray(key)
                for (i in 0 until messages.length()) {
                    errorMessages.append("$key: ${messages.getString(i)}\n")
                }
            }
            errorMessages.toString().trim()
        }
    } catch (e: Exception) {
        if (isSuccess) "Operación completada con éxito" else "Ocurrió un error inesperado"
    }
}
