/**
 * Curso: Elementos de Sistemas
 * Arquivo: JackTokenizer.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 6/05/2017
 */

package compiler;

import java.io.*;
import java.lang.*;
import java.util.*;

/**
 * Encapsula o código de leitura. Carrega as instruções na linguagem de máquina virtual a pilha,
 * analisa, e oferece acesso aos comandos.
 * Além disso, remove todos os espaços em branco e comentários.
 */
public class JackTokenizer {

    public String currentCommand = "";  // comando atual
    public String[] currentCommands = {""};  // comandos atuais
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
    public enum KeywordType {
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


    boolean comment = false;
    int tokencounter;

    /** 
     * Abre o arquivo de entrada VM e se prepara para analisá-lo.
     * @param file arquivo VM que será feito o parser.
     */
    public JackTokenizer(String file) throws FileNotFoundException {
        this.fileReader = new BufferedReader(new FileReader(file));
        tokencounter = 1;

    }



    /**
     * Carrega um comando e avança seu apontador interno para o próxima
     * linha do arquivo de entrada. Caso não haja mais linhas no arquivo de
     * entrada o método retorna "Falso", senão retorna "Verdadeiro".
     * @return Verdadeiro se ainda há instruções, Falso se as instruções terminaram.
     */
// Gets the next token from the input and makes it the current token. This method should only be called if hasMoreTokens() is true. Initially there is no current token.
    public Boolean readline() {
        tokencounter = 0;
        while(true){
            try {
                String currentLine = fileReader.readLine();
                if (currentLine == null)
                    return false;  // caso não haja mais comandos
                if(comment) {
                    if(currentLine.contains("*/")) {
                        comment = false;
                    }
                    continue;
                }
                String[] parts = currentLine.split("/\\*");
                currentLine = parts[0].replaceAll("//.*$", "").trim();
                if(parts.length > 1)
                    if(!parts[1].contains("*/")) {
                        comment = true;
                    }
                if (currentLine.equals(""))
                    continue;

                String regex = "(?<=[\"])|(?=[\"])";
                String[] findStrings = currentLine.split(regex);

                regex = "(?<=[\\{\\}\\(\\)\\]\\[.,;+\\-*/&\\|<>=~\"])|(?=[ \\{\\}\\(\\)\\]\\[.,;+\\-*/&\\|<>=~\"])";

                List<String> tmpCommands = new ArrayList<String>();

                for(int i=0;i<findStrings.length;i++) {
                    if(findStrings[i].equals("\"") || findStrings[i].equals("\'")) {
                        tmpCommands.add("\""+findStrings[i+1]+"\"");
                        i += 2;
                    } else {
                        String[] tmpS = findStrings[i].split(regex);
                        for(String value : tmpS) {
                            tmpCommands.add(value);
                        }    
                    }
                      
                }
                currentCommands = new String[ tmpCommands.size() ];
                tmpCommands.toArray(currentCommands);
                return true;   // caso um comando seja encontrado
            } catch (IOException e) {
                System.out.println("Erro de leitura de linha");
            }
        }
    }

    public Boolean advance() {
        if( tokencounter >= currentCommands.length) {
            if(!readline()) {
                return false;
            }
        }
        currentCommand = currentCommands[tokencounter++].trim();

        if(currentCommand.equals("")) {
            return advance();
        }

        return true;
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
        if (
            command.equals("class") ||
            command.equals("constructor") ||
            command.equals("function") ||
            command.equals("method") ||
            command.equals("field") ||
            command.equals("static") ||
            command.equals("var") ||
            command.equals("int") ||
            command.equals("char") ||
            command.equals("boolean") ||
            command.equals("void") ||
            command.equals("true") ||
            command.equals("false") ||
            command.equals("null") ||
            command.equals("this") ||
            command.equals("let") ||
            command.equals("do") ||
            command.equals("if") ||
            command.equals("else") ||
            command.equals("while") ||
            command.equals("return")
            ) {
            return CommandType.KEYWORD;  // 
        } else if (
            command.equals("{") ||
            command.equals("}") ||
            command.equals("(") ||
            command.equals(")") ||
            command.equals("[") ||
            command.equals("]") ||
            command.equals(".") ||
            command.equals(",") ||
            command.equals(";") ||
            command.equals("+") ||
            command.equals("-") ||
            command.equals("*") ||
            command.equals("/") ||
            command.equals("&") ||
            command.equals("|") ||
            command.equals("<") ||
            command.equals(">") ||
            command.equals("=") ||
            command.equals("~")
            ) {
            return CommandType.SYMBOL;  // 
        } else if (Character.isDigit(command.charAt(0))) {
            return CommandType.INT_CONST;  // 
        } else if (command.charAt(0)=='"' || command.charAt(0)=='\'') {
            return CommandType.STRING_CONST;
        } else {
            return CommandType.IDENTIFIER;  // 
        }
    }


// Returns the keyword which is the current token. Should be called only when tokenType() is KEYWORD.
    public KeywordType keyWord(String command) {
        if (command.equals("class")) return KeywordType.CLASS;  //
        if (command.equals("constructor")) return KeywordType.CONSTRUCTOR;
        if (command.equals("function")) return KeywordType.FUNCTION;
        if (command.equals("method")) return KeywordType.METHOD;
        if (command.equals("field")) return KeywordType.FIELD;
        if (command.equals("static")) return KeywordType.STATIC;
        if (command.equals("var")) return KeywordType.VAR;
        if (command.equals("int")) return KeywordType.INT;
        if (command.equals("char")) return KeywordType.CHAR;
        if (command.equals("boolean")) return KeywordType.BOOLEAN;
        if (command.equals("void")) return KeywordType.VOID;
        if (command.equals("true")) return KeywordType.TRUE;
        if (command.equals("false")) return KeywordType.FALSE;
        if (command.equals("null")) return KeywordType.NULL;
        if (command.equals("this")) return KeywordType.THIS;
        if (command.equals("let")) return KeywordType.LET;
        if (command.equals("do")) return KeywordType.DO;
        if (command.equals("if")) return KeywordType.IF;
        if (command.equals("else")) return KeywordType.ELSE;
        if (command.equals("while")) return KeywordType.WHILE;
        if (command.equals("return")) return KeywordType.RETURN;
        return null;
    }
    

    /**
     * Retorna o primeiro argumento de um comando push ou pop passada no argumento.
     * Se for um comando aritmético, retorna o próprio texto do comando 
     * Deve ser chamado somente quando commandType() é diferente de C_RETURN.
     * @param  command instrução a ser analisada.
     * @return somente o símbolo ou o valor número da instrução.
     */
// Returns the character which is the current token. Should be called only when tokenType() is SYMBOL.
    public Character symbol(String command) {
        return command.charAt(0);
    }

    /**
     * Retorna o segundo argumento de um comando push ou pop passada no argumento.
     * Deve ser chamado somente quando commandType() é C_PUSH, C_POP, C_FUNCTION, ou C_CALL.
     * @param  command instrução a ser analisada.
     * @return o símbolo da instrução (sem os dois pontos).
     */
// Returns the integer value of the current token. Should be called only when tokenType() is INT_CONST.
    public Integer intVal(String command) {
        return Integer.valueOf(command);
    }

// Returns the string value of the current token, without the double quotes. Should be called only when tokenType() is STRING_CONST.
    public String stringVal(String command) {
        return command.replace("\"","");
    }


    // fecha o arquivo de leitura
    public void close() throws IOException {
        fileReader.close();
    }

}
