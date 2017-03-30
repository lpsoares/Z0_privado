import java.io.*;
import java.util.*;

public class Assemble {

    // input and output files
    private String inputFile;
    private PrintWriter out;

    // symbol table for variables / labels
    private SymbolTable table = new SymbolTable();

    public Assemble(String file) throws IOException {
        // define input / output files
        inputFile = file;
        String outputFile = inputFile.substring(0, inputFile.lastIndexOf('.')) + ".hack";
        out = new PrintWriter(new FileWriter(outputFile));
        
        // Inicializa a tabela de simbolos
        table.initialize();
    }

    // first pass of the assembler
    // go through file building the symbol table
    // only labels are handled, variables are handeled in the 2nd pass
    public void assemble1() throws FileNotFoundException, IOException
    {
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
                // print warning when memory is all used
                if (romAddress > 32768)
                    System.err.println("Aviso: toda a ROM disponível do Z0 foi usada");
            }
        }
        parser.close();
        return;
    }

    // 2nd pass of the assembler
    // handle variables
    // generate code, replace symbols with values from symbol table
    public void assemble2() throws FileNotFoundException, IOException{
        Parser parser = new Parser(inputFile);
        String dest, comp, jump;
        String symbol, value;
        // starting address for variables
        int ramAddress = 16;

        while (parser.advance()){
                
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
                    } else {
                        // print warnings about memory usage
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
        return;
    }

    // close output file
    public void close() throws IOException {
        out.close();
        return;
    }
}
