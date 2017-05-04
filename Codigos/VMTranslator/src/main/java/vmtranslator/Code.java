/**
 * Curso: Elementos de Sistemas
 * Arquivo: Code.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 2/05/2017
 */

package vmtranslator;

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

            commands.add( "leaw $SP,%A" );
            commands.add( "movw (%A),%D" );
            commands.add( "decw %D" );
            commands.add( "movw %D,(%A)" );

            commands.add( "movw (%A),%A" );
            commands.add( "movw (%A),%D");

            commands.add( "leaw $SP,%A" );
            commands.add( "subw (%A),$1,%A" );
            
            commands.add( "addw (%A),%D,%D" );
            
            commands.add( "movw %D,(%A)" );
            
        } else if (command.equals("sub")) {

            commands.add( "// SUB" );

            commands.add( "leaw $SP,%A" );
            commands.add( "movw (%A),%D" );
            commands.add( "decw %D" );
            commands.add( "movw %D,(%A)" );

            commands.add( "movw (%A),%A" );
            commands.add( "movw (%A),%D");

            commands.add( "leaw $SP,%A" );
            commands.add( "subw (%A),$1,%A" );
            
            commands.add( "subw (%A),%D,%D" );
            
            commands.add( "movw %D,(%A)" );

        } else if (command.equals("neg")) {

            commands.add( "// NEG" );

            commands.add( "leaw $SP,%A" );
            commands.add( "subw (%A),$1,%A" );

            commands.add( "movw (%A),%D" );
            commands.add( "negw %D" );
            commands.add( "movw %D,(%A)" );

        } else if (command.equals("eq")) {

            commands.add( "// EQ" );

            commands.add( "leaw $SP,%A" );
            commands.add( "movw (%A),%D" );
            commands.add( "decw %D" );
            commands.add( "movw %D,(%A)" );

            commands.add( "movw (%A),%A" );
            commands.add( "movw (%A),%D");

            commands.add( "leaw $SP,%A" );
            commands.add( "subw (%A),$1,%A" );
            
            commands.add( "subw (%A),%D,%D" );

            commands.add("leaw $EQ"+filename+labelCounter+",%A");
            commands.add("je");
            commands.add("leaw $SP,%A");
            commands.add("subw (%A),$1,%A");
            commands.add("movw $0,(%A)");
            commands.add("leaw $EQ2"+filename+labelCounter+",%A");
            commands.add("jmp");
            commands.add("EQ"+filename+labelCounter+":");
            commands.add("leaw $SP,%A");
            commands.add("subw (%A),$1,%A");
            commands.add("movw $-1,(%A)");
            commands.add("EQ2"+filename+labelCounter+":");
            labelCounter += 1;

        } else if (command.equals("gt")) {

            commands.add( "// GT" );

            commands.add( "leaw $SP,%A" );
            commands.add( "movw (%A),%D" );
            commands.add( "decw %D" );
            commands.add( "movw %D,(%A)" );

            commands.add( "movw (%A),%A" );
            commands.add( "movw (%A),%D");

            commands.add( "leaw $SP,%A" );
            commands.add( "subw (%A),$1,%A" );
            
            commands.add( "subw (%A),%D,%D" );

            commands.add("leaw $GT"+filename+labelCounter+":");
            commands.add("jg");
            commands.add("leaw $SP,%A");
            commands.add("subw (%A),$1,%A");
            commands.add("movw $0,(%A)");
            commands.add("leaw $GT2"+filename+labelCounter+":");
            commands.add("jmp");
            commands.add("GT"+filename+labelCounter+":");
            commands.add("leaw $SP,%A");
            commands.add("subw (%A),$1,%A");
            commands.add("movw $-1,(%A)");
            commands.add("GT2"+filename+labelCounter+":");
            labelCounter += 1;

        } else if (command.equals("lt")) {

            commands.add( "// LT" );

            commands.add( "leaw $SP,%A" );
            commands.add( "movw (%A),%D" );
            commands.add( "decw %D" );
            commands.add( "movw %D,(%A)" );

            commands.add( "movw (%A),%A" );
            commands.add( "movw (%A),%D");

            commands.add( "leaw $SP,%A" );
            commands.add( "subw (%A),$1,%A" );
            
            commands.add( "subw (%A),%D,%D" );

            commands.add("leaw $LT"+filename+labelCounter+":");
            commands.add("jl");
            commands.add("leaw $SP,%A");
            commands.add("subw (%A),$1,%A");
            commands.add("movw $0,(%A)");
            commands.add("leaw $LT2"+filename+labelCounter+":");
            commands.add("jmp");
            commands.add("LT"+filename+labelCounter+":");
            commands.add("leaw $SP,%A");
            commands.add("subw (%A),$1,%A");
            commands.add("movw $-1,(%A)");
            commands.add("LT2"+filename+labelCounter+":");
            labelCounter += 1;

        } else if (command.equals("and")) {

            commands.add( "// AND" );

            commands.add( "leaw $SP,%A" );
            commands.add( "movw (%A),%D" );
            commands.add( "decw %D" );
            commands.add( "movw %D,(%A)" );

            commands.add( "movw (%A),%A" );
            commands.add( "movw (%A),%D");

            commands.add( "leaw $SP,%A" );
            commands.add( "subw (%A),$1,%A" );
            
            commands.add( "andw (%A),%D,%D" );
            
            commands.add( "movw %D,(%A)" );

        } else if (command.equals("or")) {

            commands.add( "// OR" );

            commands.add( "leaw $SP,%A" );
            commands.add( "movw (%A),%D" );
            commands.add( "decw %D" );
            commands.add( "movw %D,(%A)" );

            commands.add( "movw (%A),%A" );
            commands.add( "movw (%A),%D");

            commands.add( "leaw $SP,%A" );
            commands.add( "subw (%A),$1,%A" );
            
            commands.add( "orw (%A),%D,%D" );
            
            commands.add( "movw %D,(%A)" );

        } else if (command.equals("not")) {

            commands.add( "// NOT" );

            commands.add( "leaw $SP,%A" );
            commands.add( "subw (%A),$1,%A" );

            commands.add( "movw (%A),%D" );
            commands.add( "notw %D" );
            commands.add( "movw %D,(%A)" );

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
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "decw %D" );
                commands.add( "movw %D,(%A)" );

                commands.add( "leaw $"+String.valueOf(index)+",%A" );
                commands.add( "movw %A,%D" );
                commands.add( "leaw $LCL,%A");
                commands.add( "addw (%A),%D,%D" );

                commands.add( "leaw $R15,%A");
                commands.add( "movw %D,(%A)" );

                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%A" );
                commands.add( "movw (%A),%D" );

                commands.add( "leaw $R15,%A");
                commands.add( "movw (%A),%A" );
                commands.add( "movw %D,(%A)" );

            } else if (segment.equals("argument")) {
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "decw %D" );
                commands.add( "movw %D,(%A)" );

                commands.add( "leaw $"+String.valueOf(index)+",%A" );
                commands.add( "movw %A,%D" );
                commands.add( "leaw $ARG,%A");
                commands.add( "addw (%A),%D,%D" );

                commands.add( "leaw $R15,%A");
                commands.add( "movw %D,(%A)" );

                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%A" );
                commands.add( "movw (%A),%D" );

                commands.add( "leaw $R15,%A");
                commands.add( "movw (%A),%A" );
                commands.add( "movw %D,(%A)" );
 
            } else if (segment.equals("this")) {
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "decw %D" );
                commands.add( "movw %D,(%A)" );

                commands.add( "leaw $"+String.valueOf(index)+",%A" );
                commands.add( "movw %A,%D" );
                commands.add( "leaw $THIS,%A");
                commands.add( "addw (%A),%D,%D" );

                commands.add( "leaw $R15,%A");
                commands.add( "movw %D,(%A)" );

                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%A" );
                commands.add( "movw (%A),%D" );

                commands.add( "leaw $R15,%A");
                commands.add( "movw (%A),%A" );
                commands.add( "movw %D,(%A)" );

            } else if (segment.equals("that")) {
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "decw %D" );
                commands.add( "movw %D,(%A)" );

                commands.add( "leaw $"+String.valueOf(index)+",%A" );
                commands.add( "movw %A,%D" );
                commands.add( "leaw $THAT,%A");
                commands.add( "addw (%A),%D,%D" );

                commands.add( "leaw $R15,%A");
                commands.add( "movw %D,(%A)" );

                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%A" );
                commands.add( "movw (%A),%D" );

                commands.add( "leaw $R15,%A");
                commands.add( "movw (%A),%A" );
                commands.add( "movw %D,(%A)" );

            } else if (segment.equals("static")) {
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "decw %D" );
                commands.add( "movw %D,(%A)" );
                commands.add( "movw (%A),%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "leaw $"+filename+"."+String.valueOf(index)+",%A" );
                commands.add( "movw %D,(%A)" );
            } else if (segment.equals("temp")) {
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "decw %D" );
                commands.add( "movw %D,(%A)" );
                commands.add( "movw (%A),%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "leaw $"+String.valueOf(index+5)+",%A" );
                commands.add( "movw %D,(%A)" );
            } else if (segment.equals("pointer")) {
                if(index==0) {
                    commands.add( "leaw $SP,%A" );
                    commands.add( "movw (%A),%D" );
                    commands.add( "decw %D" );
                    commands.add( "movw %D,(%A)" );
                    commands.add( "movw (%A),%A" );
                    commands.add( "movw (%A),%D" );
                    commands.add( "leaw $THIS,%A" );
                    commands.add( "movw %D,(%A)" );
                } else {
                    commands.add( "leaw $SP,%A" );
                    commands.add( "movw (%A),%D" );
                    commands.add( "decw %D" );
                    commands.add( "movw %D,(%A)" );
                    commands.add( "movw (%A),%A" );
                    commands.add( "movw (%A),%D" );
                    commands.add( "leaw $THAT,%A" );
                    commands.add( "movw %D,(%A)" );
                }
            }
        } else if (command == Parser.CommandType.C_PUSH) {
            commands.add( "// PUSH "+segment+" "+String.valueOf(index));
            if (segment.equals("constant")) {
                commands.add( "leaw $"+String.valueOf(index)+",%A" );
                commands.add( "movw %A,%D" );
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%A" );
                commands.add( "movw %D,(%A)" );
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "incw %D" );
                commands.add( "movw %D,(%A)" );
            } else if (segment.equals("local")) {
                commands.add( "leaw $"+String.valueOf(index)+",%A" );
                commands.add( "movw %A,%D" );
                commands.add( "leaw $LCL,%A");
                commands.add( "addw (%A),%D,%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%A" );
                commands.add( "movw %D,(%A)" );
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "incw %D" );
                commands.add( "movw %D,(%A)" );
            } else if (segment.equals("argument")) {
                commands.add( "leaw $"+String.valueOf(index)+",%A" );
                commands.add( "movw %A,%D" );
                commands.add( "leaw $ARG,%A");
                commands.add( "addw (%A),%D,%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%A" );
                commands.add( "movw %D,(%A)" );
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "incw %D" );
                commands.add( "movw %D,(%A)" );
            } else if (segment.equals("this")) {
                commands.add( "leaw $"+String.valueOf(index)+",%A" );
                commands.add( "movw %A,%D" );
                commands.add( "leaw $THIS,%A");
                commands.add( "addw (%A),%D,%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%A" );
                commands.add( "movw %D,(%A)" );
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "incw %D" );
                commands.add( "movw %D,(%A)" );
            } else if (segment.equals("that")) {
                commands.add( "leaw $"+String.valueOf(index)+",%A" );
                commands.add( "movw %A,%D" );
                commands.add( "leaw $THAT,%A");
                commands.add( "addw (%A),%D,%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%A" );
                commands.add( "movw %D,(%A)" );
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "incw %D" );
                commands.add( "movw %D,(%A)" );
            } else if (segment.equals("static")) {
                commands.add( "leaw $"+filename+"."+String.valueOf(index)+",%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%A" );
                commands.add( "movw %D,(%A)" );
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "incw %D" );
                commands.add( "movw %D,(%A)" );
            } else if (segment.equals("temp")) {
                commands.add( "leaw $"+String.valueOf(index+5)+",%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%A" );
                commands.add( "movw %D,(%A)" );
                commands.add( "leaw $SP,%A" );
                commands.add( "movw (%A),%D" );
                commands.add( "incw %D" );
                commands.add( "movw %D,(%A)" );
            } else if (segment.equals("pointer")) {
                if(index==0) {
                    commands.add( "leaw $THIS,%A" );
                    commands.add( "movw (%A),%D" );
                    commands.add( "leaw $SP,%A" );
                    commands.add( "movw (%A),%A" );
                    commands.add( "movw %D,(%A)" );
                    commands.add( "leaw $SP,%A" );
                    commands.add( "movw (%A),%D" );
                    commands.add( "incw %D" );
                    commands.add( "movw %D,(%A)" );
                } else {
                    commands.add( "leaw $THAT,%A" );
                    commands.add( "movw (%A),%D" );
                    commands.add( "leaw $SP,%A" );
                    commands.add( "movw (%A),%A" );
                    commands.add( "movw %D,(%A)" );
                    commands.add( "leaw $SP,%A" );
                    commands.add( "movw (%A),%D" );
                    commands.add( "incw %D" );
                    commands.add( "movw %D,(%A)" );
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

        commands.add( "leaw $256,%A" );
        commands.add( "movw %A,%D" );
        commands.add( "leaw $SP,%A" );
        commands.add( "movw %D,(%A)");

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

        commands.add( label+":" );

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

        commands.add( "leaw $"+label+",%A" );
        commands.add( "jmp" );

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

        commands.add( "leaw $SP,%A" );
        commands.add( "movw (%A),%D" );
        commands.add( "decw %D" );
        commands.add( "movw %D,(%A)" );
        commands.add( "movw (%A),%A" );
        commands.add( "movw (%A),%D" );
        
        commands.add( "leaw $"+label+",%A" );
        commands.add( "jne" );

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
        commands.add( "leaw $"+functionName+"$ret."+retHash.get(functionName)+",%A" );
        commands.add( "movw %A,%D" );
        commands.add( "leaw $SP,%A");
        commands.add( "movw (%A),%A" );
        commands.add( "movw %D,(%A)" );
        commands.add( "leaw $SP,%A" );
        commands.add( "movw (%A),%D" );
        commands.add( "incw %D" );
        commands.add( "movw %D,(%A)" );
        
        //push LCL
        commands.add( "leaw $LCL,%A" );
        commands.add( "movw (%A),%D" );
        commands.add( "leaw $SP,%A");
        commands.add( "movw (%A),%A" );
        commands.add( "movw %D,(%A)" );
        commands.add( "leaw $SP,%A" );
        commands.add( "movw (%A),%D" );
        commands.add( "incw %D" );
        commands.add( "movw %D,(%A)" );

        //push ARG
        commands.add( "leaw $ARG,%A" );
        commands.add( "movw (%A),%D" );
        commands.add( "leaw $SP,%A");
        commands.add( "movw (%A),%A" );
        commands.add( "movw %D,(%A)" );
        commands.add( "leaw $SP,%A" );
        commands.add( "movw (%A),%D" );
        commands.add( "incw %D" );
        commands.add( "movw %D,(%A)" );

        //push THIS
        commands.add( "leaw $THIS,%A" );
        commands.add( "movw (%A),%D" );
        commands.add( "leaw $SP,%A");
        commands.add( "movw (%A),%A" );
        commands.add( "movw %D,(%A)" );
        commands.add( "leaw $SP,%A" );
        commands.add( "movw (%A),%D" );
        commands.add( "incw %D" );
        commands.add( "movw %D,(%A)" );

        //push THAT
        commands.add( "leaw $THAT,%A" );
        commands.add( "movw (%A),%D" );
        commands.add( "leaw $SP,%A");
        commands.add( "movw (%A),%A" );
        commands.add( "movw %D,(%A)" );
        commands.add( "leaw $SP,%A" );
        commands.add( "movw (%A),%D" );
        commands.add( "incw %D" );
        commands.add( "movw %D,(%A)" );

        //ARG = SP-n-5
        commands.add( "leaw $"+String.valueOf(numArgs+5)+",%A" );
        commands.add( "movw %A,%D" );
        commands.add( "leaw $SP,%A");
        commands.add( "movw (%A),%A" );
        commands.add( "subw (%A),%D,%D" );
        commands.add( "leaw $ARG,%A" );
        commands.add( "movw %D,(%A)" );

        //LCL = SP
        commands.add( "leaw $SP,%A");
        commands.add( "movw (%A),%D" );
        commands.add( "leaw $LCL,%A" );
        commands.add( "movw %D,(%A)" );

        //goto f
        commands.add( "leaw $"+functionName+",%A");
        commands.add( "jmp" );

        //(return-address)
        commands.add( functionName+"$ret."+retHash.get(functionName)+":" );

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
        commands.add( "leaw $LCL,%A" );
        commands.add( "movw (%A),%D" );
        commands.add( "leaw $R13,%A" ); //guarda FRAME
        commands.add( "movw %D,(%A)" );

        //RET = *(FRAME-5)
        commands.add( "leaw $5,%A" );
        commands.add( "subw %D,(%A),%A" ); 
        commands.add( "movw (%A),%D" );
        commands.add( "leaw $R14,%A" ); //guarda RET
        commands.add( "movw %D,(%A)" );        

        //*ARG = pop()
        commands.add( "leaw $ARG,%A" );
        commands.add( "movw (%A),%D" );
        commands.add( "leaw $R15,%A");
        commands.add( "movw %D,(%A)" );
        commands.add( "leaw $SP,%A" );
        commands.add( "movw (%A),%D" );
        commands.add( "decw %D" );
        commands.add( "movw %D,(%A)" );
        commands.add( "movw %D,%A" );
        commands.add( "movw (%A),%D" );
        commands.add( "leaw $R15,%A");
        commands.add( "movw (%A),%A" );
        commands.add( "movw %D,(%A)" );

        //SP = ARG+1
        commands.add( "leaw $ARG,%A");
        commands.add( "movw (%A),%D" );
        commands.add( "leaw $SP,%A" );
        commands.add( "addw %D,$1,(%A)");

        //THAT = *(FRAME-1)
        commands.add( "leaw $R13,%A");
        commands.add( "subw (%A),$1,%D" );
        commands.add( "movw %D,(%A)" ); // faz FRAME--
        commands.add( "movw %D,%A" );        
        commands.add( "movw (%A),%D");
        commands.add( "leaw $THAT,%A");
        commands.add( "movw %D,(%A)");

        //THIS = *(FRAME-2)
        commands.add( "leaw $R13,%A");
        commands.add( "subw (%A),$1,%D" );
        commands.add( "movw %D,(%A)" ); // faz FRAME--
        commands.add( "movw %D,%A" );        
        commands.add( "movw (%A),%D");
        commands.add( "leaw $THIS,%A");
        commands.add( "movw %D,(%A)");

        //ARG = *(FRAME-3)
        commands.add( "leaw $R13,%A");
        commands.add( "subw (%A),$1,%D" );
        commands.add( "movw %D,(%A)" ); // faz FRAME--
        commands.add( "movw %D,%A" );        
        commands.add( "movw (%A),%D");
        commands.add( "leaw $ARG,%A");
        commands.add( "movw %D,(%A)");

        //LCL = *(FRAME-4)
        commands.add( "leaw $R13,%A");
        commands.add( "subw (%A),$1,%D" );
        commands.add( "movw %D,(%A)" ); // faz FRAME--
        commands.add( "movw %D,%A" );        
        commands.add( "movw (%A),%D");
        commands.add( "leaw $LCL,%A");
        commands.add( "movw %D,(%A)");

        //goto RET
        commands.add( "leaw $14,%A");
        commands.add( "movw (%A),%A");
        commands.add( "jmp");

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
            commands.add( "leaw $0,%A" );
            commands.add( "movw %A,%D" );
            commands.add( "leaw $SP,%A" );
            commands.add( "movw (%A),%A" );
            commands.add( "movw %D,(%A)" );
            commands.add( "leaw $SP,%A" );
            commands.add( "movw (%A),%D" );
            commands.add( "incw %D" );
            commands.add( "movw %D,(%A)" );
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
