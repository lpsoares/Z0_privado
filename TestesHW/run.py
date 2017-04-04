# Curso de Elementos de Sistemas
# Desenvolvido por: Pedro Cunial   <pedrocc4@al.insper.edu.br>
#                   Luciano Soares <lpsoares@insper.edu.br>
# Data de criação: 25/01/2017

from os.path import join, dirname
from vunit import VUnit

root = dirname(__file__)

ui = VUnit.from_argv()
lib = ui.add_library("lib")
lib.add_source_files(join(root, "..","Computador","Modulos","PortasLogicas", "*.vhd"))  # source
lib.add_source_files(join(root, "..","Computador","Modulos","UnidadeLogicaAritmetica", "*.vhd"))  # source
lib.add_source_files(join(root, "..","Computador","Modulos","CircuitosSequenciais", "*.vhd"))   # source
lib.add_source_files(join(root, "..","Computador","Testes","PortasLogicas", "*.vhd"))   # test
lib.add_source_files(join(root, "..","Computador","Testes","UnidadeLogicaAritmetica", "*.vhd"))   # test
lib.add_source_files(join(root, "..","Computador","Testes","CircuitosSequenciais", "*.vhd"))   # test
ui.main()
