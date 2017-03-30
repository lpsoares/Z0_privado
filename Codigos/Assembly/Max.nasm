; Calcula R2 = max(R0, R1)  (R0,R1,R2 se referem a  RAM[0],RAM[1],RAM[2])
; ou seja, o maior valor que estiver, ou em R0 ou R1 sera copiado para R2

   leaw $0,%A
   movw (%A) , %D
   leaw $1,%A
   subw %D,(%A),%D
   leaw $OUTPUT_FIRST,%A
   jg
   nop
   leaw $1,%A
   movw (%A) , %D
   leaw $OUTPUT_D,%A
   jmp
   nop
OUTPUT_FIRST:
   leaw $0,%A
   movw (%A),%D
OUTPUT_D:
   leaw $2,%A
   movw %D, (%A)
INFINITE_LOOP:
   leaw $INFINITE_LOOP,%A
   jmp
   nop
