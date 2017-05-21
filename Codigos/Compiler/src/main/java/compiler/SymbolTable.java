/**
 * Curso: Elementos de Sistemas
 * Arquivo: SymbolTable.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 04/02/2017
 */

package compiler;

import java.util.*;

/**
 * Mantém uma tabela com a correspondência entre os rótulos simbólicos e endereços numéricos de memória.
 */
public class SymbolTable {

    private HashMap<String, Integer> symbolTable;

    /**
     * Cria a tabela de símbolos.
     * Creates a new empty symbol table.
     */
    public SymbolTable() {
        symbolTable = new HashMap<String, Integer>();
        initialize();
    }

    /** Enumerator para os kind. */
    public enum Kind {
        STATIC,
        FIELD, 
        ARG, 
        VAR
    }

    // Starts a new subroutine scope (i.e., resets the subroutine’s symbol table).
    public void startSubroutine() {

    }

    // Defines a new identifier of a given name, type, and kind and assigns it a running index. 
    // STATIC and FIELD identifiers have a class scope, while ARG and VAR identifiers have a subroutine scope.
    public Integer Define(String name, String type, Kind kind) {

        return null;
    }

    // Returns the number of variables of the given kind already defined in the current scope.
    public void VarCount(Kind kind) {
        return;
    }


    // Returns the kind of the named identifier in the current scope.
    // If the identifier is unknown in the current scope, returns NONE.
    public void kindOf() {

    }

    // Returns the type of the named identifier in the current scope.
    public String TypeOf(String name) {
        return null;
    }

    // Returns the index assigned to the named identifier.
    public Integer IndexOf(String name) {
        return null;
    }



    /**
     * Insere uma entrada de um símbolo com seu endereço numérico na tabela de símbolos.
     * @param  symbol símbolo a ser armazenado na tabela de símbolos.
     * @param  address símbolo a ser armazenado na tabela de símbolos.
     */
    public void addEntry(String symbol, int address) {
        symbolTable.put(symbol, address);
        return;
    }

    /**
     * Confere se o símbolo informado já foi inserido na tabela de símbolos.
     * @param  symbol símbolo a ser procurado na tabela de símbolos.
     * @return Verdadeiro se símbolo está na tabela de símbolos, Falso se não está na tabela de símbolos.
     */
    public Boolean contains(String symbol) {
        return symbolTable.containsKey(symbol);
    }

    /**
     * Retorna o valor númerico associado a um símbolo já inserido na tabela de símbolos.
     * @param  symbol símbolo a ser procurado na tabela de símbolos.
     * @return valor numérico associado ao símbolo procurado.
     */
    public Integer getAddress(String symbol) {
        return symbolTable.get(symbol);
    }

    // initialize the symbol table with predefined symbols
    public void initialize() {

        // Ponteiros para teclado e display
        this.addEntry("KBD", 24576);
   
        return;
    }
}
