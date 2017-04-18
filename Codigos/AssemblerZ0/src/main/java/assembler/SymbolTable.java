/**
 * Curso: Elementos de Sistemas
 * Arquivo: SymbolTable.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 04/02/2017
 */

package assembler;

import java.util.*;

public class SymbolTable {

    private HashMap<String, Integer> symbolTable;

    // Cria a tabela de símbolos
    public SymbolTable() {
        symbolTable = new HashMap<String, Integer>();
        initialize();
    }

    // Insere uma entrada de um símbolo com seu endereço numérico na tabela de símbolos
    public void addEntry(String symbol, int address) {
        symbolTable.put(symbol, address);
        return;
    }

    // Confere se o símbolo já foi inserido na tabela de símbolos
    public boolean contains(String symbol) {
        return symbolTable.containsKey(symbol);
    }

    // Retorna o valor número de um símbolo já inserido na tabela de símbolos
    public int getAddress(String symbol) {
        return symbolTable.get(symbol);
    }

    // initialize the symbol table with predefined symbols
    public void initialize() {

        // Registradores Virtuais
        for (int i = 0; i < 16; i++)
            this.addEntry("R" + i, i);
        
        // Ponteiros para teclado e display
        this.addEntry("KBD", 24576);
        this.addEntry("SCREEN", 16384);
    
        // Simbolos para máquina virtual
        this.addEntry("SP", 0);
        this.addEntry("LCL", 1);
        this.addEntry("ARG", 2);
        this.addEntry("THIS", 3);
        this.addEntry("THAT", 4);
        
        return;
    }
}
