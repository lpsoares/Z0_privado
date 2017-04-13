/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 04/02/2017
 */

package elemulator;

class Error {
    public static void error(String message) {
        System.err.println("Erro: " + message);
        System.exit(1);
    }
}
