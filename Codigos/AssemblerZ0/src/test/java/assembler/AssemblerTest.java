/**
 * Curso: Elementos de Sistemas
 * Arquivo: AssemblerTest.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 16/04/2017
 */

package assembler;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Assmbler.
 */
public class AssemblerTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AssemblerTest( String testName ) {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( AssemblerTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testAssembler() {

        try {
            assertTrue( true );
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
}
