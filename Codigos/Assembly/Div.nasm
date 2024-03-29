; Arquivo: Div.nasm
; Curso: Elementos de Sistemas
; Criado por: Luciano Soares
; Data: 27/03/2017

; Divide R0 por R1 e armazena o resultado em R2.
; (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
; divisao para numeros inteiros positivos

leaw $R2,%A
movw $0, (%A)

LOOP:

; subtrai R0 de R1
leaw $R1,%A
movw (%A), %D
leaw $R0,%A

subw (%A),%D,%D
movw %D,(%A)

; incrementa R2
leaw $R2,%A
addw (%A),$1,%D
movw %D,(%A)

; testa se R0 >= R1
leaw $R1,%A
movw (%A), %D
leaw $R0,%A
subw (%A),%D,%D
leaw $LOOP,%A
jge
nop

; loop infinito
END:
leaw $END,%A
jmp
nop
