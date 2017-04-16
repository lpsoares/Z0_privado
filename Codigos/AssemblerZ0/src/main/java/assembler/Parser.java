/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 04/02/2017
 */

package assembler;

import java.io.*;

public class Parser {

    public String currentCommand = "";  // comando atual
    public String inputFile;				    // arquivo de leitura
    public int lineNumber = 0;				  // linha atual do arquivo (nao do codigo gerado)
    public String currentLine;				  // linha de codigo atual
    boolean simulator;                  // Testa se é para simulador e descarta limitações do hardware do Z0
    private BufferedReader fileReader;  // leitor de arquivo

    // tipos de comandos
    public enum CommandType {
        A_COMMAND,      // comandos LEA, que armazenam no registrador A
        C_COMMAND,      // comandos de calculos
        L_COMMAND       // simbolos (tags)
    }

    // abre arquivo NASM
    public Parser(String file) throws FileNotFoundException {
        this.inputFile = file;
        this.fileReader = new BufferedReader(new FileReader(file));
        this.lineNumber = 0;
        this.simulator = false;
    }

    // Em caso de simulação, vai permitir certas operações
    public void setSimulator(boolean testSimulator) {
      this.simulator = testSimulator;
    }
    
    public boolean getSimulator() {
      return this.simulator;
    }

    // carrega proximo comando e faz ele o comando atual
    public boolean advance() throws IOException {
        while(true){
            currentLine = fileReader.readLine();
            lineNumber++;
            if (currentLine == null)
                return false;  // caso não haja mais comandos
            currentCommand = currentLine.replaceAll(";.*$", "").trim();
            if (currentCommand.equals(""))
                continue;
            return true;   // caso um comando seja encontrado
        }
    }

    // informa qual o tipo do comando atual
    public CommandType commandType(String command) {
        if (command.startsWith("lea")) {
            return CommandType.A_COMMAND;  // A_COMMAND for lea xxx
        } else if (command.endsWith(":")) {
            return CommandType.L_COMMAND;  // L_COMMAND for a label, xxx:
        } else {
            return CommandType.C_COMMAND;  // C_COMMAND for mov, etc...
        }
    }

    // somente retorna o comando atual
    public String command() {
      return currentCommand;
    }

    // retorna o simbolo ou valor decimal do comando atual
    public String label(String command) {
      return command.replace(":", "");
    }

    // retorna o simbolo ou valor decimal do comando atual
    public String symbol(String command) {
        String[] array = command.split("[ ,]+");
        return array[1].substring(1);
    }

    // identifica o tipo de instrução C e salva no arquivo
    public String[] instruction(String command)  throws InvalidCompException,InvalidAssemblyException,InvalidDestException,InvalidJumpException {
        return command.split("[ ,]+");
    }

    // fecha o arquivo de leitura
    public void close() throws IOException {
        fileReader.close();
    }
}
