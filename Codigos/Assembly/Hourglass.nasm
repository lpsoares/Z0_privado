// Luciano Soares
// File name: Hourglass.asm

// Desenha uma ampulheta (dois triangulo invertidos um em cima do outro) 
// na tela quando uma tecla é pressionada.

// Put your code here.
@0
M = 0
@1
M = -1
@2 
M = 0
@15
D = A
@3
M = D
D = M
(tag)
@SCREEN
D = D - 1
A = A + D
M = -1 
@tag
D;JGT
@15
D = A
@0
M = D
(tag2)
@320
D = A
@1
M = M + D
D = M
@SCREEN
D = D + A
@0
D = D + M
A = D
M = - 1
@2
M = D
@0
M = M - 1
D = M
@tag2
D;JGT
@3
M = M - 1
(tag3)
@SCREEN
D = D - 1
@2
D = D + M
A = A + D
M = -1 
@3
M = M -1
D = M
@tag3
D;JGT
@15
D = A
@1
M = D
@2 
M = 0
@15
D = A
@0
M = D
(tag4)
@320
D = A
@1
M = M + D
D = M
@SCREEN
D = D + A
@0
D = D - M
A = D
M = - 1
@2
M = D
@0
M = M - 1
D = M
@tag4
D;JGT