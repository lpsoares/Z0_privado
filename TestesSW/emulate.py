
import subprocess
import loadTestes

def emulate():

	nomes_testes = loadTestes.testes("testesSW/testes.txt")

	for j in nomes_testes:

		nome = j.split()

		for i in range(int(nome[1])):

			error_code = subprocess.call(['java', '-jar', 'testesSW/Elemulator/Elemulator.jar',
				"testesSW/machine_code/{0}.hack".format(nome[0]),
				"-i","testesSW/testesAssembly/{0}{1}_in.mif".format(nome[0],i),
				"-o","testesSW/machine_code/{0}{1}_out.mif".format(nome[0],i),"-c","1000"])
			if(error_code!=0):
				exit(error_code)

if __name__ == '__main__':
	emulate()

