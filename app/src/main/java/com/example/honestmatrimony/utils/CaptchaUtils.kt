package com.example.honestmatrimony.utils

import kotlin.random.Random

data class Captcha(val question: String, val answer: String)

object CaptchaUtils {
    fun generateCaptcha(): Captcha {
        val a = Random.nextInt(1, 10)
        val b = Random.nextInt(1, 10)
        return Captcha(question = "$a + $b = ?", answer = (a + b).toString())
    }

    fun isCaptchaValid(input: String, answer: String): Boolean {
        return input.trim() == answer
    }
    // 🔹 THIS IS WHAT LOGIN & SIGNUP USE
    fun verifyCaptcha(captcha: Captcha, input: String): Boolean {
        return captcha.answer == input.trim()
    }
}
