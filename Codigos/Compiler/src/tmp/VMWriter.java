/**
 * Curso: Elementos de Sistemas
 * Arquivo: JackTokenizer.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 6/05/2017
 */

package vmtranslator;

import java.io.*;



/**
 * Encapsula o código de leitura. Carrega as instruções na linguagem de máquina virtual a pilha,
 * analisa, e oferece acesso aos comandos.
 * Além disso, remove todos os espaços em branco e comentários.
 */
public class JackTokenizer {

    public String currentCommand = "";  // comando atual
    private BufferedReader fileReader;  // leitor de arquivo

    /** Enumerator para os tipos de comandos de Linguagem de Máquina Virtua a Pilha. */
    public enum CommandType {
        KEYWORD,
        SYMBOL, 
        IDENTIFIER, 
        INT_CONST, 
        STRING_CONST           // 
    }

    /** Enumerator para os tipos de comandos de Linguagem de Máquina Virtua a Pilha. */
    public enum CommandType2 {
        CLASS,
        METHOD, 
        FUNCTION, 
        CONSTRUCTOR, 
        INT, 
        BOOLEAN, 
        CHAR, 
        VOID, 
        VAR, 
        STATIC, 
        FIELD, 
        LET, 
        DO, 
        IF, 
        ELSE, 
        WHILE, 
        RETURN, 
        TRUE, 
        FALSE, 
        NULL, 
        THIS           // 
    }

    /** 
     * Abre o arquivo de entrada VM e se prepara para analisá-lo.
     * @param file arquivo VM que será feito o parser.
     */
    public JackTokenizer(String file) throws FileNotFoundException {
        this.fileReader = new BufferedReader(new FileReader(file));
    }

    /**
     * Carrega um comando e avança seu apontador interno para o próxima
     * linha do arquivo de entrada. Caso não haja mais linhas no arquivo de
     * entrada o método retorna "Falso", senão retorna "Verdadeiro".
     * @return Verdadeiro se ainda há instruções, Falso se as instruções terminaram.
     */
// Gets the next token from the input and makes it the current token. This method should only be called if hasMoreTokens() is true. Initially there is no current token.
    public Boolean advance() throws IOException {
        while(true){
            String currentLine = fileReader.readLine();
            if (currentLine == null)
                return false;  // caso não haja mais comandos
            currentCommand = currentLine.replaceAll("//.*$", "").trim();
            if (currentCommand.equals(""))
                continue;
            return true;   // caso um comando seja encontrado
        }
    }

    /**
     * Retorna o comando "intrução" atual (sem o avanço)
     * @return a instrução atual para ser analilisada
     */
    public String command() {
      return currentCommand;
    }

    /**
     * Retorna o tipo da instrução passada no argumento:
     *  C_PUSH para push, por exemplo push constant 1
     *  C_POP para pop, por exemplo pop constant 2
     * @param  command instrução a ser analisada.
     * @return o tipo da instrução.
     */
// Returns the type of the current token.
    public CommandType tokenType(String command) {
        if (command.startsWith("push")) {
            return CommandType.C_PUSH;  // comandos de PUSH
        } else if (command.startsWith("pop")) {
            return CommandType.C_POP;  //  comandos de POP
        } else {
            return CommandType.C_ARITHMETIC;  // C_ARITHMETIC for add, sub, etc...
        }
    }
    

// Returns the keyword which is the current token. Should be called only when tokenType() is KEYWORD.
    public CommandType keyWord(String command) {
        if (command.startsWith("push")) {
            return CommandType.C_PUSH;  // comandos de PUSH
        } else if (command.startsWith("pop")) {
            return CommandType.C_POP;  //  comandos de POP
        } else {
            return CommandType.C_ARITHMETIC;  // C_ARITHMETIC for add, sub, etc...
        }
    }
    



    /**
     * Retorna o primeiro argumento de um comando push ou pop passada no argumento.
     * Se for um comando aritmético, retorna o próprio texto do comando 
     * Deve ser chamado somente quando commandType() é diferente de C_RETURN.
     * @param  command instrução a ser analisada.
     * @return somente o símbolo ou o valor número da instrução.
     */
// Returns the character which is the current token. Should be called only when tokenType() is SYMBOL.
    public String symbol(String command) {
        
    }

    /**
     * Retorna o segundo argumento de um comando push ou pop passada no argumento.
     * Deve ser chamado somente quando commandType() é C_PUSH, C_POP, C_FUNCTION, ou C_CALL.
     * @param  command instrução a ser analisada.
     * @return o símbolo da instrução (sem os dois pontos).
     */

// Returns the identifier which is the current token. Should be called only when tokenType() is IDENTIFIER.
    public Integer identifier(String command) {

    }

// Returns the integer value of the current token. Should be called only when tokenType() is INT_CONST.
    public Integer intVal(String command) {

    }

// Returns the string value of the current token, without the double quotes. Should be called only when tokenType() is STRING_CONST.
    public Integer stringVal(String command) {

    }


    // fecha o arquivo de leitura
    public void close() throws IOException {
        fileReader.close();
    }

}
