/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by Luciano <lpsoares@insper.edu.br> 
 * Date: 04/02/2017
 */

import java.io.*;
import java.util.*;

public class Assemble {

    public static final int START_RAM_ADDRESS = 16;

    private String inputFile;   // arquivo de entrada
    private PrintWriter out;    // grava saida do código de máquina
    boolean debug;              // flag que especifica se mensagens de debug são impressas
    private SymbolTable table;  // tabela de símbolos (variáveis e marcadores)

    public Assemble(String inFile, String outFile, boolean debug) throws IOException {
        this.debug = debug;
        inputFile = inFile;
        if(outFile==null) {
            String outputFile = inputFile.substring(0, inputFile.lastIndexOf('.')) + ".hack";
            out = new PrintWriter(new FileWriter(outputFile));
        } else {
            out = new PrintWriter(new FileWriter(outFile));
        }
        table = new SymbolTable(); 
        table.initialize();  // Inicializa a tabela de simbolos
    }

    // primeiro passo para a construção da tabela de símbolos de marcadores (labels)
    public void assemble1() throws FileNotFoundException, IOException {
        Parser parser = new Parser(inputFile);
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
        parser.close();
        return;
    }

    // Segundo passo para a geração do código de máquina
    public void assemble2() throws FileNotFoundException, IOException{
        Parser parser = new Parser(inputFile);
        String dest, comp, jump;
        String symbol, value;
        int ramAddress = START_RAM_ADDRESS; // endereço de início das variáveis

        while (parser.advance()){
            if(this.debug) {
                System.out.println(parser.command());
            }
            if (parser.commandType() == Parser.CommandType.C_COMMAND) {
                parser.C(out);
            } else if (parser.commandType() == Parser.CommandType.A_COMMAND) {
                symbol = parser.symbol();
                value = Code.toBinary("0");
                if (Character.isDigit(symbol.charAt(0)) || 
                    (symbol.charAt(0) == '+' && Character.isDigit(symbol.charAt(1)) ) ){
                    value = Code.toBinary(symbol);
                } else if (symbol.charAt(0) == '-'){
                    System.err.println("Arquitetura não suporta a entrada de números negativos no LEAW");
                } else if (table.contains(symbol)) {
                    value = Integer.toString(table.getAddress(symbol));
                    value = Code.toBinary(value);
                } else { // avisos caso memória tenha estourado
                    if (ramAddress > 16383) 
                        System.err.println("Aviso: alocando variável em memória mapeada de I/O");
                    if (ramAddress > 24576)
                        System.err.println("Aviso: não há mais memória RAM disponível");

                    table.addEntry(symbol, ramAddress);
                    value = Code.toBinary("" + ramAddress);
                    ramAddress++;
                }
                out.println("0" + value);
            }
        }
        parser.close();
    }

    // fecha o arquivo de código de máquina
    public void close() throws IOException {
        out.close();
    }
}
