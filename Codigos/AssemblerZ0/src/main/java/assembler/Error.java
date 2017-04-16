/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 04/02/2017
 */

package assembler;

class Error {
    public static void error(String message, String fileName, int lineNum, String line) {
        System.err.println(fileName + ":" + lineNum + ": Erro: " + message);
        System.err.println("\t instrução: " + line);
    }
    
    public static void error(String message, String fileName, int lineNum) {
        System.err.println(fileName + ":" + lineNum + ": Erro: " + message);
    }
    
    public static void error(String message) {
        System.err.println("Erro: " + message);
    }
}
