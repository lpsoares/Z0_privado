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
 * Responsável por abrir o arquivo para gravar instruções, possui funcionalidade para gravar as instruções.
 */
public class VMWriter {

    PrintWriter outputFile = null;  // PrintWriter usado para gravar as instruções no arquivo.

    /** 
     * Grava instruções no formato de máquina virtual a pilha.
     * @param filename nome do arquivo onde serão salvas as instruções em VM.
     */
    public VMWriter(String filename) {
        try {
            File file = new File(filename);
            outputFile = new PrintWriter(new FileWriter(file));
        } catch (FileNotFoundException e) {
            Error.error("Arquivo "+filename+" não pode ser aberto para gravar instruções VM");
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
     * Grava as instruções no arquivo de saída.
     * @param text texto que será gravado no arquivo de saída.
     */
    private void print(String text) {
        outputFile.println(text);        
    }

    /** 
     * Encontra o código do segmento e traduz para o texto usado em linguagem VM.
     * Exemplo: findSegment(Segment.CONST) ==> "constant".
     * @param segment código do segmento usado no VMWriter.
     * @return String com o texto usado em linguagem VM
     */
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

    /** 
     * Grava um comand "push" no arquivo de instruções VM.
     * @param segment código do segmento usado no VMWriter.
     * @param index índice do segmento de memória a ser usado..
     */
    public void writePush(Segment segment, Integer index) {
        String segmentName = findSegment(segment);
        print("push "+segmentName+" "+String.valueOf(index));
    }

    /** 
     * Grava um comand "pop" no arquivo de instruções VM.
     * @param segment código do segmento usado no VMWriter.
     * @param index índice do segmento de memória a ser usado..
     */
    public void writePop(Segment segment, Integer index) {
        String segmentName = findSegment(segment);
        print("pop "+segmentName+" "+String.valueOf(index));
    }

    /** 
     * Grava um comand aritmético no arquivo de instruções VM.
     * @param coomand código da instrução a ser salva em linguagem de VM.
     */
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

    /** 
     * Grava um marcador (Lable) no arquivo de instruções VM.
     * @param label nome de marcador a ser usado na linha do arquivo.
     */
    public void writeLabel(String label) {
        print("label "+label);
    }

    /** 
     * Grava uma instrução de goto (incondicional) no arquivo de instruções VM.
     * @param label nome do marcador para onde será realizado o salto de execução.
     */
    public void writeGoto(String label) {
        print("goto "+label);
    }

    /** 
     * Grava uma instrução de if-goto (goto condicional) no arquivo de instruções VM.
     * @param label nome do marcador para onde será realizado o salto de execução caso condição satisfeita.
     */
    public void writeIf(String label) {
        print("if-goto "+label);
    }

    /** 
     * Grava uma instrução call (usada para invocar uma subrotina) no arquivo de instruções VM.
     * @param name nome da subrotina a ser executada.
     * @param nArgs número de argumento que serão passados para a subrotina.
     */
    public void writeCall(String name, Integer nArgs) {
        print("call "+name+" "+String.valueOf(nArgs));
    }

    /** 
     * Declara uma função em linguagem VM no arquivo de instruções VM.
     * @param name nome da subrotina a ser criada.
     * @param nLocals número de espaços de memória local que devem ser reservados.
     */
    public void writeFunction(String name, Integer nLocals) {
        print("function "+name+" "+String.valueOf(nLocals));
    }

    /** 
     * Grava uma instrução de return no arquivo de instruções VM.
     */
    public void writeReturn() {
        print("return");

    }

    /** 
     * Grava um String, letra por letra, no arquivo de instruções VM.
     * Cada caracter é traduzido para seu código ASCII e colocado na pilha.
     * O módulo de Sistema Operaciona String.appendChar termina a execução.
     * @param text String a ser escrita em linguagem de máquina virtual à pilha.
     */
    public void writeString(String text) {
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
    public void close() {
        this.outputFile.close();
    }

}
