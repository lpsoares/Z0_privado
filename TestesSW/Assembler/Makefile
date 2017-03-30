JAVAC=javac

all:
	$(JAVAC) AssemblerZ0.java Assemble.java Parser.java Code.java SymbolTable.java Error.java

clean :
	rm -f *.class *.jar

jar:
	jar cfm AssemblerZ0.jar MANIFEST.MF *.class
