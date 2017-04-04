/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 04/02/2017
 */

class Error {
    public static void error(String message, String fileName, int lineNum, String line) {
        System.err.println(fileName + ":" + lineNum + ": Erro: " + message);
        System.err.println("\t" + line);
        System.exit(1);
    }
    
    public static void error(String message, String fileName, int lineNum) {
        System.err.println(fileName + ":" + lineNum + ": Erro: " + message);
        System.exit(1);
    }
    
    public static void error(String message) {
        System.err.println("Erro: " + message);
        System.exit(1);
    }
}

class InvalidDestException extends Exception {} // exception para destinos inconsistentes
class InvalidCompException extends Exception {} // exception para cálculos inconsistentes
class InvalidJumpException extends Exception {} // exception para saltos inconsistentes
