package com.sapreme.dailyrank.ui.theme

import androidx.compose.ui.graphics.Color
import com.sapreme.dailyrank.data.model.GameResult

object GameColor{
    data class Palette(
        val title: Color
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
        val Cyan = Color(0xff83CEE8)
        val Yellow = Color(0xffefc929)
        val DarkYellow = Color(0xffD5AF10)
    }

    object Mini{
        val Blue = Color(0xff6493e6)
        val Black = Color.Black
    }

    object Misc{
        val Red = Color(0xffC35A6C)
    }

}

fun GameResult.Type.colorPalette(): GameColor.Palette = when (this) {
    GameResult.Type.WORDLE -> GameColor.Palette(
        title = GameColor.Wordle.Green
    )
    GameResult.Type.CONNECTIONS -> GameColor.Palette(
        title = GameColor.Connections.Purple
    )
    GameResult.Type.STRANDS -> GameColor.Palette(
        title = GameColor.Strands.Cyan
    )
    GameResult.Type.MINI -> GameColor.Palette(
        title = GameColor.Mini.Black
    )
}