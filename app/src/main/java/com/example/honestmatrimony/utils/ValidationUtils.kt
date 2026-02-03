package com.example.honestmatrimony.utils

object ValidationUtils {

    fun isValidEmail(email: String): Boolean {
        val regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return regex.matches(email)
    }

    fun isValidMobile(mobile: String): Boolean {
        val indian = "^[6-9][0-9]{9}$".toRegex()
        val global = "^\\+[0-9]{7,15}$".toRegex()
        return indian.matches(mobile) || global.matches(mobile)
    }

    fun isValidUserId(userId: String): Boolean {
        val regex = "^[A-Za-z0-9@#_.-]{6,20}$".toRegex()
        return regex.matches(userId)
    }

    // UserID OR Email OR Mobile
    fun isValidLoginId(input: String): Boolean {
        return when {
            input.contains("@") -> isValidEmail(input)
            input.startsWith("+") || input.firstOrNull()?.isDigit() == true ->
                isValidMobile(input)
            else -> isValidUserId(input)
        }
    }
    // 🔹 THIS IS WHAT LOGIN USES
    fun isValidLoginInput(input: String): Boolean {
        return isValidEmail(input) ||
                isValidMobile(input) ||
                isValidUserId(input)
    }
}
