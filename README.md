Tetris em Kotlin com Jetpack Compose

Este projeto é uma implementação do clássico jogo Tetris, desenvolvido em Kotlin usando Jetpack Compose. O objetivo é recriar a experiência do Tetris, oferecendo funcionalidades como movimento das peças, detecção de colisões, remoção de linhas completas, e níveis de dificuldade crescente.

Funcionalidades
Movimento das Peças: As peças podem ser movidas para os lados e para baixo, e podem ser rotacionadas.
Detecção de Colisões: As peças colidem corretamente com as bordas e com outras peças já fixadas.
Remoção de Linhas Completas: Linhas completas são removidas, aumentando a pontuação do jogador.
Aumento de Nível: A dificuldade aumenta à medida que mais linhas são removidas, acelerando o jogo.
Pausa e Retomada: O jogo pode ser pausado e retomado a qualquer momento.
Game Over: O jogo termina quando não há espaço para uma nova peça no tabuleiro.
Tecnologias Utilizadas
Kotlin: Linguagem principal utilizada no desenvolvimento.
Jetpack Compose: Framework para construção da interface gráfica.
Coroutines: Utilizadas para controlar o fluxo do jogo e as animações.
Estrutura do Código
View: Contém a lógica de renderização e interação com o usuário.
Model: Define os tipos de peças e suas formas, além de gerenciar o estado do tabuleiro e das peças.
Temas: Contém as definições de cores e temas utilizados na interface do jogo.
