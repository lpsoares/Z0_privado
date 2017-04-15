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
    enum CommandType {
        A_COMMAND,      // comandos LEA, que armazenam no registrador A
        C_COMMAND,      // comandos de calculos
        L_COMMAND       // simbolos (tags)
    }

    // abre arquivo NASM
    public Parser(String file, boolean testSimulator) throws FileNotFoundException {
        inputFile = file;
        fileReader = new BufferedReader(new FileReader(file));
        lineNumber = 0;
        this.simulator = testSimulator;
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
    public CommandType commandType() {
        if (currentCommand.startsWith("lea")) {
            return CommandType.A_COMMAND;  // A_COMMAND for lea xxx
        } else if (currentCommand.endsWith(":")) {
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
    public String symbol() {
	    if (currentCommand.startsWith("lea")) {
	        String[] array = currentCommand.split("[ ,]+");
            return array[1].substring(1);
        } else {
        	return currentCommand.replace(":", "");
        }
    }
        
    // identifica o tipo de instrução C e salva no arquivo
    public String C()  throws InvalidAssemblyException {
        String comp = "";
        String dest = "";
        String jump = "";
        String[] array = currentCommand.split("[ ,]+");
        try {
          if (array[0].startsWith("mov")) {
            comp = array[1];
            dest = array[2];
            if(array.length==4) dest += " "+array[3];
            if(array.length==5) dest += " "+array[4];
          } else
          if (array[0].startsWith("add")) {
            comp = array[1]+"+"+array[2];
            dest = array[3];
            if(array.length==5) dest += " "+array[4];
            if(array.length==6) dest += " "+array[5];
          } else
          if (array[0].startsWith("sub")) {
            comp = array[1]+"-"+array[2];
            dest = array[3];
            if(array.length==5) dest += " "+array[4];
            if(array.length==6) dest += " "+array[5];
          } else
          if (array[0].startsWith("rsub")) {
            comp = array[2]+"-"+array[1];
            dest = array[3];
            if(array.length==5) dest += " "+array[4];
            if(array.length==6) dest += " "+array[5];
          } else
          if (array[0].startsWith("inc")) {
            comp = array[1]+"+1";
            dest = array[1];
          } else
          if (array[0].startsWith("dec")) {
            comp = array[1]+"-1";
            dest = array[1];
          } else
          if (array[0].startsWith("not")) {
            comp = "!"+array[1];
            dest = array[1];
          } else
          if (array[0].startsWith("neg")) {
            comp = "-"+array[1];
            dest = array[1];
          } else
          if (array[0].startsWith("and")) {
            comp = array[1]+"&"+array[2];
            dest = array[3];
            if(array.length==5) dest += " "+array[4];
            if(array.length==6) dest += " "+array[5];
          } else
          if (array[0].startsWith("or")) {
            comp = array[1]+"|"+array[2];
            dest = array[3];
            if(array.length==5) dest += " "+array[4];
            if(array.length==6) dest += " "+array[5];
          } else
          if ( array[0].startsWith("jg")  ||
               array[0].startsWith("je")  ||
               array[0].startsWith("jge") ||
               array[0].startsWith("jl")  ||
               array[0].startsWith("jne") ||
               array[0].startsWith("jle")
               ) {
            comp = "%D";
            jump = array[0];
          } else
          if (array[0].startsWith("jmp") ) {
            comp = "$0";
            jump = array[0];
          } else
          if (array[0].startsWith("nop")) {
            comp = "$0";
          } else {
            Error.error("Instrução não identificada", inputFile, lineNumber, currentLine);
            throw new InvalidAssemblyException();
          }

          String val = "111" + Code.comp(comp) + Code.dest(dest) + Code.jump(jump);

          if( (!this.simulator) && val.charAt(3)=='1' && val.charAt(12)=='1' ) {
            Error.error("Uma instrução não pode ler e gravar na memória RAM ao mesmo tempo", inputFile, lineNumber, currentLine);
            throw new InvalidAssemblyException();
          }

          return(val);  // salva no arquivo a instrução

        } catch (InvalidDestException ex) {
            Error.error("Tentando salvar dados em um local inválido da CPU", inputFile, lineNumber, currentLine);
            throw new InvalidAssemblyException();
        } catch (InvalidCompException ex) {
            Error.error("Instrução inválida", inputFile, lineNumber, currentLine);
            throw new InvalidAssemblyException();
        } catch (InvalidJumpException ex) {
            Error.error("Instrução de jump inválida", inputFile, lineNumber, currentLine);
            throw new InvalidAssemblyException();
        }

    }

    // fecha o arquivo de leitura
    public void close() throws IOException {
        fileReader.close();
    }
}
