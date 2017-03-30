; Div.nasm

; Divide R0 por R1 e armazena o resultado em R2.
; (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
; divisao para numeros inteiros positivos

leaw $R2,%A
movw $0, (%A)
LOOP:
leaw $R1,%A
movw (%A), %D
leaw $END,%A
je
leaw $0,%A
movw (%A), %D
leaw $1,%A
subw %D,(%A),%D
leaw $END,%A
jl
leaw $0,%A
movw (%A), %D
leaw $1,%A
movw (%A), %D
leaw $0,%A
subw (%A),%D,(%A)
leaw $END,%A
jl
leaw $R2,%A
inc (%A)
movw (%A), %D
leaw $LOOP,%A
jg
END:
leaw $END,%A
movw %A, %D
jmp