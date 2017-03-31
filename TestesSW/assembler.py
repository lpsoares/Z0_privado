
import subprocess
import loadTestes

def assembler():
	
	subprocess.call(["mkdir", "-p", "TestesSW/machine_code"])

	nomes_testes = loadTestes.testes("TestesSW/testes.txt")

	for i in nomes_testes:
		nome = i.split()
		error_code = subprocess.call(['java', '-jar', 'TestesSW/Assembler/AssemblerZ0.jar',
			"Codigos/Assembly/{0}.nasm".format(nome[0]),
			"-o","TestesSW/machine_code/{0}.hack".format(nome[0])])
		if(error_code!=0):
			exit(error_code)

if __name__ == '__main__':
	assembler()

