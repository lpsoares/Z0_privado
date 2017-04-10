; Arquivo: RightTriangle.nasm
; Curso: Elementos de Sistemas
; Criado por: Luciano Soares
; Data: 27/03/2017

; Desenha um triângulo retângulo na tela quando uma tecla é pressionada.

; desenha triangulo no canto da tela
leaw $SCREEN,%A
movw %A,%D

; onde desenhar
leaw $R0,%A
movw %D,(%A)

; o que desenhar
leaw $1,%A
movw %A,%D
leaw $R1,%A
movw %D,(%A)

LOOP:

leaw $R1,%A   ; pixels
movw (%A),%D

leaw $R0,%A   ; local
movw (%A),%A

movw %D,(%A)  ; desenha

leaw $R0,%A   ; pula linha
movw (%A),%D
leaw $32,%A
addw %A,%D,%D 
leaw $R0,%A
movw %D,(%A)


leaw $R1,%A   ; carrega linha
movw (%A),%D
movw %D,%A    ; aumenta triangulo
addw %A,%D,%D
incw %D
leaw $R1,%A   ; salva nova linha do triangulo
movw %D,(%A)

leaw $LOOP,%A
jg
nop

; ultima linha
leaw $R1,%A   ; pixels
movw (%A),%D
leaw $R0,%A   ; local
movw (%A),%A
movw %D,(%A)  ; desenha

; loop infinito para parar
END:
leaw $END,%A
jmp
nop