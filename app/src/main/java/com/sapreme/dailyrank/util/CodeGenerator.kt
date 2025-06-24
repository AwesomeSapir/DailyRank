package com.sapreme.dailyrank.util

import java.security.SecureRandom

object CodeGenerator {

    const val UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    const val LOWERCASE = "abcdefghijklmnopqrstuvwxyz"
    const val DIGITS = "0123456789"
    private val rnd = SecureRandom()

    fun newCode(alphabet: String = (UPPERCASE + DIGITS), length: Int): String =
        buildString(length) {
            repeat(length) { append(alphabet[rnd.nextInt(alphabet.length)]) }
        }

}