; Abs.nasm

; Copia o valor de RAM[1] para RAM[0] deixando o valor sempre positivo.


   leaw $R1,%A
   movw (%A),%D
   leaw $NEGA,%A
   jl
   nop
   leaw $R0,%A
   movw %D,(%A)
   leaw $END,%A
   jmp
   nop
NEGA:
   negw %D
   leaw $R0,%A
   movw %D,(%A)
END:
   leaw $END,%A
   jmp
   nop
