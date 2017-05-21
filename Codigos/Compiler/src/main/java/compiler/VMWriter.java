/**
 * Curso: Elementos de Sistemas
 * Arquivo: VMWriter.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 6/05/2017
 */


//output module for generating VM code;

package compiler;

import java.io.*;

/**
 * Encapsula o código de leitura. Carrega as instruções na linguagem de máquina virtual a pilha,
 * analisa, e oferece acesso aos comandos.
 * Além disso, remove todos os espaços em branco e comentários.
 */
public class VMWriter {

    public String currentCommand = "";  // comando atual
    private BufferedReader fileReader;  // leitor de arquivo

    /** 
     * Abre o arquivo de entrada VM e se prepara para analisá-lo.
     * @param file arquivo VM que será feito o parser.
     */
    public VMWriter(String file) throws FileNotFoundException {
        try {
            this.fileReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {

        }
    }

    /** Enumerator para os kind. */
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

    /** Enumerator para os kind. */
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

    // Writes a VM push command.
    public void writePush(Segment segment, Integer index) {
        
    }

    // Writes a VM pop command.
    public void writePop(Segment segment, Integer index) {

    }

    // Writes a VM arithmetic command.
    public void WriteArithmetic(Command command) {

    }

    // Writes a VM label command.
    public void WriteLabel(String label) {

    }

    // Writes a VM goto command.
    public void WriteGoto(String label) {

    }

    // Writes a VM If-goto command.
    public void WriteIf(String label) {

    }

    // Writes a VM call command.
    public void writeCall(String label) {

    }

    // Writes a VM function command.
    public void writeFunction(String label, Integer nLocals) {

    }

    // Writes a VM return command.
    public void writeReturn() {

    }


    // fecha o arquivo de leitura
    public void close() throws IOException {
        fileReader.close();
    }

}
