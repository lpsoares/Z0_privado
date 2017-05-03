/**
 * Curso: Elementos de Sistemas
 * Arquivo: Code.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 2/05/2017
 */

//package vmtranslator;

import java.util.*;
import java.io.*;
import java.nio.file.*;

/** 
 * Traduz da linguagem vm para códigos assembly.
 */
public class Code {

    PrintWriter outputFile = null;
    String outputFilename = null;
    String filename = null;
    Integer labelCounter;
    Map<String, Integer> retHash;


    /** 
     * Abre o arquivo de entrada VM e se prepara para analisá-lo.
     * @param filename nome do arquivo VM que será feito o parser.
     */
    public Code(String filename) throws FileNotFoundException,IOException {
        this.outputFilename = filename.substring(filename.lastIndexOf('/')+1, filename.lastIndexOf('.'));
        File file = new File(filename);
        this.outputFile = new PrintWriter(new FileWriter(file));
        labelCounter = 0;

        retHash = new HashMap<String, Integer>();

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
                commands.add( "@"+filename+"."+String.valueOf(index) );
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
                commands.add( "@"+filename+"."+String.valueOf(index) );
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

    /**
     * Grava no arquivo de saida as instruções em Assembly para inicializar o processo da VM (bootstrap).
     * Também prepara a chamada para a função Sys.init
     * O código deve ser colocado no início do arquivo de saída.
     */
    public void writeInit() {

        List<String> commands = new ArrayList<String>();
        commands.add( "// Inicialização para VM" );

        commands.add( "@256" );
        commands.add( "D=A" );
        commands.add( "@SP" );
        commands.add( "M=D");

        String[] stringArray = new String[ commands.size() ];
        commands.toArray( stringArray );
        write(stringArray);

        writeCall("Sys.init",0);

    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para gerar o labels (marcadores de jump).
     * @param  label define nome do label (marcador) a ser escrito.
     */
    public void writeLabel(String label) {

        List<String> commands = new ArrayList<String>();
        commands.add( "// Label (marcador)" );

        commands.add( "("+label+")" );

        String[] stringArray = new String[ commands.size() ];
        commands.toArray( stringArray );
        write(stringArray);

    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para gerar as instruções de goto (jumps).
     * Realiza um jump incondicional para o label informado.
     * @param  label define jump a ser realizado para um label (marcador).
     */
    public void writeGoto(String label) {

        List<String> commands = new ArrayList<String>();
        commands.add( "// Goto incondicional (JMP)" );

        commands.add( "@"+label );
        commands.add( "0;JMP" );

        String[] stringArray = new String[ commands.size() ];
        commands.toArray( stringArray );
        write(stringArray);

    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para gerar as instruções de goto condicional (jumps condicionais).
     * Realiza um jump condicional para o label informado.
     * @param  label define jump a ser realizado para um label (marcador).
     */
    public void writeIf(String label) {

        List<String> commands = new ArrayList<String>();
        commands.add( "// Goto condicional (outros Jumps)" );

        commands.add( "@SP" );
        commands.add( "M=M-1" );
        commands.add( "A=M" );
        commands.add( "D=M" );
        
        commands.add( "@"+label );
        commands.add( "D;JNE" );

        String[] stringArray = new String[ commands.size() ];
        commands.toArray( stringArray );
        write(stringArray);

    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para uma chamada de função (Call).
     * @param  functionName nome da função a ser "chamada" pelo call.
     * @param  numArgs número de argumentos a serem passados na função call.
     */
    public void writeCall(String functionName, Integer numArgs) {
        List<String> commands = new ArrayList<String>();
        commands.add( "// Chamando função "+functionName );

        retHash.put(functionName, retHash.containsKey(functionName) ? (retHash.get(functionName) + 1) : 1);

        //push return-address
        commands.add( "@"+functionName+"$ret."+retHash.get(functionName) );
        commands.add( "D=A" );
        commands.add( "@SP");
        commands.add( "A=M" );
        commands.add( "M=D" );
        commands.add( "@SP" );
        commands.add( "M=M+1" );
        
        //push LCL
        commands.add( "@LCL" );
        commands.add( "D=M" );
        commands.add( "@SP");
        commands.add( "A=M" );
        commands.add( "M=D" );
        commands.add( "@SP" );
        commands.add( "M=M+1" );

        //push ARG
        commands.add( "@ARG" );
        commands.add( "D=M" );
        commands.add( "@SP");
        commands.add( "A=M" );
        commands.add( "M=D" );
        commands.add( "@SP" );
        commands.add( "M=M+1" );

        //push THIS
        commands.add( "@THIS" );
        commands.add( "D=M" );
        commands.add( "@SP");
        commands.add( "A=M" );
        commands.add( "M=D" );
        commands.add( "@SP" );
        commands.add( "M=M+1" );

        //push THAT
        commands.add( "@THAT" );
        commands.add( "D=M" );
        commands.add( "@SP");
        commands.add( "A=M" );
        commands.add( "M=D" );
        commands.add( "@SP" );
        commands.add( "M=M+1" );

        //ARG = SP-n-5
        commands.add( "@"+String.valueOf(numArgs+5) );
        commands.add( "D=A" );
        commands.add( "@SP");
        commands.add( "A=M" );
        commands.add( "D=A-D" );
        commands.add( "@ARG" );
        commands.add( "M=D" );

        //LCL = SP
        commands.add( "@SP");
        commands.add( "D=M" );
        commands.add( "@LCL" );
        commands.add( "M=D" );

        //goto f
        commands.add( "@"+functionName);
        commands.add( "0;JMP" );

        //(return-address)
        commands.add( "("+functionName+"$ret."+retHash.get(functionName)+")" );

        String[] stringArray = new String[ commands.size() ];
        commands.toArray( stringArray );
        write(stringArray);
    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para o retorno de uma sub rotina.
     */
    public void writeReturn() {

        List<String> commands = new ArrayList<String>();
        commands.add( "// Retorno de função " );

        //FRAME = LCL
        commands.add( "@LCL" );
        commands.add( "D=M" );
        commands.add( "@R13" ); //guarda FRAME
        commands.add( "M=D" );

        //RET = *(FRAME-5)
        commands.add( "@5" );
        commands.add( "A=D-A" ); 
        commands.add( "D=M" );
        commands.add( "@R14" ); //guarda RET
        commands.add( "M=D" );        

        //*ARG = pop()
        commands.add( "@ARG" );
        commands.add( "D=M" );
        commands.add( "@R15");
        commands.add( "M=D" );
        commands.add( "@SP" );
        commands.add( "AM=M-1" );
        commands.add( "D=M" );
        commands.add( "@R15");
        commands.add( "A=M" );
        commands.add( "M=D" );

        //SP = ARG+1
        commands.add( "@ARG");
        commands.add( "D=M" );
        commands.add( "@SP" );
        commands.add( "M=D+1");

        //THAT = *(FRAME-1)
        commands.add( "@R13");
        commands.add( "D=M-1" );
        commands.add( "M=D" ); // faz FRAME--
        commands.add( "A=D" );        
        commands.add( "D=M");
        commands.add( "@THAT");
        commands.add( "M=D");

        //THIS = *(FRAME-2)
        commands.add( "@R13");
        commands.add( "D=M-1" );
        commands.add( "M=D" ); // faz FRAME--
        commands.add( "A=D" );        
        commands.add( "D=M");
        commands.add( "@THIS");
        commands.add( "M=D");

        //ARG = *(FRAME-3)
        commands.add( "@R13");
        commands.add( "D=M-1" );
        commands.add( "M=D" ); // faz FRAME--
        commands.add( "A=D" );        
        commands.add( "D=M");
        commands.add( "@ARG");
        commands.add( "M=D");

        //LCL = *(FRAME-4)
        commands.add( "@R13");
        commands.add( "D=M-1" );
        commands.add( "M=D" ); // faz FRAME--
        commands.add( "A=D" );        
        commands.add( "D=M");
        commands.add( "@LCL");
        commands.add( "M=D");

        //goto RET
        commands.add( "@14");
        commands.add( "A=M");
        commands.add( "0;JMP");

        String[] stringArray = new String[ commands.size() ];
        commands.toArray( stringArray );
        write(stringArray);

    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para a declaração de uma função.
     * @param  functionName nome da função a ser criada.
     * @param  numLocals número de argumentos a serem passados na função call.
     */
    public void writeFunction(String functionName, Integer numLocals) {

        List<String> commands = new ArrayList<String>();
        commands.add( "// Declarando função "+functionName );

        //(f)
        commands.add( "("+functionName+")" );

        //repeat k times:
        for(int i=0;i<numLocals;i++) {
            //PUSH 0
            commands.add( "@0" );
            commands.add( "D=A" );
            commands.add( "@SP" );
            commands.add( "A=M" );
            commands.add( "M=D" );
            commands.add( "@SP" );
            commands.add( "M=M+1" );
        }
   
        String[] stringArray = new String[ commands.size() ];
        commands.toArray( stringArray );
        write(stringArray);
    }

    /**
     * Armazena o nome do arquivo vm de origem.
     * Usado para definir os dados estáticos do código (por arquivo).
     * @param  filename nome do arquivo sendo tratado.
     */
    public void vmfile(String file) {

        int i = file.lastIndexOf(File.separator);
        int j = file.lastIndexOf('.');
        this.filename = file.substring(i+1,j);

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
