/**
 * Curso: Elementos de Sistemas
 * Arquivo: VMTranslator.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 30/04/2017
 */

//package vmtranslator;

import java.io.*;

/**
 * Classe principal que orquestra a tradução do arquivo em linguagem de máquina virtual à pilha.
 * Opções:
 *   <arquivo vm>         primeiro parametro é o nome do arquivo vm a ser aberto 
 *   -o <arquivo nasm>    parametro indica onde será salvo o arquivo gerado .nasm
 */
class VMTranslator {

    public static void main(String[] args) {

        if (args.length < 1)  // checa se arquivo foi passado
            Error.error("informe o nome do arquivo vm");

        String inputFilename = null;
        String outputFilename = null;
        boolean debug = false;

        for (int i = 0; i < args.length; i++) {
            switch (args[i].charAt(0)) {
            case '-':
                if (args[i].charAt(1) == 'h') {
                    System.out.println("Opções");
                    System.out.println("<arquivo> : programa em linguagem de máquina a ser carregado");
                    System.out.println("-o <arquivo> : nome do arquivo para salvar no formato NASM)");
                } else
                if (args[i].charAt(1) == 'o') {
                    outputFilename = args[i+1]; // arquivo output
                    i++;
                } else {
                    Error.error("Argumento não reconhecido: "+args[i]);
                }
                break;
            default:
                inputFilename = args[i];
                break;
            }
        }

        try {

            if(outputFilename==null) {
                outputFilename = inputFilename.substring(0, inputFilename.lastIndexOf('.')) + ".asm";
            }

            Parser parser = new Parser(inputFilename);
            Code code = new Code(outputFilename);

            // Avança enquanto houver linhas para traduzir
            while (parser.advance()){
                if(debug) {System.out.println(parser.command());}
                
                
                if( parser.commandType(parser.command())==Parser.CommandType.C_PUSH ||
                    parser.commandType(parser.command())==Parser.CommandType.C_POP
                ) {
                    code.writePushPop(parser.commandType(parser.command()),
                                                  parser.arg1(parser.command()),
                                                  parser.arg2(parser.command())
                                                  );
                } else if( parser.commandType(parser.command())==Parser.CommandType.C_ARITHMETIC) {
                    code.writeArithmetic(parser.command());
                } else {
                    Error.error("Comando não reconhecido");
                }

            }
            parser.close();
            code.close();

        } catch (FileNotFoundException e){
            Error.error("Arquivo \'" + inputFilename + "\' nao encontrado");
        } catch (IOException e) {
            Error.error("uma excessao de i/o foi lancada");
        }
    }
}
