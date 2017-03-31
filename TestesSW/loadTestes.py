def testes(nome_arquivo):

	nomes_testes = []

	# rotina de leitura do resultado da emulação
	with open(nome_arquivo, 'r') as arquivo:
		nomes_testes = arquivo.read().splitlines()

	return nomes_testes
