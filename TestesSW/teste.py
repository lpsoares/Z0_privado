# Testador de emulação
# Luciano Soares

import sys, getopt
import unittest
import pytest
import loadTestes

nomes_testes = loadTestes.testes("testesSW/testes.txt")

@pytest.mark.parametrize(('nomes_testes'),nomes_testes)
def test_Assembly(nomes_testes):

	nomes_testes = nomes_testes.split()

	for i in range(int(nomes_testes[1])):

		resultado = "testesSW/machine_code/{0}{1}_out.mif".format(nomes_testes[0],i)
		teste = "testesSW/testesAssembly/{0}{1}_tst.mif".format(nomes_testes[0],i)
		debug = False

		ram = {}
		validacao = {}

		# rotina de leitura do resultado da emulação
		with open(resultado, 'r') as arquivo:
			linhas = arquivo.read().splitlines()

			for linha in linhas:
				alocacao = linha.split(":")
				ram[int(alocacao[0].strip())] = int("0b"+alocacao[1].strip(),2)
			
			if debug:
				print("RAM")
				for e,v in ram.items():
					print("|{0}|  =>  |{1:016b}|".format(e,v))
				print("\n")

		# rotina do teste da emulação
		with open(teste, 'r') as arquivo:
			linhas = arquivo.read().splitlines()

			for linha in linhas:
				alocacao = linha.split(":")
				validacao[int(alocacao[0].strip())] = int("0b"+alocacao[1].strip(),2)
			
			if debug:
				print("Teste")
				for e,v in validacao.items():
					print("|{0}|  =>  |{1:016b}|".format(e,v))
				print("\n")

			for e,v in validacao.items():
				assert (v==ram[e]),"Erro RAM[{0}]={1:016b}, valor esperado ({2:016b})".format(e,ram[e],v)	

	
