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
        assertTrue( true );
    }
}
