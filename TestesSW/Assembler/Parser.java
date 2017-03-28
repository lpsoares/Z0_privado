import java.io.*;

public class Parser {

    public String currentCommand = "";		// comando atual
    public String inputFile;				// arquivo de leitura
    public int lineNumber = 0;				// linha atual do arquivo (nao do codigo gerado)
    public String currentLine;				// linha de codigo atual
    private BufferedReader fileReader;		// leitor de arquivo

    // tipos de comandos
    enum CommandType {
        A_COMMAND,      // comandos LEA, que armazenam no registrador A
        C_COMMAND,      // comandos de calculos
        L_COMMAND       // simbolos (tags)
    }

    // abre arquivo NASM
    public Parser(String file) throws FileNotFoundException {
        inputFile = file;
        fileReader = new BufferedReader(new FileReader(file));
        lineNumber = 0;
    }

    // reads next command from input and makes it the curent command
    // returns true if command found
    // returns false at end of file
    public boolean advance() throws IOException {
        while (true){
            currentLine = fileReader.readLine();
            lineNumber++;
            if (currentLine == null)
                return false;
            currentCommand = currentLine.replaceAll(";.*$", "").trim();
            if (currentCommand.equals(""))
                continue;

            return true;
        }
    }

    // returns the type of the current command
    // A_COMMAND for lea xxx
    // L_COMMAND for a label, xxx:
    // C_COMMAND for mov, etc...
    public CommandType commandType() {
    
        if (currentCommand.startsWith("lea")) {
            return CommandType.A_COMMAND;
        } else if (currentCommand.endsWith(":")) {
            return CommandType.L_COMMAND;
        } else {
            return CommandType.C_COMMAND;
        }
    }

    // returns symbol or decimal xxx of the current command
    // only applies to A_COMMAND or L_COMMAND
    public String symbol() {
	    if (currentCommand.startsWith("lea")) {
	        String[] array = currentCommand.split("[ ,]+");
            return array[1].substring(1);
        } else {
        	return currentCommand.replace(":", "");
        }
    }
        
    public void C(PrintWriter out) {
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
          }

          out.println("111" + Code.comp(comp) + Code.dest(dest) + Code.jump(jump));
            }
            catch (InvalidDestException ex) {
                Error.error("Invalid destination"+currentCommand);
            }
            catch (InvalidCompException ex) {
                Error.error("Invalid computation"+currentCommand);
            }
            catch (InvalidJumpException ex) {
                Error.error("Invalid jump"+currentCommand);
            }
        return;
    }

    // close input file
    public void close() throws IOException {
        fileReader.close();
        return;
    }
}
