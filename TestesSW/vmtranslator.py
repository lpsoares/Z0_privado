# Curso de Elementos de Sistemas
# Desenvolvido por: Luciano Soares <lpsoares@insper.edu.br>
# Data de criação: 4/05/2017
	
import subprocess
import loadTestes
import time
import argparse

def vmtranslator(testes,in_dir,out_dir,processos):
	
	start_time = time.time()

	subprocess.call(["mkdir", "-p", out_dir])

	nomes_testes = loadTestes.testes(testes)

	error_code = 0
	done = 0

	for i in nomes_testes:

		nome = i.split()
		error = subprocess.call(['java', '-jar', 'Codigos/VMTranslator/target/VMTranslator-1.0.jar',
			in_dir+"{0}.vm".format(nome[0]),
			"-o",out_dir+"{0}.nasm".format(nome[0]),"-n"])
		if(error!=0):
			error_code += error
		else:
			done += 1

	elapsed_time = time.time() - start_time
	print('\033[92m'+"VM Translated {0} file(s) in {1:.2f} seconds".format(done,elapsed_time)+'\033[0m') 

	if(error_code!=0):
		print('\033[91m'+"Failed {0} file(s)".format(len(nomes_testes)-done)+'\033[0m') 
		exit(error_code)

	
if __name__ == "__main__":
	ap = argparse.ArgumentParser()
	ap.add_argument("-t", "--tests", required=True,help="arquivo com lista de testes")
	ap.add_argument("-in", "--in_dir", required=True,help="caminho para codigos")
	ap.add_argument("-out", "--out_dir", required=True,help="caminho para salvar resultado de testes")
	ap.add_argument("-p", "--processos", required=True,help="numero de threads a se paralelizar")
	args = vars(ap.parse_args())
	vmtranslator(testes=args["tests"],in_dir=args["in_dir"],out_dir=args["out_dir"],processos=int(args["processos"]))
	

