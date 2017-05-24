/**
 * Curso: Elementos de Sistemas
 * Arquivo: VMWriter.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 6/05/2017
 */

package compiler;

import java.io.*;

/**
 * Encapsula o código para gravar as instruções em Liguagem de Máquina Virtual à Pilha.
 * Responsável por abrir o arquivo para gravar instruções, possui funcionalidades para gravar as instruções.
 */
public class VMWriter {

    PrintWriter outputFile = null;  // PrintWriter usado para gravar as instruções no arquivo.

    /** 
     * Grava instruções no formato de máquina virtual a pilha.
     * @param objeto File para o arquivo onde serão salvas as instruções em VM.
     */
    public VMWriter(File file) {
        try {
            //File file = new File(filename);
            outputFile = new PrintWriter(new FileWriter(file));
        } catch (FileNotFoundException e) {
            Error.error("Arquivo "+file.getName()+" não pode ser aberto para gravar instruções VM");
            System.exit(1);
        } catch (java.io.IOException e) {
            Error.error("uma excessao de i/o foi lancada");
            System.exit(1);
        }
    }

    /** Enumerator para os tipos de segmentos de memória do Z0. */
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

    /** Enumerator para os tipos de instruções aritméticas suportadas pelo Z0. */
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


    /** 
     * Grava um comand "push" no arquivo de instruções VM.
     * Adicionalmente o método tem a possibilidade de retornar a instrução gerada.
     * @param segment código do segmento usado no VMWriter.
     * @param index índice do segmento de memória a ser usado.
     * @return retorna a String do respectivo comando
     */
    public String writePush(Segment segment, Integer index) {
        String segmentName = findSegment(segment);
        String command = "push "+segmentName+" "+String.valueOf(index);
        print(command);
        return command;
    }

    /** 
     * Grava um comand "pop" no arquivo de instruções VM.
     * @param segment código do segmento usado no VMWriter.
     * @param index índice do segmento de memória a ser usado.
     * @return retorna a String do respectivo comando
     */
    public String writePop(Segment segment, Integer index) {
        String segmentName = findSegment(segment);
        String command = "pop "+segmentName+" "+String.valueOf(index);
        print(command);
        return command;
    }

    /** 
     * Grava um comand aritmético no arquivo de instruções VM.
     * @param coomand código da instrução a ser salva em linguagem de VM.
     * @return retorna a String do respectivo comando
     */
    public String writeArithmetic(Command command) {

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
        return(commandName);
    }

    /** 
     * Grava um marcador (Lable) no arquivo de instruções VM.
     * @param label nome de marcador a ser usado na linha do arquivo.
     * @return retorna a String do respectivo comando
     */
    public String writeLabel(String label) {
        String command = "label "+label;
        print(command);
        return(command);
    }

    /** 
     * Grava uma instrução de goto (incondicional) no arquivo de instruções VM.
     * @param label nome do marcador para onde será realizado o salto de execução.
     * @return retorna a String do respectivo comando
     */
    public String writeGoto(String label) {
        String command = "goto "+label;
        print(command);
        return(command);
    }

    /** 
     * Grava uma instrução de if-goto (goto condicional) no arquivo de instruções VM.
     * @param label nome do marcador para onde será realizado o salto de execução caso condição satisfeita.
     * @return retorna a String do respectivo comando
     */
    public String writeIf(String label) {
        String command = "if-goto "+label;
        print(command);
        return(command);
    }

    /** 
     * Grava uma instrução call (usada para invocar uma subrotina) no arquivo de instruções VM.
     * @param name nome da subrotina a ser executada.
     * @param nArgs número de argumento que serão passados para a subrotina.
     * @return retorna a String do respectivo comando
     */
    public String writeCall(String name, Integer nArgs) {
        String command = "call "+name+" "+String.valueOf(nArgs);
        print(command);
        return(command);
    }

    /** 
     * Declara uma função em linguagem VM no arquivo de instruções VM.
     * @param name nome da subrotina a ser criada.
     * @param nLocals número de espaços de memória local que devem ser reservados.
     * @return retorna a String do respectivo comando
     */
    public String writeFunction(String name, Integer nLocals) {
        String command = "function "+name+" "+String.valueOf(nLocals);
        print(command);
        return(command);
    }

    /** 
     * Grava uma instrução de return no arquivo de instruções VM.
     * @return retorna a String do respectivo comando
     */
    public String writeReturn() {
        String command = "return";
        print(command);
        return(command);
    }

    /** 
     * Grava um String, letra por letra, no arquivo de instruções VM.
     * Cada caracter é traduzido para seu código ASCII e colocado na pilha.
     * O módulo de Sistema Operaciona String.appendChar termina a execução.
     * @param text String a ser escrita em linguagem de máquina virtual à pilha.
     * @return retorna o numero de caracteres que foram detectados na String.
     */
    public Integer writeString(String text) {
        writePush(Segment.CONST,text.length());
        writeCall("String.new",1);
        for(int i=0;i<text.length();i++) {
            char character = text.charAt(i);
            int ascii = (int) character;
            writePush(Segment.CONST,ascii);
            writeCall("String.appendChar",2);
        }
        return(text.length());
    }

    /** 
     * Fecha o arquivo de leitura.
     * O arquivo deve ser fechado ao final da gravação, senão dados podem não ser gravados de fato.
     */
    public void close() {
        this.outputFile.close();
    }

    
    // Grava as instruções no arquivo de saída.
    // @param text texto que será gravado no arquivo de saída.
    private void print(String text) {
        outputFile.println(text);        
    }

    // Encontra o código do segmento e traduz para o texto usado em linguagem VM.
    // Exemplo: findSegment(Segment.CONST) ==> "constant".
    // @param segment código do segmento usado no VMWriter.
    // @return String com o texto usado em linguagem VM
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



}
