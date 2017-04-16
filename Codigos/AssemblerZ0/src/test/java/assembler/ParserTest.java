package assembler;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.*;

import assembler.Parser;

public class ParserTest extends TestCase  {

    Parser parser = null;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ParserTest( String testName ) {
        super( testName );
        try {
            parser = new Parser("src/test/resources/testLeaw.nasm");
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite( ParserTest.class );
    }

    /**
     * Teste para checar se parser foi instanciado
     */
    public void testParser_instanciado() {
        assertNotNull("Falha a criar o Parser",parser);
    }


    /**
     * Teste para a instrução commandType
     */
    public void testParser_commandType() {
        assertTrue("leaw $0,%A",parser.commandType("leaw $0,%A")==Parser.CommandType.A_COMMAND);
        assertTrue("abc:",parser.commandType("abc:")==Parser.CommandType.L_COMMAND);
        assertTrue("movw %A,%D",parser.commandType("movw %A,%D")==Parser.CommandType.C_COMMAND);
        assertTrue("TESTE:",parser.commandType("TESTE:")==Parser.CommandType.L_COMMAND);
        assertTrue("leaw $100,%A",parser.commandType("leaw $100,%A")==Parser.CommandType.A_COMMAND);
        assertTrue("Z0:",parser.commandType("Z0:")==Parser.CommandType.L_COMMAND);
        assertTrue("movw %D,%A",parser.commandType("movw %D,%A")==Parser.CommandType.C_COMMAND);
        assertTrue("jmp",parser.commandType("jmp")==Parser.CommandType.C_COMMAND);
        assertTrue("nop",parser.commandType("nop")==Parser.CommandType.C_COMMAND);
        assertTrue("addw %D,%A,%D",parser.commandType("addw %D,%A,%D")==Parser.CommandType.C_COMMAND);
    }




}