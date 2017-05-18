@echo off
rem Todos os Testes do Z0
rem Arquivo: tests.bat
rem Criado por: Luciano Soares <lpsoares@insper.edu.br>
rem Data: 18/05/2017

ren let "n_error=0"

ren Testes para VHDL
set vhdl=F
if not "%2"=="" set vhdl=T
if "%~1"=="vhdl" set vhdl=T
if "%vhdl%"=="T" (
	echo
	echo "\t\t\t                \t\t\t"
	echo "\t\t\tTestes para VHDL\t\t\t"
	echo "\t\t\t                \t\t\t"
	echo 
	python TestesHW/run.py -p3
	ren let "n_error+=$?"
)

