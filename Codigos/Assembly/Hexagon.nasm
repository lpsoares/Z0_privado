; Arquivo: Hexagon.nasm
; Curso: Elementos de Sistemas
; Criado por: Luciano Soares
; Data: 27/03/2017

; Desenha um hezagono na tela quando uma tecla é pressionada.


; Desenvolvido com a ajuda da Sabrina Simao

;triangulo invertido de baixo

movw $1, %D
leaw $0,%A
movw %D,(%A)
leaw $17959,%A
movw %A,%D
leaw $1,%A
movw %D,(%A)

loop3:
leaw $0,%A
movw (%A),%D
leaw $1,%A
movw (%A),%A
notw %D
movw %D,(%A)
notw %D
leaw $0,%A
addw %D,(%A),%D
incw %D
leaw $0,%A
movw %D,(%A)
leaw $32,%A
movw %A,%D
leaw $1,%A
addw %D,(%A),(%A)
movw (%A),%D
leaw $18471,%A
subw %A,%D,%D
leaw %loop3,%A
jge
nop

;triangulo invertido de cima

movw $1, %D
leaw $0,%A
movw %D,(%A)
leaw $17895,%A
movw %A,%D
leaw $1,%A
movw %D,(%A)

loop4:
leaw $0,%A
movw (%A),%D
leaw $1,%A
movw (%A),%A
notw %D
movw %D,(%A)
notw %D
leaw $0,%A
addw %D,(%A),%D
incw %D
leaw $0,%A
movw %D,(%A)
leaw $32,%A
movw %A,%D
leaw $1,%A
subw (%A),%D,(%A)
movw (%A),%D
leaw $17383,%A
subw %A,%D,%D
leaw %loop4,%A
jle
nop

;triangulos normais

movw $1, %D
leaw $0,%A
movw %D,(%A)
leaw $17417,%A
movw %A,%D
leaw $1,%A
movw %D,(%A)

loop:
leaw $0,%A
movw (%A),%D
leaw $1,%A
movw (%A),%A
movw %D,(%A)
addw %D,(%A),%D
incw %D
leaw $0,%A
movw %D,(%A)
leaw $32,%A
movw %A,%D
leaw $1,%A
addw %D,(%A),(%A)
movw (%A),%D
leaw $17897,%A
subw %A,%D,%D
leaw %loop,%A
jge
nop

movw $1, %D
leaw $0,%A
movw %D,(%A)
leaw $18441,%A
movw %A,%D
leaw $1,%A
movw %D,(%A)

loop1:
leaw $0,%A
movw (%A),%D
leaw $1,%A
movw (%A),%A
movw %D,(%A)
addw %D,(%A),%D
incw %D
leaw $0,%A
movw %D,(%A)
leaw $32,%A
movw %A,%D
leaw $1,%A
subw (%A),%D,(%A)
movw (%A),%D
leaw $17897,%A
subw %A,%D,%D
leaw %loop1,%A
jle
nop

;quadrado

leaw $17416,%A
movw %A,%D
leaw $0,%A
movw %D,(%A)


loop2:
leaw $0,%A
movw (%A),%D
movw %D,%A
movw $-1,(%A)
leaw $32,%A
addw %A,%D,%D

leaw $0,%A
movw %D,(%A)

leaw $18440,%A
subw %A,%D,%D

leaw $loop2,%A
jge

;tampador de buraco
leaw $17927,%A
movw $-1,(%A)

