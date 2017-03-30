; KolakoskiSequence.nasm

; Le a quantidade de valores da RAM[0] e grava a sequencia de números 
; da Sequencia de Kolakoski nas posições seguintes RAM[1], RAM[2], etc....
; Por exemplo: se RAM[0]=6
; RAM[1]=1, RAM[2]=2, RAM[3]=1, RAM[4]=1, RAM[5]=2, RAM[6]=1
; Maiores informações em: https://oeis.org/A000002

n=9
l=[0]*3
l[0]=1
l[1]=2
l[2]=2
for i in range(2,n):
	l+=[1+i%2]*l[i]
print(l)
