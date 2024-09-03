package com.Caetano.tetris.View

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Caetano.tetris.Model.PecaTetris
import com.Caetano.tetris.Model.TiposDePeca
import com.Caetano.tetris.ui.theme.BLACK80
import com.Caetano.tetris.ui.theme.WHITE
import com.Caetano.tetris.ui.theme.YELLOW1
import kotlinx.coroutines.delay
import kotlin.random.Random

const val LARGURA = 301
const val ALTURA = 601
const val TAMANHO_BLOCO = 31
const val LARGURA_TABULEIRO = LARGURA / TAMANHO_BLOCO
const val ALTURA_TABULEIRO = ALTURA / TAMANHO_BLOCO
const val BORDA_TABULEIRO = 2f

@Composable
fun Tetris() {
    var tabuleiro by remember { mutableStateOf(Array(ALTURA_TABULEIRO) { Array(LARGURA_TABULEIRO) { Color.Black } }) }
    var peca by remember { mutableStateOf(gerarNovaPeca()) }
    var pontuacao by remember { mutableStateOf(0) }
    var level by remember { mutableStateOf(1) }
    var gameOver by remember { mutableStateOf(false) }
    var linhasRemovidas by remember { mutableStateOf(0) }
    var paused by remember { mutableStateOf(false) }

    fun reiniciarJogo() {
        tabuleiro = Array(ALTURA_TABULEIRO) { Array(LARGURA_TABULEIRO) { Color.Black } }
        peca = gerarNovaPeca()
        pontuacao = 0
        level = 1
        gameOver = false
        linhasRemovidas = 0
        paused = false
    }

    fun pausarOuRetomar() {
        paused = !paused
    }

    LaunchedEffect(key1 = gameOver) {
        if (gameOver) return@LaunchedEffect
        while (!gameOver) {
            if (!paused) {
                delay(1000L / level)
                if (!moverPeca(tabuleiro, peca, 0, 1)) {
                    fixarPeca(tabuleiro, peca)
                    val linhasRemovidasAgora = removerLinhasCompletas(tabuleiro) { linhas ->
                        pontuacao += linhas * 100
                        linhasRemovidas += linhas
                        if (linhasRemovidas >= level * 10) {
                            level++
                            linhasRemovidas = 0
                        }
                    }
                    val novaPeca = gerarNovaPeca()
                    if (!moverPeca(tabuleiro, novaPeca, 0, 0)) {
                        gameOver = true // Aciona o Game Over se não houver espaço para a nova peça
                    } else {
                        peca = novaPeca
                    }
                } else {
                    peca = peca.copy(y = peca.y + 1)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BLACK80),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Pontuação: $pontuacao",
            fontSize = 19.sp,
            color = YELLOW1,
            modifier = Modifier.padding(21.dp)
        )
        Text(
            text = "Lv: $level",
            fontSize = 19.sp,
            color = YELLOW1,
            modifier = Modifier.padding(11.dp)
        )

        Box(
            modifier = Modifier
                .width(300.dp)
                .height(600.dp)
                .border(2.dp, YELLOW1)
                .background(Color.Blue)
                .pointerInput(Unit) {
                    detectDragGestures { _, dragAmount ->
                        if (!gameOver && !paused) {
                            val dx = (dragAmount.x / TAMANHO_BLOCO).toInt().coerceIn(-1, 1)
                            val dy = (dragAmount.y / TAMANHO_BLOCO).toInt().coerceIn(0, 1)

                            if (dx != 0) {
                                if (moverPeca(tabuleiro, peca, dx, 0)) peca = peca.copy(x = peca.x + dx)
                            }

                            if (dy != 0) {
                                if (moverPeca(tabuleiro, peca, 0, dy)) peca = peca.copy(y = peca.y + dy)
                            }
                        }
                    }
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasLargura = size.width
                val canvasAltura = size.height
                val tamanhoBloco = (canvasLargura / LARGURA_TABULEIRO).coerceAtMost(canvasAltura / ALTURA_TABULEIRO)

                val larguraTabuleiro = (canvasLargura / tamanhoBloco).toInt()
                val alturaTabuleiro = (canvasAltura / tamanhoBloco).toInt()

                drawRect(color = Color.Black, size = Size(canvasLargura, canvasAltura))

                for (x in 0 until larguraTabuleiro) {
                    drawLine(
                        color = WHITE,
                        start = Offset(x * tamanhoBloco, 0f),
                        end = Offset(x * tamanhoBloco, canvasAltura)
                    )
                }

                for (y in 0 until alturaTabuleiro) {
                    drawLine(
                        color = WHITE,
                        start = Offset(0f, y * tamanhoBloco),
                        end = Offset(canvasLargura, y * tamanhoBloco)
                    )
                }

                // Desenha as peças fixadas
                tabuleiro.forEachIndexed { y, linha ->
                    linha.forEachIndexed { x, cor ->
                        if (cor != Color.Black) {
                            drawRect(
                                color = cor,
                                topLeft = Offset(
                                    x * tamanhoBloco + BORDA_TABULEIRO / 2,
                                    y * tamanhoBloco + BORDA_TABULEIRO / 2
                                ),
                                size = Size(tamanhoBloco - BORDA_TABULEIRO, tamanhoBloco - BORDA_TABULEIRO)
                            )
                        }
                    }
                }

                // Desenha a peça atual
                val formaDaPeca = peca.tipo.shapes[peca.indexRotacao]
                formaDaPeca.forEachIndexed { i, row ->
                    row.forEachIndexed { j, block ->
                        if (block == 1) {
                            drawRect(
                                color = peca.tipo.cor,
                                topLeft = Offset(
                                    (peca.x + j) * tamanhoBloco + BORDA_TABULEIRO / 2,
                                    (peca.y + i) * tamanhoBloco + BORDA_TABULEIRO / 2
                                ),
                                size = Size(tamanhoBloco - BORDA_TABULEIRO, tamanhoBloco - BORDA_TABULEIRO)
                            )
                        }
                    }
                }
            }
        }

        if (gameOver) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Game Over",
                    fontSize = 32.sp,
                    color = Color.Yellow
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { reiniciarJogo() },
                    colors = ButtonDefaults.buttonColors(containerColor = YELLOW1)
                ) {
                    Text("Reiniciar", color = Color.Black)
                }
            }
        } else {
            Button(
                onClick = { pausarOuRetomar() },
                colors = ButtonDefaults.buttonColors(containerColor = YELLOW1),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = if (paused) "Retomar" else "Pausar", color = Color.Black)
            }
        }
    }
}

