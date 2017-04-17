/**
 * Curso: Elementos de Sistemas
 * Arquivo: SymbolTableTest.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 16/04/2017
 */

package assembler;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import assembler.SymbolTable;

public class SymbolTableTest extends TestCase  {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SymbolTableTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( SymbolTableTest.class );
    }

    /**
     * Teste para conversão para binário
     */
    public void testSymbolTable_toBinary() {

        try {
            assertTrue(Code.toBinary("32767").equals("111111111111111"));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}