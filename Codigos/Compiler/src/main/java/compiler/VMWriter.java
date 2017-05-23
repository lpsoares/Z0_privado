/**
 * Curso: Elementos de Sistemas
 * Arquivo: VMWriter.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 6/05/2017
 */


//output module for generating VM code;

package compiler;

import java.io.*;

/**
 * Encapsula o código de leitura. Carrega as instruções na linguagem de máquina virtual a pilha,
 * analisa, e oferece acesso aos comandos.
 * Além disso, remove todos os espaços em branco e comentários.
 */
public class VMWriter {

    PrintWriter outputFile = null;

    /** 
     * Abre o arquivo de entrada VM e se prepara para analisá-lo.
     * @param file arquivo VM que será feito o parser.
     */
    public VMWriter(String outputfilename) {
        try {

            File file = new File(outputfilename);
            outputFile = new PrintWriter(new FileWriter(file));

        } catch (FileNotFoundException e) {

        } catch (java.io.IOException e) {

        }
    }

    /** Enumerator para os kind. */
    public enum Segment {
        CONST,
        ARG, 
        LOCAL, 
        STATIC,
        THIS,
        THAT,
        POINTER,
        TEMP
    }

    /** Enumerator para os kind. */
    public enum Command {
        ADD,
        SUB, 
        NEG, 
        EQ,
        GT,
        LT,
        AND,
        OR,
        NOT
    }

    public void print(String text) {
        //System.out.println(text);
        outputFile.println(text);        
    }


    public String findSegment(Segment segment) {

        String segmentName = "";

        switch(segment) {
            case CONST: segmentName = "constant";
            break;
            case ARG: segmentName = "argument";
            break;
            case LOCAL: segmentName = "local";
            break;
            case STATIC: segmentName = "static";
            break;
            case THIS: segmentName = "this";
            break;
            case THAT: segmentName = "that";
            break;
            case POINTER: segmentName = "pointer";
            break;
            case TEMP: segmentName = "temp";
            break;
        } 

        return segmentName;
    }

    // Writes a VM push command.
    public void writePush(Segment segment, Integer index) {
        String segmentName = findSegment(segment);
        print("push "+segmentName+" "+String.valueOf(index));
    }

    // Writes a VM pop command.
    public void writePop(Segment segment, Integer index) {
        String segmentName = findSegment(segment);
        print("pop "+segmentName+" "+String.valueOf(index));
    }

    // Writes a VM arithmetic command.
    public void writeArithmetic(Command command) {

        String commandName = "";

        switch(command) {
            case ADD: commandName = "add";
            break;
            case SUB: commandName = "sub";
            break;
            case NEG: commandName = "neg";
            break;
            case EQ: commandName = "eq";
            break;
            case GT: commandName = "gt";
            break;
            case LT: commandName = "lt";
            break;
            case AND: commandName = "and";
            break;
            case OR: commandName = "or";
            break;
            case NOT: commandName = "not";
            break;
        }
    
        print(commandName);

    }

    // Writes a VM label command.
    public void writeLabel(String label) {
        print("label "+label);
    }

    // Writes a VM goto command.
    public void writeGoto(String label) {
        print("goto "+label);
    }

    // Writes a VM If-goto command.
    public void writeIf(String label) {
        print("if-goto "+label);
    }

    // Writes a VM call command.
    public void writeCall(String name, Integer nArgs) {
        print("call "+name+" "+String.valueOf(nArgs));
    }

    // Writes a VM function command.
    public void writeFunction(String name, Integer nLocals) {
        print("function "+name+" "+String.valueOf(nLocals));
    }

    // Writes a VM return command.
    public void writeReturn() {
        print("return");

    }

    public void writeString(String text) {
        //print(String.valueOf(text.length()));
        writePush(Segment.CONST,text.length()-2);
        writeCall("String.new",1);
        
        for(int i=1;i<text.length()-1;i++) {
            char character = text.charAt(i);
            int ascii = (int) character;
            writePush(Segment.CONST,ascii);
            writeCall("String.appendChar",2);
        }

    }

    // fecha o arquivo de leitura
    public void close() throws IOException {
        this.outputFile.close();
    }

}
