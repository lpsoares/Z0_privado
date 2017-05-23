/**
 * Curso: Elementos de Sistemas
 * Arquivo: Symbol.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 18/05/2017
 */

package compiler;

import java.util.*;

/**
 * M...
 */
public class Symbol {

    //type, kind, #

    String type;
    Kind kind;
    Integer index;

    /**
     * Cria .
     */
    public Symbol(String type, Kind kind, Integer index) {

        this.type = type;
        this.kind = kind;
        this.index = index;
        
    }

    /** Enumerator para os kind. */
    public enum Kind {
        STATIC,
        FIELD, 
        ARG, 
        VAR
    }


}
