package com.sapreme.dailyrank.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import com.sapreme.dailyrank.data.model.GameResult

object GameColor{
    data class Palette(
        val title: Color,
        val positive: Color,
        val negative: Color
    )

    object Wordle{
        val Green = Color(0xff6ca965)
        val Yellow = Color(0xffc8b653)
    }

    object Connections{
        val Yellow = Color(0xfff9df6d)
        val Green = Color(0xffa0c35a)
        val Blue = Color(0xffb0c4ef)
        val Purple = Color(0xffba81c5)
    }

    object Strands{
        val Cyan = Color(0xffaae5f2)
        val Yellow = Color(0xffefc929)
    }

    object Mini{
        val Blue = Color(0xff6493e6)
    }

}

fun GameResult.Type.colorPalette(): GameColor.Palette = when (this) {
    GameResult.Type.WORDLE -> GameColor.Palette(
        title = GameColor.Wordle.Green,
        positive = GameColor.Wordle.Green,
        negative = GameColor.Wordle.Yellow
    )
    GameResult.Type.CONNECTIONS -> GameColor.Palette(
        title = GameColor.Connections.Purple,
        positive = GameColor.Connections.Green,
        negative = GameColor.Connections.Yellow
    )
    GameResult.Type.STRANDS -> GameColor.Palette(
        title = GameColor.Strands.Cyan,
        positive = GameColor.Strands.Cyan,
        negative = GameColor.Strands.Yellow
    )
    GameResult.Type.MINI -> GameColor.Palette(
        title = GameColor.Mini.Blue,
        positive = GameColor.Mini.Blue,
        negative = Color.Black
    )
}