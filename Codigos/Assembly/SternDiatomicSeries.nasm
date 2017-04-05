; Arquivo: SternDiatomicSeries.nasm
; Curso: Elementos de Sistemas
; Criado por: Luciano Soares
; Data: 27/03/2017

; Calcula a série Stern’s Diatomic, a quantidade de elementos gerados é RAM[0]*2+1
; Os resultados são armazenados na RAM[1], RAM[2], etc....
; A série deve gerar: 1,1,2,1,3,2,3,1,4,3,5,2,5,3,4,1,5,4,7,3,8,5,7,2,7,5, etc...


leaw $R2,%A
movw %A,%D
leaw $1000,%A
movw %D,(%A)
leaw $1,%A
movw %A,%D
leaw $1001,%A
movw %D,(%A)
leaw $1,%A
movw $1,(%A)
LOOP:
leaw $1001,%A
movw (%A),%A
movw (%A),%D
leaw $1000,%A
movw (%A),%A
movw %D,(%A)

leaw $1000,%A
addw (%A),$1,%D
movw %D,(%A)

leaw $1001,%A
movw (%A),%A
movw (%A),%D
incw %A
addw %D,(%A),%D

leaw $1000,%A
movw (%A),%A
movw %D,(%A)
leaw $1000,%A
addw (%A),$1,%D
movw %D,(%A)

leaw $1001,%A
addw (%A),$1,%D
movw %D,(%A)

leaw $0,%A
subw (%A),$1,%D
movw %D,(%A)

movw (%A),%D
leaw $LOOP,%A
jg
nop

END:
leaw $END,%A
jmp
nop

