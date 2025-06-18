package com.sapreme.dailyrank.ui.theme

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Spacing{
    val s:  Dp = 4.dp
    val m:   Dp = 8.dp
    val l:   Dp = 16.dp
    val xl: Dp = 32.dp
}

fun Modifier.sizeXS() = this.then(Modifier.size(Spacing.s))
fun Modifier.sizeS()  = this.then(Modifier.size(Spacing.m))
fun Modifier.sizeM()  = this.then(Modifier.size(Spacing.l))
fun Modifier.sizeL()  = this.then(Modifier.size(Spacing.xl))
fun Modifier.sizeXL() = this.then(Modifier.size(Spacing.xl))