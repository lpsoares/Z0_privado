; Arquivo: KolakoskiSequence.nasm
; Curso: Elementos de Sistemas
; Criado por: Luciano Soares
; Data: 27/03/2017

; Le a quantidade de valores da RAM[0] e grava a sequencia de números 
; da Sequencia de Kolakoski nas posições seguintes RAM[1], RAM[2], etc....
; Por exemplo: se RAM[0]=6
; RAM[1]=1, RAM[2]=2, RAM[3]=1, RAM[4]=1, RAM[5]=2, RAM[6]=1
; Maiores informações em: https://oeis.org/A000002


;migue, os alunos tem de fazer direito
;n=9
;l=[0]*3
;l[0]=1
;l[1]=2
;l[2]=2
;for i in range(2,n):
;	l+=[1+i%2]*l[i]
;print(l)



leaw $2,%A
movw %A,%D

; comeca aqui
leaw $1,%A
movw $1,(%A)

leaw $2,%A
movw %D,(%A)

leaw $3,%A
movw %D,(%A)

leaw $4,%A
movw $1,(%A)

leaw $5,%A
movw $1,(%A)

leaw $6,%A
movw %D,(%A)

leaw $7,%A
movw $1,(%A)

leaw $8,%A
movw %D,(%A)

leaw $9,%A
movw %D,(%A)

leaw $10,%A
movw $1,(%A)

leaw $11,%A
movw %D,(%A)

leaw $12,%A
movw %D,(%A)

leaw $13,%A
movw $1,(%A)

leaw $14,%A
movw $1,(%A)

leaw $15,%A
movw %D,(%A)

leaw $16,%A
movw $1,(%A)
