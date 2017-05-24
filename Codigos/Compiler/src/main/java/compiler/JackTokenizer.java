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

    /** Enumerator para os tipos de símbolos terminais de Linguagem Jack. */
    public enum TokenType {
        KEYWORD,        // Palavras reservadas
        SYMBOL,         // Símbolos, como chaves, colchetes, pontuações, etc..
        IDENTIFIER,     // Identificadores, como nome de variáveis ou classes por exemplo
        INT_CONST,      // Inteiros constantes, ou seja, números no código
        STRING_CONST    // Strings constantes, ou seja, textos entre aspas
    }

    /** Enumerator para as palavras reservadas na linguagem Jack. */
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
        THIS
    }


    public String currentToken = "";       // comando atual
    public String[] currentTokens = {""};  // comandos atuais
    private BufferedReader fileReader;       // leitor de arquivo

    boolean comment = false;
    int tokencounter;

    /** 
     * Abre o arquivo de entrada no formato Jack e se prepara para analisá-lo.
     * @param file arquivo VM que será feito o parser.
     */
    public JackTokenizer(String file) {

        try {
            this.fileReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            Error.error("Arquivo \'" + file + "\' nao encontrado");
            System.exit(1);
        }
        tokencounter = 1;

    }

    /**
     * Carrega um token (átomo) e avança seu apontador interno para o próximo token
     * Caso não haja mais tokens no arquivo de entrada o método retorna "Falso", 
     * senão retorna "Verdadeiro".
     * Os tokens são os elementos de um documento que interessam, espaços e tabulações são removidos,
     * os comentários também são removidos como do barra(/) asterisco (*), até um asterisco (*) barra (/),
     * duas barras seguidas, contudo os outros símbolos são separados e recuperados um a um. 
     * Em especial textos entre aspas são recuperados como um token só e não devem ter seus dados internos
     * as aspas tratados, ou seja devem ser algo único.
     * @return Verdadeiro se ainda há tokens, Falso se os tokens terminaram.
     */
    public Boolean advance() {

        if( tokencounter >= currentTokens.length) {
            if(!readline()) {
                return false;
            }
        }

        currentToken = currentTokens[tokencounter++].trim();

        if(currentToken.equals("")) {
            return advance();
        }

        return true;

    }

    /**
     * Retorna o token (átomo) atual (sem o avanço).
     * @return o átomo atual para ser analilisado.
     */
    public String token() {
      return currentToken;
    }

    /**
     * Retorna o tipo do token passada no argumento.
     * KEYWORD para class, constructor, function, method, field, static, var, int, char, boolean, void, true, false, null, this, let, do, if, else, while, return.
     * SYMBOL para { } ( ) [ ] . , ; + - * & | < > = ~ /
     * INT_CONST para números
     * STRING_CONST para textos entre aspas
     * IDENTIFIER para os nomes de variáveis, classes, métodos, etc.
     * @param  token a ser analisado.
     * @return o tipo do token.
     */
    public static TokenType tokenType(String token) {
        if (
            token.equals("class") ||
            token.equals("constructor") ||
            token.equals("function") ||
            token.equals("method") ||
            token.equals("field") ||
            token.equals("static") ||
            token.equals("var") ||
            token.equals("int") ||
            token.equals("char") ||
            token.equals("boolean") ||
            token.equals("void") ||
            token.equals("true") ||
            token.equals("false") ||
            token.equals("null") ||
            token.equals("this") ||
            token.equals("let") ||
            token.equals("do") ||
            token.equals("if") ||
            token.equals("else") ||
            token.equals("while") ||
            token.equals("return")
            ) {
            return TokenType.KEYWORD;  // 
        } else if (
            token.equals("{") ||
            token.equals("}") ||
            token.equals("(") ||
            token.equals(")") ||
            token.equals("[") ||
            token.equals("]") ||
            token.equals(".") ||
            token.equals(",") ||
            token.equals(";") ||
            token.equals("+") ||
            token.equals("-") ||
            token.equals("*") ||
            token.equals("/") ||
            token.equals("&") ||
            token.equals("|") ||
            token.equals("<") ||
            token.equals(">") ||
            token.equals("=") ||
            token.equals("~")
            ) {
            return TokenType.SYMBOL;  // 
        } else if (Character.isDigit(token.charAt(0))) {
            return TokenType.INT_CONST;  // 
        } else if (token.charAt(0)=='"' || token.charAt(0)=='\'') {
            return TokenType.STRING_CONST;
        } else {
            return TokenType.IDENTIFIER;  // 
        }
    }


    /**
     * Retorna o tipo de keyword (palavra reservada) do token passada no argumento.
     * O retorno é em função dos tipos do enumerator KeywordType.
     * Rotina só deve ser chamada se o tipo do token for um KEYWORD
     * @param  token a ser analisado.
     * @return o tipo de keyword do token.
     */
    public static KeywordType keyWord(String token) {

        if (token.equals("class"))       return KeywordType.CLASS;  //
        if (token.equals("constructor")) return KeywordType.CONSTRUCTOR;
        if (token.equals("function"))    return KeywordType.FUNCTION;
        if (token.equals("method"))      return KeywordType.METHOD;
        if (token.equals("field"))       return KeywordType.FIELD;
        if (token.equals("static"))      return KeywordType.STATIC;
        if (token.equals("var"))         return KeywordType.VAR;
        if (token.equals("int"))         return KeywordType.INT;
        if (token.equals("char"))        return KeywordType.CHAR;
        if (token.equals("boolean"))     return KeywordType.BOOLEAN;
        if (token.equals("void"))        return KeywordType.VOID;
        if (token.equals("true"))        return KeywordType.TRUE;
        if (token.equals("false"))       return KeywordType.FALSE;
        if (token.equals("null"))        return KeywordType.NULL;
        if (token.equals("this"))        return KeywordType.THIS;
        if (token.equals("let"))         return KeywordType.LET;
        if (token.equals("do"))          return KeywordType.DO;
        if (token.equals("if"))          return KeywordType.IF;
        if (token.equals("else"))        return KeywordType.ELSE;
        if (token.equals("while"))       return KeywordType.WHILE;
        if (token.equals("return"))      return KeywordType.RETURN;
        
        Error.error("O token \'" + token + "\' nao foi reconhecido");
        return null;    // null significa que o token não foi reconhecido.

    }
    
    /**
     * Retorna o dado como um Character no caso de um toke do tipo SYMBOL.
     * Deve ser chamado somente quando o tipo de token for um SYMBOL.
     * @param  command instrução a ser analisada.
     * @return somente o símbolo ou o valor número da instrução.
     */
    public static Character symbol(String token) {
        return token.charAt(0);
    }

    /**
     * Retorna o valor numérico de um token que é um número constante.
     * Deve ser chamado somente quando o tipo de token for um INT_CONST.
     * @param  token a ser analisado.
     * @return o valor numérico (Integer) do token informado (String).
     */
    public static Integer intVal(String token) {
        return Integer.valueOf(token);
    }

    /**
     * Retorna a String de um token fornecido no argumento.
     * Caso o token possua aspas, estas deverão ser removidas.
     * Deve ser chamado somente quando o tipo de token for um STRING_CONST.
     * @param  token a ser analisado.
     * @return o valor numérico (Integer) do token informado (String).
     */
    public static String stringVal(String token) {
        if(token.charAt(0)=='"') {
            return token.replace("\"","");
        }
        else if(token.charAt(0)=='\'') {
            return token.replace("\'","");
        }
        return null;
    }

    // Le uma linha do arquivo de entrada
    private Boolean readline() {
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
                currentTokens = new String[ tmpCommands.size() ];
                tmpCommands.toArray(currentTokens);
                return true;   // caso um comando seja encontrado
            } catch (IOException e) {
                System.out.println("Erro de leitura de linha");
            }
        }
    }


    // fecha o arquivo de leitura
    public void close() throws IOException {
        fileReader.close();
    }

}
