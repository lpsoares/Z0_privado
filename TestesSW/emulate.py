
import subprocess
import loadTestes

def emulate():

	nomes_testes = loadTestes.testes("TestesSW/testes.txt")

	for j in nomes_testes:

		nome = j.split()
		for i in range(int(nome[1])):

			error_code = subprocess.call(['java', '-jar', 'TestesSW/Elemulator/Elemulator.jar',
				"TestesSW/machine_code/{0}.hack".format(nome[0]),
				"-i","TestesSW/testesAssembly/{0}{1}_in.mif".format(nome[0],i),
				"-o","TestesSW/machine_code/{0}{1}_out.mif".format(nome[0],i),"-c",nome[2]])
			if(error_code!=0):
				exit(error_code)

if __name__ == '__main__':
	emulate()

