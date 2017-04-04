# Makefile para Assembler do Z0
# Desenvolvido por: Luciano Soares <lpsoares@insper.edu.br>
# Data: 03/04/2017

JAVAC = javac
JAVA_FLAGS = -g

JAR = jar
JAR_FLAGS = cfm

.SUFFIXES: .java .class .jar

sources = $(wildcard *.java)
classes = $(sources:.java=.class)
jarfile = AssemblerZ0.jar 

all: $(jarfile) $(classes)

%.class : %.java
	$(JAVAC) $(sources)

$(jarfile): $(classes) $(sources) 
	jar cfm $(jarfile) MANIFEST.MF $(classes)

clean:
	$(RM) *.class *.jar