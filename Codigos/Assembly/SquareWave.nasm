; Arquivo: SquareWave.nasm
; Curso: Elementos de Sistemas
; Criado por: Luciano Soares
; Data: 27/03/2017

; desenha três linhas
; centraliza na tela

leaw $4101,%A
movw %A,%D
leaw $SCREEN,%A
addw %A,%D,%D

; primeiro traco
movw %D,%A
movw $-1,(%A)

; segunda traco
incw %D

leaw $R1,%A
movw %D,(%A)

; contador
leaw $16,%A
movw %A,%D
leaw $COUNTER,%A
movw %D,(%A)

LOOP:

leaw $R1,%A
movw (%A),%A
movw $1,(%A)

leaw $32,%A
movw %A,%D

leaw $R1,%A
movw (%A),%A

addw %A,%D,%D
leaw $R1,%A
movw %D,(%A)

leaw $COUNTER,%A
movw (%A),%D
decw %D
movw %D,(%A)

leaw $LOOP,%A
jne
nop

; terceiro traco
leaw $R1,%A
movw (%A),%A
movw $-1,(%A)

; loop infinito para parar
END:
leaw $END,%A
jmp
nop
