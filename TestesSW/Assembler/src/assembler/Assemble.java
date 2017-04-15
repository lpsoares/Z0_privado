/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by Luciano <lpsoares@insper.edu.br> 
 * Date: 04/02/2017
 */

package assembler;

import java.io.*;
import java.util.*;

public class Assemble {

    public static final int START_RAM_ADDRESS = 16;

    private String inputFile;   // arquivo de entrada
    
    String outputFileHack = null;
    String outputFileMif = null;

    File hackFile = null;
    File mifFile = null;

    private PrintWriter outHACK = null;    // grava saida do código de máquina em Hack
    private PrintWriter outMIF = null;    // grava saida do código de máquina em MIF

    boolean debug;              // flag que especifica se mensagens de debug são impressas
    boolean simulator;          // Testa se é para simulador e descarta limitações do hardware do Z0
    private SymbolTable table;  // tabela de símbolos (variáveis e marcadores)

    public Assemble(String inFile, boolean hack, String outFileHack, boolean mif, String outFileMif, boolean testSimulator, boolean debug) throws IOException {
        this.debug = debug;
        this.simulator = testSimulator;
        inputFile = inFile;

        if(hack) {
            if(outFileHack==null) {
                outputFileHack = inputFile.substring(0, inputFile.lastIndexOf('.')) + ".hack";
            } else {
                outputFileHack = outFileHack;
            }
            hackFile = new File(outputFileHack);
            outHACK = new PrintWriter(new FileWriter(hackFile));
        }
        
        if(mif) {
            if(outFileMif==null) {
                outputFileMif = inputFile.substring(0, inputFile.lastIndexOf('.')) + ".mif";
            } else {
                outputFileMif = outFileMif;
            }
            mifFile = new File(outputFileMif);
            outMIF = new PrintWriter(new FileWriter(mifFile));
        }

        table = new SymbolTable(); 
        table.initialize();  // Inicializa a tabela de simbolos
    }

    // primeiro passo para a construção da tabela de símbolos de marcadores (labels)
    public void assemble1() throws FileNotFoundException, IOException {
        Parser parser = new Parser(inputFile,this.simulator);
        int romAddress = 0;
        String symbol;
        while (parser.advance()) {
            if (parser.commandType() == Parser.CommandType.L_COMMAND) {
                symbol = parser.symbol();
                if (!table.contains(symbol))
                    table.addEntry(symbol, romAddress);
            } else {
                romAddress++;
                if (romAddress > 32768)    // aviso caso a memoria ROM tenha acabado
                    System.err.println("Aviso: toda a ROM disponível do Z0 foi usada");
            }
        }

        if(outMIF!=null) {
            outMIF.println("\nWIDTH=16;");
            outMIF.println("DEPTH="+romAddress+";");
            outMIF.println("\nADDRESS_RADIX=UNS;");
            outMIF.println("DATA_RADIX=BIN;");
            outMIF.println("\nCONTENT BEGIN");
        }

        parser.close();
        return;
    }

    // Segundo passo para a geração do código de máquina
    public void assemble2() throws FileNotFoundException, IOException{
        Parser parser = new Parser(inputFile,this.simulator);
        String dest, comp, jump;
        String symbol, value;
        int ramAddress = START_RAM_ADDRESS; // endereço de início das variáveis

        int line = 0;

        boolean flagNOP = false;    // para ser usado no teste de NOP depois um JUMP

        try {

        while (parser.advance()){
            if(this.debug) {
                System.out.println(parser.command());
            }
            if (parser.commandType() == Parser.CommandType.C_COMMAND) {
                value = parser.C();
            } else if (parser.commandType() == Parser.CommandType.A_COMMAND) {
                symbol = parser.symbol();
                value = Code.toBinary("0");
                if (Character.isDigit(symbol.charAt(0)) || 
                    (symbol.charAt(0) == '+' && Character.isDigit(symbol.charAt(1)) ) ){
                    value = "0" + Code.toBinary(symbol);
                } else if (symbol.charAt(0) == '-'){
                    System.err.println("Arquitetura não suporta a entrada de números negativos no LEAW");
                } else if (table.contains(symbol)) {
                    value = Integer.toString(table.getAddress(symbol));
                    value = "0" + Code.toBinary(value);
                } else { // avisos caso memória tenha estourado
                    if (ramAddress > 16383) 
                        System.err.println("Aviso: alocando variável em memória mapeada de I/O");
                    if (ramAddress > 24576)
                        System.err.println("Aviso: não há mais memória RAM disponível");
                    table.addEntry(symbol, ramAddress);
                    value = "0" + Code.toBinary("" + ramAddress);
                    ramAddress++;
                }

            } else {
                continue;
            }
            if(outHACK!=null) {
                outHACK.println(value);
            }
            if(outMIF!=null) {
                outMIF.println(String.format(" %5d", line)+" : "+value+";");
                line++;
            }

            if(!simulator) {
                if(flagNOP) {
                    if(value.charAt(0)=='1' && value.substring(10, 16).equals("000000")) {
                        //System.out.println("NOP");    
                        flagNOP = false;
                    } else {
                        Error.error("Uma instrução de JUMP precisa de um NOP após ela.");
                    }
                    flagNOP = false;
                } else if(value.charAt(0)=='1' && ( value.charAt(13)=='1' || value.charAt(14)=='1' || value.charAt(15)=='1' ) ) {
                    //System.out.println("JUMP");    
                    flagNOP = true;
                }
            }

               
        }

        } catch (InvalidAssemblyException ex) {
            close();
            delete();
            System.exit(1);
        }

        if(outMIF!=null) {
            outMIF.println("END;\n");
        }
        parser.close();

    }

    // fecha o arquivo de código de máquina
    public void close() throws IOException {
        if(outHACK!=null) {
           outHACK.close();
        } 
        if(outMIF!=null) {
           outMIF.close();
        } 
    }

    // remove o arquivo de código para casos de erros
    public void delete() {
        try{
            if(hackFile!=null) {
               hackFile.delete();
            } 
            if(mifFile!=null) {
               mifFile.delete();
            } 
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
