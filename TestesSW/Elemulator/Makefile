# Makefile para Assembler do Z0
# Desenvolvido por: Luciano Soares <lpsoares@insper.edu.br>
# Data: 03/04/2017

JAVAC = javac
JAVA_FLAGS = -g -d bin -classpath bin -sourcepath src

JAR = jar
JAR_FLAGS = cfm

.SUFFIXES: .java .class .jar

sources = $(wildcard src/elemulator/*.java)

javafile = src/elemulator/MainActivity.java
classfile = bin/elemulator/MainActivity.class
jarfile = Elemulator.jar 
manifest = src/MANIFEST.MF

all: $(classfile) $(jarfile)

$(classfile) : $(javafile) $(sources)
	$(JAVAC) $(JAVA_FLAGS) $^

$(jarfile): $(classes) $(sources) $(manifest)
	jar cfm $(jarfile) src/MANIFEST.MF -C bin/ . 

clean:
	$(RM) bin/elemulator/*.class *.jar