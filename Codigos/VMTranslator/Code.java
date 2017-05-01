/**
 * Curso: Elementos de Sistemas
 * Arquivo: Code.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 30/04/2017
 */

//package vmtranslator;

import java.util.*;
import java.io.*;


/** 
 * Traduz da linguagem vm para códigos assembly.
 */
public class Code {

    PrintWriter outputFile = null;
    String outputFilename = null;
    Integer labelCounter;

    /** 
     * Abre o arquivo de entrada VM e se prepara para analisá-lo.
     * @param filename nome do arquivo VM que será feito o parser.
     */
    public Code(String filename) throws FileNotFoundException,IOException {
        this.outputFilename = filename.substring(filename.lastIndexOf('/')+1, filename.lastIndexOf('.'));
        File file = new File(filename);
        this.outputFile = new PrintWriter(new FileWriter(file));
        labelCounter = 0;
    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para executar o comando aritmético.
     * @param  command comando aritmético a ser analisado.
     */
    public void writeArithmetic(String command) {

        if ( command.equals("")) {
            Error.error("Instrução invalida");
        }

        List<String> commands = new ArrayList<String>();

        if(command.equals("add")) {

            commands.add( "// ADD" );

            commands.add( "@SP" );
            commands.add( "M=M-1" );

            commands.add( "A=M" );
            commands.add( "D=M");

            commands.add( "@SP" );
            commands.add( "A=M-1" );
            
            commands.add( "D=M+D" );
            
            commands.add( "M=D" );
            
        } else if (command.equals("sub")) {

            commands.add( "// SUB" );

            commands.add( "@SP" );
            commands.add( "M=M-1" );

            commands.add( "A=M" );
            commands.add( "D=M");

            commands.add( "@SP" );
            commands.add( "A=M-1" );
            
            commands.add( "D=M-D" );
            
            commands.add( "M=D" );

        } else if (command.equals("neg")) {

            commands.add( "// NEG" );

            commands.add( "@SP" );
            commands.add( "A=M-1" );

            commands.add( "M=-M" );

        } else if (command.equals("eq")) {

            commands.add( "// EQ" );
            commands.add( "@SP" );
            commands.add( "M=M-1" );

            commands.add( "A=M" );
            commands.add( "D=M");

            commands.add( "@SP" );
            commands.add( "A=M-1" );
            
            commands.add( "D=M-D" );

            commands.add("@EQ"+outputFilename+labelCounter);
            commands.add("D; JEQ");
            commands.add("@SP");
            commands.add("A=M-1");
            commands.add("M=0");
            commands.add("@EQ2"+outputFilename+labelCounter);
            commands.add("0; JMP");
            commands.add("(EQ"+outputFilename+labelCounter+")");
            commands.add("@SP");
            commands.add("A=M-1");
            commands.add("M=-1");
            commands.add("(EQ2"+outputFilename+labelCounter+")");
            labelCounter += 1;

        } else if (command.equals("gt")) {

            commands.add( "// GT" );
            commands.add( "@SP" );
            commands.add( "M=M-1" );

            commands.add( "A=M" );
            commands.add( "D=M");

            commands.add( "@SP" );
            commands.add( "A=M-1" );
            
            commands.add( "D=M-D" );

            commands.add("@GT"+outputFilename+labelCounter);
            commands.add("D; JGT");
            commands.add("@SP");
            commands.add("A=M-1");
            commands.add("M=0");
            commands.add("@GT2"+outputFilename+labelCounter);
            commands.add("0; JMP");
            commands.add("(GT"+outputFilename+labelCounter+")");
            commands.add("@SP");
            commands.add("A=M-1");
            commands.add("M=-1");
            commands.add("(GT2"+outputFilename+labelCounter+")");
            labelCounter += 1;

        } else if (command.equals("lt")) {

            commands.add( "// GT" );
            commands.add( "@SP" );
            commands.add( "M=M-1" );

            commands.add( "A=M" );
            commands.add( "D=M");

            commands.add( "@SP" );
            commands.add( "A=M-1" );
            
            commands.add( "D=M-D" );

            commands.add("@LT"+outputFilename+labelCounter);
            commands.add("D; JLT");
            commands.add("@SP");
            commands.add("A=M-1");
            commands.add("M=0");
            commands.add("@LT2"+outputFilename+labelCounter);
            commands.add("0; JMP");
            commands.add("(LT"+outputFilename+labelCounter+")");
            commands.add("@SP");
            commands.add("A=M-1");
            commands.add("M=-1");
            commands.add("(LT2"+outputFilename+labelCounter+")");
            labelCounter += 1;

        } else if (command.equals("and")) {

            commands.add( "// AND" );

            commands.add( "@SP" );
            commands.add( "M=M-1" );

            commands.add( "A=M" );
            commands.add( "D=M");

            commands.add( "@SP" );
            commands.add( "A=M-1" );
            
            commands.add( "D=M&D" );
            
            commands.add( "M=D" );

        } else if (command.equals("or")) {

            commands.add( "// OR" );

            commands.add( "@SP" );
            commands.add( "M=M-1" );

            commands.add( "A=M" );
            commands.add( "D=M");

            commands.add( "@SP" );
            commands.add( "A=M-1" );
            
            commands.add( "D=M|D" );
            
            commands.add( "M=D" );

        } else if (command.equals("not")) {

            commands.add( "// NOT" );

            commands.add( "@SP" );
            commands.add( "A=M-1" );

            commands.add( "M=!M" );

        }

        String[] stringArray = new String[ commands.size() ];
        commands.toArray( stringArray );
        write(stringArray);

    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para executar o comando de Push ou Pop.
     * @param  command comando de push ou pop a ser analisado.
     * @param  segment segmento de memória a ser usado pelo comando.
     * @param  index índice do segkento de memória a ser usado pelo comando.
     */
    public void writePushPop(Parser.CommandType command, String segment, Integer index) {

        if ( command.equals("")) {
            Error.error("Instrução invalida");
        }

        List<String> commands = new ArrayList<String>();

        if(command == Parser.CommandType.C_POP) {
            commands.add( "// POP "+segment+" "+String.valueOf(index));
            if (segment.equals("constant")) {
                Error.error("Não faz sentido POP com constant");
            } else if (segment.equals("local")) {
                commands.add( "@SP" );
                commands.add( "M=M-1" );

                commands.add( "@"+String.valueOf(index) );
                commands.add( "D=A" );
                commands.add( "@LCL");
                commands.add( "D=M+D" );

                commands.add( "@R15");
                commands.add( "M=D" );

                commands.add( "@SP" );
                commands.add( "A=M" );
                commands.add( "D=M" );

                commands.add( "@R15");
                commands.add( "A=M" );
                commands.add( "M=D" );

            } else if (segment.equals("argument")) {
                commands.add( "@SP" );
                commands.add( "M=M-1" );

                commands.add( "@"+String.valueOf(index) );
                commands.add( "D=A" );
                commands.add( "@ARG");
                commands.add( "D=M+D" );

                commands.add( "@R15");
                commands.add( "M=D" );

                commands.add( "@SP" );
                commands.add( "A=M" );
                commands.add( "D=M" );

                commands.add( "@R15");
                commands.add( "A=M" );
                commands.add( "M=D" );
 
            } else if (segment.equals("this")) {
                commands.add( "@SP" );
                commands.add( "M=M-1" );

                commands.add( "@"+String.valueOf(index) );
                commands.add( "D=A" );
                commands.add( "@THIS");
                commands.add( "D=M+D" );

                commands.add( "@R15");
                commands.add( "M=D" );

                commands.add( "@SP" );
                commands.add( "A=M" );
                commands.add( "D=M" );

                commands.add( "@R15");
                commands.add( "A=M" );
                commands.add( "M=D" );

            } else if (segment.equals("that")) {
                commands.add( "@SP" );
                commands.add( "M=M-1" );

                commands.add( "@"+String.valueOf(index) );
                commands.add( "D=A" );
                commands.add( "@THAT");
                commands.add( "D=M+D" );

                commands.add( "@R15");
                commands.add( "M=D" );

                commands.add( "@SP" );
                commands.add( "A=M" );
                commands.add( "D=M" );

                commands.add( "@R15");
                commands.add( "A=M" );
                commands.add( "M=D" );

            } else if (segment.equals("static")) {
                commands.add( "@SP" );
                commands.add( "M=M-1" );
                commands.add( "A=M" );
                commands.add( "D=M" );
                commands.add( "@"+outputFilename+"."+String.valueOf(index) );
                commands.add( "M=D" );
            } else if (segment.equals("temp")) {
                commands.add( "@SP" );
                commands.add( "M=M-1" );
                commands.add( "A=M" );
                commands.add( "D=M" );
                commands.add( "@"+String.valueOf(index+5) );
                commands.add( "M=D" );
            } else if (segment.equals("pointer")) {
                if(index==0) {
                    commands.add( "@SP" );
                    commands.add( "M=M-1" );
                    commands.add( "A=M" );
                    commands.add( "D=M" );
                    commands.add( "@THIS" );
                    commands.add( "M=D" );
                } else {
                    commands.add( "@SP" );
                    commands.add( "M=M-1" );
                    commands.add( "A=M" );
                    commands.add( "D=M" );
                    commands.add( "@THAT" );
                    commands.add( "M=D" );
                }
            }
        } else if (command == Parser.CommandType.C_PUSH) {
            commands.add( "// PUSH "+segment+" "+String.valueOf(index));
            if (segment.equals("constant")) {
                commands.add( "@"+String.valueOf(index) );
                commands.add( "D=A" );
                commands.add( "@SP" );
                commands.add( "A=M" );
                commands.add( "M=D" );
                commands.add( "@SP" );
                commands.add( "M=M+1" );
            } else if (segment.equals("local")) {
                commands.add( "@"+String.valueOf(index) );
                commands.add( "D=A" );
                commands.add( "@LCL");
                commands.add( "A=M+D" );
                commands.add( "D=M" );
                commands.add( "@SP" );
                commands.add( "A=M" );
                commands.add( "M=D" );
                commands.add( "@SP" );
                commands.add( "M=M+1" );
            } else if (segment.equals("argument")) {
                commands.add( "@"+String.valueOf(index) );
                commands.add( "D=A" );
                commands.add( "@ARG");
                commands.add( "A=M+D" );
                commands.add( "D=M" );
                commands.add( "@SP" );
                commands.add( "A=M" );
                commands.add( "M=D" );
                commands.add( "@SP" );
                commands.add( "M=M+1" );
            } else if (segment.equals("this")) {
                commands.add( "@"+String.valueOf(index) );
                commands.add( "D=A" );
                commands.add( "@THIS");
                commands.add( "A=M+D" );
                commands.add( "D=M" );
                commands.add( "@SP" );
                commands.add( "A=M" );
                commands.add( "M=D" );
                commands.add( "@SP" );
                commands.add( "M=M+1" );
            } else if (segment.equals("that")) {
                commands.add( "@"+String.valueOf(index) );
                commands.add( "D=A" );
                commands.add( "@THAT");
                commands.add( "A=M+D" );
                commands.add( "D=M" );
                commands.add( "@SP" );
                commands.add( "A=M" );
                commands.add( "M=D" );
                commands.add( "@SP" );
                commands.add( "M=M+1" );
            } else if (segment.equals("static")) {
                commands.add( "@"+outputFilename+"."+String.valueOf(index) );
                commands.add( "D=M" );
                commands.add( "@SP" );
                commands.add( "A=M" );
                commands.add( "M=D" );
                commands.add( "@SP" );
                commands.add( "M=M+1" );
            } else if (segment.equals("temp")) {
                commands.add( "@"+String.valueOf(index+5) );
                commands.add( "D=M" );
                commands.add( "@SP" );
                commands.add( "A=M" );
                commands.add( "M=D" );
                commands.add( "@SP" );
                commands.add( "M=M+1" );
            } else if (segment.equals("pointer")) {
                if(index==0) {
                    commands.add( "@THIS" );
                    commands.add( "D=M" );
                    commands.add( "@SP" );
                    commands.add( "A=M" );
                    commands.add( "M=D" );
                    commands.add( "@SP" );
                    commands.add( "M=M+1" );
                } else {
                    commands.add( "@THAT" );
                    commands.add( "D=M" );
                    commands.add( "@SP" );
                    commands.add( "A=M" );
                    commands.add( "M=D" );
                    commands.add( "@SP" );
                    commands.add( "M=M+1" );
                }
            }
        }

        String[] stringArray = new String[ commands.size() ];
        commands.toArray( stringArray );
        write(stringArray);

    }


    // grava as instruções em Assembly no arquivo de saída
    public void write(String[] stringArray) {
        // gravando comandos no arquivos
        for (String s: stringArray) {  
            System.out.println(s);
            this.outputFile.println(s);
        }
    }

    // fecha o arquivo de escrita
    public void close() throws IOException {
        this.outputFile.close();
    }

}
