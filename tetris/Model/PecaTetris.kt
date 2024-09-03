package com.Caetano.tetris.Model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.Caetano.tetris.ui.theme.BLUE
import com.Caetano.tetris.ui.theme.BLUE1
import com.Caetano.tetris.ui.theme.GREEN
import com.Caetano.tetris.ui.theme.ORANGE
import com.Caetano.tetris.ui.theme.PURPLE
import com.Caetano.tetris.ui.theme.PURPLE1
import com.Caetano.tetris.ui.theme.RED
import com.Caetano.tetris.ui.theme.YELLOW

data class PecaTetris(
    val tipo: TiposDePeca,
    var x: Int,
    var y: Int,
    var indexRotacao: Int = 0
)

enum class TiposDePeca(
    val shapes: Array<Array<IntArray>>,
    val cor: Color
) {
    I(
        arrayOf(
            arrayOf(intArrayOf(1, 1, 1, 1)), // Horizontal
            arrayOf(intArrayOf(1), intArrayOf(1), intArrayOf(1), intArrayOf(1)) // vertical
        ), BLUE
    ),

    J(
        arrayOf(
            arrayOf(
                intArrayOf(0, 0, 1),
                intArrayOf(1, 1, 1)
            ),
            arrayOf(
                intArrayOf(1, 1, 1),
                intArrayOf(1, 0, 0)
            ),
            arrayOf(
                intArrayOf(0, 1),
                intArrayOf(0, 1),
                intArrayOf(1, 1)
            )
        ), RED
    ),

    L(arrayOf(
        arrayOf(
            intArrayOf(1, 0, 0),
            intArrayOf(1, 1, 1)),
        arrayOf(
            intArrayOf(1, 1),
            intArrayOf(1, 0),
            intArrayOf(1, 0)
        ),
        arrayOf(
            intArrayOf(1, 1, 1),
            intArrayOf(0, 0, 1)),
        arrayOf(
            intArrayOf(1,0),
            intArrayOf(1,0),
            intArrayOf(1,1)),
        arrayOf(
            intArrayOf(1,1),
            intArrayOf(0,1),
            intArrayOf(0,1)),
        arrayOf(
            intArrayOf(1,1,1),
            intArrayOf(1,0,0)),
    ), GREEN),

    O(arrayOf(
        arrayOf(
            intArrayOf(1,1),
            intArrayOf(1,1)),

    ), PURPLE),

    S(arrayOf(
        arrayOf(
            intArrayOf(0,1,1),
            intArrayOf(1,1,0)),
        arrayOf(
            intArrayOf(1,0),
            intArrayOf(1,1),
            intArrayOf(1,0)),
    ), ORANGE),

    T(arrayOf(
        arrayOf(
            intArrayOf(0,1,0),
            intArrayOf(1,1,1)),
        arrayOf(
            intArrayOf(1,1,1),
            intArrayOf(0,1,0)),
        arrayOf(
            intArrayOf(1,0),
            intArrayOf(1,1),
            intArrayOf(1,0)),
        arrayOf(
            intArrayOf(0,1),
            intArrayOf(1,1),
            intArrayOf(0,1)),
    ), YELLOW),

    Z(arrayOf(
        arrayOf(
            intArrayOf(1,1,0),
            intArrayOf(0,1,1)),
        arrayOf(
            intArrayOf(0,1,1),
            intArrayOf(1,1,0)),
        arrayOf(
            intArrayOf(0,1),
            intArrayOf(1,1),
            intArrayOf(1,0)),
        arrayOf(
            intArrayOf(1,0),
            intArrayOf(1,1),
            intArrayOf(0,1)),
    ), BLUE1),
    OO(arrayOf(
        arrayOf(
            intArrayOf(1,1,1),
            intArrayOf(1,1,1),
            intArrayOf(1,1,1)),
    ), PURPLE1),
}