# Makefile para Assembler do Z0
# Desenvolvido por: Luciano Soares <lpsoares@insper.edu.br>
# Data: 03/04/2017

JAVAC = javac
JAVA_FLAGS = -g -d bin -classpath bin -sourcepath src

JAR = jar
JAR_FLAGS = cfm

.SUFFIXES: .java .class .jar

sources = $(wildcard src/assembler/*.java)

javafile = src/assembler/AssemblerZ0.java
classfile = bin/assembler/AssemblerZ0.class
jarfile = AssemblerZ0.jar 
manifest = src/MANIFEST.MF

all: $(classfile) $(jarfile)

$(classfile) : $(javafile) $(sources)
	mkdir -p bin
	$(JAVAC) $(JAVA_FLAGS) $^

$(jarfile): $(classes) $(sources) $(manifest)
	jar cfm $(jarfile) src/MANIFEST.MF -C bin/ . 

clean:
	$(RM) bin/assembler/*.class *.jar
