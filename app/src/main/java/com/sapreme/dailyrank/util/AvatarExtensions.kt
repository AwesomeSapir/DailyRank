package com.sapreme.dailyrank.util

import com.sapreme.dailyrank.data.model.Player

private const val DEFAULT_STYLE = "bottts"

fun Player.avatarUrl(): String = avatarUrl(this.uid)

fun avatarUrl(seed: String, style: String = DEFAULT_STYLE): String = "https://api.dicebear.com/9.x/$style/svg?seed=$seed"