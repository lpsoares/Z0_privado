import subprocess
import loadTestes
import time


def emulate():
	
	start_time = time.time()

	nomes_testes = loadTestes.testes("TestesSW/testes.txt")

	for j in nomes_testes:

		nome = j.split()
		if int(nome[1]) > 0:
			for i in range(int(nome[1])):
				error_code = subprocess.call(['java', '-jar', 'TestesSW/Elemulator/Elemulator.jar',
					"TestesSW/machine_code/{0}.hack".format(nome[0]),
					"-i","TestesSW/testesAssembly/{0}{1}_in.mif".format(nome[0],i),
					"-o","TestesSW/machine_code/{0}{1}_out.mif".format(nome[0],i),"-c",nome[2]])
				if(error_code!=0):
					exit(error_code)
		else:
			error_code = subprocess.call(['java', '-jar', 'TestesSW/Elemulator/Elemulator.jar',
				"TestesSW/machine_code/{0}.hack".format(nome[0]),
				"-p","TestesSW/machine_code/{0}.pbm".format(nome[0],i),
				"-i","TestesSW/testesAssembly/{0}_in.mif".format(nome[0],i),
				"-o","TestesSW/machine_code/{0}_out.mif".format(nome[0],i),"-c",nome[2]])
			if(error_code!=0):
				exit(error_code)

	elapsed_time = time.time() - start_time
	print('\033[92m'+"Emulated {0} files in {1:.2f} seconds".format(len(nomes_testes),elapsed_time)+'\033[0m') 

if __name__ == '__main__':
	emulate()

