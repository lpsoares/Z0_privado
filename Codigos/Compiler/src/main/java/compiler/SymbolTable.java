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

    // name, type, kind, #
    private HashMap<String, Symbol> symbolTable;

    Integer indexSTATIC;
    Integer indexFIELD;
    Integer indexARG;
    Integer indexVAR;

    /**
     * Cria a tabela de símbolos.
     * Creates a new empty symbol table.
     */
    public SymbolTable() {
        startSubroutine();
    }

    // Starts a new subroutine scope (i.e., resets the subroutine’s symbol table).
    public void startSubroutine() {
        symbolTable = new HashMap<String, Symbol>();
        indexSTATIC = 0;
        indexFIELD = 0;
        indexARG = 0;
        indexVAR = 0;
    }

    /**
     * Insere uma entrada de um símbolo com seu endereço numérico na tabela de símbolos.
     * @param  name símbolo a ser armazenado na tabela de símbolos.
     * @param  type símbolo a ser armazenado na tabela de símbolos.
     * @param  kind símbolo a ser armazenado na tabela de símbolos.
     */
    // Defines a new identifier of a given name, type, and kind and assigns it a running index. 
    // STATIC and FIELD identifiers have a class scope, while ARG and VAR identifiers have a subroutine scope.
    public Integer define(String name, String type, Symbol.Kind kind) {

        Integer tmpIndex=0;

        switch (kind) {
            case STATIC:
                tmpIndex = indexSTATIC;
                indexSTATIC++;
                break;

            case FIELD:
                tmpIndex = indexFIELD;
                indexFIELD++;
                break;
            case ARG:
                tmpIndex = indexARG;
                indexARG++;
                break;

            case VAR:
                tmpIndex = indexVAR;
                indexVAR++;
                break;
        }

        symbolTable.put(name, new Symbol(type, kind,tmpIndex));
        
        return tmpIndex;
    }

    // Returns the number of variables of the given kind already defined in the current scope.
    public Integer varCount(Symbol.Kind kind) {
        Integer tmpIndex=0;
        switch (kind) {
            case STATIC:
                tmpIndex = indexSTATIC;
                break;
            case FIELD:
                tmpIndex = indexFIELD;
                break;
            case ARG:
                tmpIndex = indexARG;
                break;
            case VAR:
                tmpIndex = indexVAR;
                break;
        }
        return tmpIndex;
    }


    /**
     * Retorna o valor númerico associado a um símbolo já inserido na tabela de símbolos.
     * @param  symbol símbolo a ser procurado na tabela de símbolos.
     * @return valor numérico associado ao símbolo procurado.
     */
    // Returns the kind of the named identifier in the current scope.
    // If the identifier is unknown in the current scope, returns NONE.
    public Symbol.Kind kindOf(String name) {
        if(symbolTable.containsKey(name)) {
            return symbolTable.get(name).kind;
        }
        return null;
    }

    // Returns the type of the named identifier in the current scope.
    public String typeOf(String name) {
        if(symbolTable.containsKey(name)) {
            return symbolTable.get(name).type;    
        }
        return null;
    }

    // Returns the index assigned to the named identifier.
    public Integer indexOf(String name) {
        if(symbolTable.containsKey(name)) {
            return symbolTable.get(name).index;
        }
        return null;
    }





}
