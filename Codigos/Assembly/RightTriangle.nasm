// Luciano Soares
// File name: RightTriangle.asm

// Desenha um triângulo retângulo na tela quando uma tecla é pressionada.

<<<<<<< HEAD
//@KBD // Define o registrador A para apontar para o teclado
//D = M // Coloca no registrador D o valor ASCII da tecla.
//@0
//M=D
//0,JEQ

(LOOP)//Espera tecla clicar 

@KBD // Define o registrador A para apontar para o teclado
D = M 
@LOOP
D,JEQ

@96
D=A
@1
M=D
@0
D=A
@2
M=D
@SCREEN
M=1

(LOOP1)// Faz cateto1
@32
D=A

@2
M=M+D
D=M

@SCREEN
D=D+A
A=D
M=1

@1
M=M-1
D=M

@LOOP1
D;JGT

@6
D=A
@3
M=D

(LOOP2)//Faz cateto2
@2
D=M
M=M+1

@SCREEN
D=D+A
A=D
M=-1

@3
M=M-1
D=M

@LOOP2
D,JGT


@6
D=A
@6
M=D


(LOOP3)//Faz A hipotenusa

@2
D=M

@SCREEN
D=D+A
A=D
M=1

@2
D=M
@513
D=D-A
@2
M=D

@6
M=M-1
D=M

@LOOP3
D;JGT

@5
M=D

@67
0,JEQ








































=======
// Put your code here.
>>>>>>> b4f3aabc94609011c72df047a991639457fa986a
