/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 04/02/2017
 */

package assembler;

import java.io.*;

/**
 * Classe principal que orquestra execução do Assembler.
 * Opções:
 *   <arquivo nasm>      primeiro parametro é o nome do arquivo nasm a ser aberto 
 *   -f <arquivo mif>    parametro indica onde será salvo o arquivo gerado .mif
 */
class AssemblerZ0 {

    public static void main(String[] args) {

        if (args.length < 1)  // checa se arquivo foi passado
            Error.error("informe o nome do arquivo assembly Z0 nasm");

        String inputFile = null;
        String outputFileHack = null;
        String outputFileMif = null;
        boolean geraHack = false;
        boolean geraMif = false;
        boolean simulator = false;
        boolean debug = false;

        for (int i = 0; i < args.length; i++) {
            switch (args[i].charAt(0)) {
            case '-':
                if (args[i].charAt(1) == 'h') {
                    System.out.println("Opções");
                    System.out.println("<arquivo> : programa em linguagem de máquina a ser carregado");
                    System.out.println("-d : Mostra informações de debug");
                    System.out.println("-o <arquivo> : nome do arquivo para salvar no formto HACK)");
                    System.out.println("-f <arquivo> : nome do arquivo para salvar no formato MIF)");
                    System.out.println("-hack : gera arquivo hack");
                    System.out.println("-mif : gera arquivo mif");
                    System.out.println("-simulator : não testa limitações do hardware Z0");
                } else
                if (args[i].charAt(1) == 'd') {
                    debug = true;
                } else
                if (args[i].charAt(1) == 'h') {
                    geraHack = true;
                } else
                if (args[i].charAt(1) == 's') {
                    simulator = true;
                } else
                if (args[i].charAt(1) == 'm') {
                    geraMif = true;
                } else
                if (args[i].charAt(1) == 'o') {
                    geraHack = true;
                    outputFileHack = args[i+1]; // arquivo output
                    i++;
                } else
                if (args[i].charAt(1) == 'f') {
                    geraMif = true;
                    outputFileMif = args[i+1]; // arquivo output
                    i++;
                } else {
                    throw new IllegalArgumentException("Argumento não reconhecido: "+args[i]);
                }
                break;
            default:
                inputFile = args[i];
                break;
            }
        }

        if(!(geraMif||geraHack)) {
            geraMif = true;
        }

        try {
            Assemble assembler = new Assemble(inputFile, geraHack, outputFileHack, geraMif, outputFileMif, simulator, debug);
            assembler.assemble1();
            assembler.assemble2();
            assembler.close();
        } catch (FileNotFoundException ex){
            Error.error("Arquivo \'" + inputFile + "\' nao encontrado");
        } catch (IOException ex) {
            Error.error("uma excessao de i/o foi lancada");
        }
    }
}
