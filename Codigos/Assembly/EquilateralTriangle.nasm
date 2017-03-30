// Luciano Soares
// File name: EquilateralTriangle.asm

// Desenha um triângulo equilátero na tela quando uma tecla é pressionada.

(LOOP) 
 
 @KBD
 D = M 
 @LOOP
 D,JEQ


//LINHA1

@21
D=A
@0
M=D

(LOOP1) 
@0
D=M
@SCREEN
A=A+D
M=-1

@0
M=M-1

@LOOP1
D,JGT

//LINHA2

@21
D=A
@0
M=D

(LOOP2)

@0
D=M

@16896 	//SCREEN
A=A+D
M=-1

@0
M=M-1

@LOOP2
D,JGT

//LINHA3

@19
D=A
@0
M=D

(LOOP3)

@0
D=M

@17377 	//SCREEN
A=A+D
M=-1

@0
M=M-1

@LOOP3
D,JGT

//LINHA4

@17
D=A
@0
M=D

(LOOP4)

@0
D=M

@17890 	//SCREEN
A=A+D
M=-1

@0
M=M-1

@LOOP4
D,JGT

//LINHA5

@15
D=A
@0
M=D

(LOOP5)

@0
D=M

@18435 	//SCREEN
A=A+D
M=-1

@0
M=M-1

@LOOP5
D,JGT

//LINHA6

@15
D=A
@0
M=D

(LOOP6)

@0
D=M

@18947 	//SCREEN
A=A+D
M=-1

@0
M=M-1

@LOOP6
D,JGT

//LINHA7

@13
D=A
@0
M=D

(LOOP7)

@0
D=M

@19460 	//SCREEN
A=A+D
M=-1

@0
M=M-1

@LOOP7
D,JGT

//LINHA8

@13
D=A
@0
M=D

(LOOP8)

@0
D=M

@19972 	//SCREEN
A=A+D
M=-1

@0
M=M-1

@LOOP8
D,JGT

//LINHA9

@11
D=A
@0
M=D

(LOOP9)

@0
D=M

@20485 	//SCREEN
A=A+D
M=-1

@0
M=M-1

@LOOP9
D,JGT

//LINHA10

@9
D=A
@0
M=D

(LOOP10)

@0
D=M

@20998	//SCREEN
A=A+D
M=-1

@0
M=M-1

@LOOP10
D,JGT

//LINHA11

@9
D=A
@0
M=D

(LOOP11)

@0
D=M

@21510 	//SCREEN
A=A+D
M=-1

@0
M=M-1

@LOOP11
D,JGT

//LINHA12

@7
D=A
@0
M=D

(LOOP12)

@0
D=M

@22023 	//SCREEN
A=A+D
M=-1

@0
M=M-1

@LOOP12
D,JGT

//LINHA13

@7
D=A
@0
M=D

(LOOP13)

@0
D=M

@22535 	//SCREEN
A=A+D
M=-1

@0
M=M-1

@LOOP13
D,JGT

//LINHA14

@5
D=A
@0
M=D

(LOOP14)

@0
D=M

@23048	//SCREEN
A=A+D
M=-1

@0
M=M-1

@LOOP14
D,JGT

//LINHA15

@3
D=A
@0
M=D

(LOOP15)

@0
D=M

@23561 	//SCREEN
A=A+D
M=-1

@0
M=M-1

@LOOP15
D,JGT

//LINHA16

@24074 //SCREEN
M=-1
@24075
M=-1

(END)
@END
0,JEQ
