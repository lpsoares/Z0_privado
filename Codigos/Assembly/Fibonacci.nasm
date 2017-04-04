; Arquivo: Fibonacci.nasm
; Curso: Elementos de Sistemas
; Criado por: Luciano Soares
; Data: 27/03/2017


; Le a quantidade de valores da RAM[0] e grava a sequencia de números 
; de Fibonacci nas posições seguintes RAM[1], RAM[2], etc....
; Por exemplo: se RAM[0]=6
; RAM[1]=0, RAM[2]=1, RAM[3]=1, RAM[4]=2, RAM[5]=3, RAM[6]=5
; Maiores informações em: https://oeis.org/A000045

; Limitado a 24 iteracoes
; Só funciona se memória estiver limpa


leaw $R0,%A
subw (%A),$1,%D
leaw $END,%A
jl

leaw $R0,%A
dec %D
leaw $END,%A
jl

leaw $R0,%A
dec %D
leaw $R2,%A
movw $1,(%A)
leaw $END,%A
jl


leaw $R0,%A
dec %D
leaw $R3,%A
movw $1,(%A)
leaw $END,%A
jl


leaw $R3,%A
movw %A,%D
leaw $R1,%A
movw %D,(%A)

LOOP:
leaw $R1,%A
movw (%A),%D
movw %D,%A
movw (%A),%D
inc %A
addw %D,(%A),(%A)
dec %A
dec %A
movw (%A),%D
inc %A
inc %A
addw %D,(%A),(%A)


leaw $R0,%A
movw (%A),%D
leaw $1,%A
inc (%A)
subw (%A),%D,%D

leaw $LOOP,%A
jl


END:
leaw $1,%A
movw $0,(%A)
leaw $END,%A
jmp


