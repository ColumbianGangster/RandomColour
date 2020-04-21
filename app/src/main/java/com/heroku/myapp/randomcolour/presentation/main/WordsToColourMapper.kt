package com.heroku.myapp.randomcolour.presentation.main

import android.graphics.Color
import com.heroku.myapp.randomcolour.domain.Mapper


class WordsToColourMapper : Mapper<List<String>, Int> {
    override fun map(from: List<String>): Int =
        if (from.isNotEmpty())
            from.first()
                .map { it.toAscii() }
                .toRgbList()
                .let { it.toColorArgb() }
        else 0
}

fun Char.toAscii() = toByte().toInt()

fun List<Int>.toRgbList() = chunked(2) {
    it[0] + if (it.size > 1) it[1] else 0
}

fun List<Int>.toColorArgb() = Color.argb(
    if (size >= 4) this[3] else 255, // for larger words, we allow alpha
    if (isNotEmpty()) this[0] else 0,
    if (size >= 2) this[1] else 0,
    if (size >= 3) this[2] else 0
)