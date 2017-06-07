#!/usr/bin/env python
# -*- coding: utf-8 -*-

# Testador de emulação de Sistema Operacional
# Arquivo: testOS.py
# Criado por: Luciano Soares <lpsoares@insper.edu.br>
# Data: 16/03/2017

import sys, getopt
import unittest
import pytest
import loadTestes
import detectImage
import diffImage
import checkUnitTests

nomes_testes = loadTestes.testes("TestesSW/testesOS.txt")

@pytest.mark.parametrize(('nomes_testes'),nomes_testes)
def test_OS(nomes_testes):

	nomes_testes = nomes_testes.split()

	debug = False

	if int(nomes_testes[1]) == 0:

		resultado = "TestesSW/os_code/{0}_out.mif".format(nomes_testes[0])
		teste = "TestesSW/testesOS/{0}_tst.mif".format(nomes_testes[0])

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
					print("|{0}|  =>  |{1:032b}|".format(e,v))
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
					print("|{0}|  =>  |{1:032b}|".format(e,v))
				print("\n")

		for e,v in validacao.items():
			assert (v==ram[e]),"Erro: {0} RAM[{1}]={2:032b}, valor esperado ({3:032b})".format(nomes_testes[0],e,ram[e],v)	

	else:

		teste = "TestesSW/testesOS/{0}_tst.pbm".format(nomes_testes[0])
		imagem = "TestesSW/os_code/{0}.pbm".format(nomes_testes[0])
		
		assert ( diffImage.diffImage(teste,imagem) ),"Erro: {0} imagens não conferem {1} != {2}".format(nomes_testes[0],teste,imagem)	
