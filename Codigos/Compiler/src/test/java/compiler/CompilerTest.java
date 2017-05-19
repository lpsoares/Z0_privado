/**
 * Curso: Elementos de Sistemas
 * Arquivo: ParserTest.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 2/05/2017
 */

package compiler;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

//import vmtranslator.Parser;

public class CompilerTest {

	//Parser parser = null;

	/**
	 * Create the test case
	 *
	 */
	public CompilerTest() {
		try {
			//parser = new Parser("src/test/resources/SimpleAdd.vm");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Teste para a instrução commandType
	 */
	@Test
	public void testParser_commandType() {

/*
		try {
			org.junit.Assume.assumeNotNull( parser.commandType("nop") );		// ignora test
		} catch(Exception e) { 
			org.junit.Assume.assumeNoException(e);
		}
		
		try {
			
			assertTrue("add",parser.commandType("add")==Parser.CommandType.C_ARITHMETIC);
			assertTrue("push constant 0",parser.commandType("push constant 0")==Parser.CommandType.C_PUSH);
			assertTrue("pop temp 0",parser.commandType("pop temp 0")==Parser.CommandType.C_POP);
			assertTrue("label LOOP",parser.commandType("label LOOP")==Parser.CommandType.C_LABEL);
			assertTrue("goto LOOP",parser.commandType("goto LOOP")==Parser.CommandType.C_GOTO);
			assertTrue("if-goto LOOP",parser.commandType("if-goto LOOP")==Parser.CommandType.C_IF);
			assertTrue("function Sys.init 0",parser.commandType("function Sys.init 0")==Parser.CommandType.C_FUNCTION);
			assertTrue("return",parser.commandType("return")==Parser.CommandType.C_RETURN);
			assertTrue("call Sys.init 0",parser.commandType("call Sys.init 0")==Parser.CommandType.C_CALL);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		*/
	}

}