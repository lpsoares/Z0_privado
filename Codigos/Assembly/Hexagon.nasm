; Luciano Soares
; File name: Hexagon.asm

; Desenha um hezagono na tela quando uma tecla é pressionada.


; desenha quadrado
; centraliza na tela
leaw $4112,%A
movw %A,%D
leaw $SCREEN,%A
addw %A,%D,%D

; onde desenhar
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
movw $-1,(%A)

leaw $32,%A
movw %A,%D

leaw $R1,%A
movw (%A),%A

addw %A,%D,%D
leaw $R1,%A
movw %D,(%A)

leaw $COUNTER,%A
decw (%A)
movw (%A),%D

leaw $LOOP,%A
jne

; loop infinito para parar
END:
leaw $END,%A
jmp