private fun gerarNovaPeca(): PecaTetris {
    val tipopeca = TiposDePeca.entries[Random.nextInt(TiposDePeca.entries.size)]
    return PecaTetris(tipo = tipopeca, x = LARGURA_TABULEIRO / 2 - tipopeca.shapes[0][0].size / 2, y = 0)
}

private fun moverPeca(tabuleiro: Array<Array<Color>>, peca: PecaTetris, dx: Int, dy: Int): Boolean {
    peca.tipo.shapes[peca.indexRotacao].forEachIndexed { i, row ->
        row.forEachIndexed { j, block ->
            if (block == 1) {
                val novoX = peca.x + j + dx
                val novoY = peca.y + i + dy
                if (novoX < 0 || novoX >= LARGURA_TABULEIRO || novoY >= ALTURA_TABULEIRO || tabuleiro[novoY][novoX] != Color.Black) {
                    return false
                }
            }
        }
    }
    return true
}

private fun fixarPeca(tabuleiro: Array<Array<Color>>, peca: PecaTetris) {
    peca.tipo.shapes[peca.indexRotacao].forEachIndexed { i, row ->
        row.forEachIndexed { j, block ->
            if (block == 1) {
                tabuleiro[peca.y + i][peca.x + j] = peca.tipo.cor
            }
        }
    }
}

private fun removerLinhasCompletas(tabuleiro: Array<Array<Color>>, onLinhasRemovidas: (Int) -> Unit): Int {
    val linhasParaRemover = mutableListOf<Int>()
    var linhasCompletas = 0

    // Identificar linhas completas
    for (i in tabuleiro.indices) {
        if (tabuleiro[i].all { it != Color.Black }) {
            linhasParaRemover.add(i)
        }
    }

    // Remover linhas completas
    linhasParaRemover.forEach { i ->
        linhasCompletas++
        for (j in i downTo 1) {
            tabuleiro[j] = tabuleiro[j - 1].copyOf()
        }
        tabuleiro[0].fill(Color.Black)
    }

    onLinhasRemovidas(linhasCompletas)

    return linhasCompletas
}
