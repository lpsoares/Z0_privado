/**
 * Curso: Elementos de Sistemas
 * Arquivo: Error.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 2/05/2017
 */

package compiler;

class Error {
    public static void error(String message) {
        System.err.println("Erro: " + message);
    }
}
