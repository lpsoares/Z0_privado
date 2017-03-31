; Factorial.nasm

; Calcula o fatorial do número em R0 e armazena o valor em R1.


leaw $R1,%A ; Limpa a memória
movw $0, (%A)

; Fatorial de 0
leaw $0,%A 
movw (%A),%D
leaw $1,%A 
movw %A,(%A)
leaw $END,%A 
je


; Fatorial de 1
leaw $0,%A 
movw (%A),%D
leaw $1,%A 
movw %A,(%A)
leaw $END,%A 
je


leaw $0,%A
subw (%A),$1,%D
leaw $3,%A 
subw %D,$1,(%A) ; Contador loop externo
leaw $2,%A 
movw %D,(%A) ; Contador Loop interno
leaw $4,%A 
addw %D,$1,(%A) ;  valor à ser somado
leaw $0,%A 
movw %A,%D
leaw $1,%A
movw %D,(%A)


LOOP:
; LOOP INTERNO
leaw $4,%A
movw (%A),%D
leaw $1,%A
addw (%A),%D,(%A)
leaw $2,%A
dec (%A)
movw (%A),%D
leaw $LOOP,%A
jg

; LOOP EXTERNO
leaw $1,%A
movw (%A),%D
leaw $4,%A
movw %D,(%A)
leaw $3,%A
decw (%A)
movw (%A),%D
leaw $2,%A
movw %D,(%A)
leaw $LOOP,%A
jg

END:
leaw $END,%A
jmp ; Loop