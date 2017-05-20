/**
 * Curso: Elementos de Sistemas
 * Arquivo: CompilationEngine.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 6/05/2017
 */

package compiler;

import java.io.*;


/** 
 * Traduz da linguagem jack para códigos vm.
 */
public class CompilationEngine {

    JackTokenizer tokenizer;

    PrintWriter outputFile = null;

    /** 
     * Abre o arquivo de entrada VM e se prepara para analisá-lo.
     * @param filename nome do arquivo NASM que receberá o código traduzido.
     */
    public CompilationEngine(String inputfilename, String outputfilename, String outputfilenameT) {
        
        try  {
            tokenizer = new JackTokenizer(inputfilename);

            PrintWriter outputFileT = null;
            //this.outputFilename = filename.substring(filename.lastIndexOf('/')+1, filename.lastIndexOf('.'));
            File fileT = new File(outputfilenameT);
            outputFileT = new PrintWriter(new FileWriter(fileT));
            

            outputFileT.println("<tokens>");

            // Avança enquanto houver linhas para traduzir
            while(tokenizer.advance()) {

                if      (tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.KEYWORD) {
                    outputFileT.print("  <keyword>");
                }else if(tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.SYMBOL) {
                    outputFileT.print("  <symbol>");
                }else if(tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.IDENTIFIER) {
                    outputFileT.print("  <identifier>");
                }else if(tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.INT_CONST) {
                    outputFileT.print("  <integerConstant>");
                }else if(tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.STRING_CONST) {
                    outputFileT.print("  <stringConstant>");
                }

                outputFileT.print(" "+tokenizer.command());            

                if      (tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.KEYWORD) {
                    outputFileT.println("  </keyword>");
                }else if(tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.SYMBOL) {
                    outputFileT.println("  </symbol>");
                }else if(tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.IDENTIFIER) {
                    outputFileT.println("  </identifier>");
                }else if(tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.INT_CONST) {
                    outputFileT.println("  </integerConstant>");
                }else if(tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.STRING_CONST) {
                    outputFileT.println("  </stringConstant>");
                }
                
            }

            outputFileT.println("</tokens>");

            outputFileT.close();

        } catch (FileNotFoundException e){
            Error.error("Arquivo \'" + inputfilename + "\' nao encontrado");
            System.exit(1);
        } catch (IOException e) {
            Error.error("uma excessao de i/o foi lancada");
            System.exit(1);
        }

        // Para gravar o sintatico
        try  {
            tokenizer = new JackTokenizer(inputfilename);

            File file = new File(outputfilename);
            outputFile = new PrintWriter(new FileWriter(file));

        } catch (FileNotFoundException e){
            Error.error("Arquivo \'" + inputfilename + "\' nao encontrado");
            System.exit(1);
        } catch (IOException e) {
            Error.error("uma excessao de i/o foi lancada");
            System.exit(1);
        }

    }

    public void saveOpenTag(String tag, boolean linebreak) {    
        //System.out.print("<"+tag+">");
        outputFile.print("<"+tag+">");
        //if(linebreak) { System.out.println(); }
        if(linebreak) { outputFile.println(); }
    }

    public void saveCloseTag(String tag) {   
        outputFile.println("</"+tag+">");
        //System.out.println("</"+tag+">");
    }
    
    public void saveName(String name) { 
        outputFile.print(" "+name+" ");
        //System.out.print(" "+name+" ");
    }

    public void saveTerminal(String tag, String name) {
        saveOpenTag(tag, false);
        saveName(name);
        saveCloseTag(tag);
        if(!tokenizer.advance()) return;
    }

    public void compileIdentifier() {
        if(tokenizer.tokenType(tokenizer.command())!=JackTokenizer.CommandType.IDENTIFIER) {
            Error.error("Não encontrado identifier. Encontrado: "+tokenizer.command());
        }
        saveTerminal("identifier",tokenizer.command());
    }

    public void compileSymbol(Character symbol) {
        if(tokenizer.tokenType(tokenizer.command())!=JackTokenizer.CommandType.SYMBOL) {
            Error.error("Não encontrado symbol"+Character.toString(symbol)+". Encontrado: "+tokenizer.command());
        }
        if(tokenizer.symbol(tokenizer.command())!=symbol) {
            Error.error("Não encontrado symbol"+Character.toString(symbol)+". Encontrado: "+tokenizer.command());
        }
        saveTerminal("symbol",tokenizer.command());
    }

    public boolean isType() {
        return (tokenizer.command().equals("int") ||
                tokenizer.command().equals("char") ||
                tokenizer.command().equals("boolean") ||
                tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.IDENTIFIER);
    }

    public boolean isStatement() {
        return (tokenizer.command().equals("let") ||
                tokenizer.command().equals("if") ||
                tokenizer.command().equals("while") ||
                tokenizer.command().equals("do") ||
                tokenizer.command().equals("return"));
                
    }

    public void compileType() {
        if(!isType()) {
            Error.error("Não encontrado keyword ou identifier. Encontrado: "+tokenizer.command());
        }
        if(tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.IDENTIFIER) {
            saveTerminal("identifier",tokenizer.command());    
        } else {
            saveTerminal("keyword",tokenizer.command());
        }
        
    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para executar o comando aritmético.
     * @param  command comando aritmético a ser analisado.
     */
    public void compileClass() {

        saveOpenTag("class",true);

        if(!tokenizer.advance()) return; // pega primeiro token

        if(tokenizer.tokenType(tokenizer.command())!=JackTokenizer.CommandType.KEYWORD ||
           tokenizer.keyWord(tokenizer.command())!=JackTokenizer.KeywordType.CLASS) {
            Error.error("Não encontrada class. Encontrado: "+tokenizer.command());
        }

        saveTerminal("keyword",tokenizer.command());

        compileIdentifier();

        compileSymbol('{');

        saveOpenTag("classVarDec",true);
        while( tokenizer.command().equals("static") || tokenizer.command().equals("field")) {
            compileClassVarDec();    
        }
        saveCloseTag("classVarDec");
        
        while( tokenizer.command().equals("constructor") || tokenizer.command().equals("function") || tokenizer.command().equals("method") ) {
            saveOpenTag("subroutineDec",true);
            compileSubroutineDec();    
            saveCloseTag("subroutineDec");
        }
        
        compileSymbol('}');

        saveCloseTag("class");

        outputFile.close();

    }

    public void compileClassVarDec() {

        if(tokenizer.tokenType(tokenizer.command())!=JackTokenizer.CommandType.KEYWORD ||
           !(tokenizer.keyWord(tokenizer.command())==JackTokenizer.KeywordType.STATIC ||
             tokenizer.keyWord(tokenizer.command())==JackTokenizer.KeywordType.FIELD)) {
            Error.error("Não encontrada static ou field. Encontrado: "+tokenizer.command());
        }

        saveTerminal("keyword",tokenizer.command());  // static ou field

        compileType();

        compileIdentifier();
        
        while( tokenizer.command().equals(",") ) {
            compileSymbol(',');
            compileIdentifier();
        }

        compileSymbol(';');

    }

    public void compileSubroutineDec() {

        if(tokenizer.tokenType(tokenizer.command())!=JackTokenizer.CommandType.KEYWORD ||
           !(tokenizer.keyWord(tokenizer.command())==JackTokenizer.KeywordType.CONSTRUCTOR ||
             tokenizer.keyWord(tokenizer.command())==JackTokenizer.KeywordType.METHOD ||
             tokenizer.keyWord(tokenizer.command())==JackTokenizer.KeywordType.FUNCTION) )
        {
            Error.error("Não encontrada constructor, method ou function. Encontrado: "+tokenizer.command());
        }

        saveTerminal("keyword",tokenizer.command());

        if( tokenizer.command().equals("void") ) {
            saveTerminal("keyword",tokenizer.command());
        } else {
            compileType();    
        }
        
        compileIdentifier();
        
        compileSymbol('(');

        saveOpenTag("parameterList",true);
        if(isType()) {
            compileParameterList();
        }
        saveCloseTag("parameterList");

        compileSymbol(')');

        compileSubroutineBody();

    }

    public void compileParameterList() {

        // ((type varName) (',' type varName)*)?

        compileType();

        compileIdentifier();
    
        while( tokenizer.command().equals(",") ) {
        
            compileSymbol(',');
        
            compileType();
        
            compileIdentifier();
        
        }

    }

    public void compileSubroutineBody() {

        saveOpenTag("subroutineBody",true);
        compileSymbol('{');

        compileVarDec();

        compileStatements();

        compileSymbol('}');
        saveCloseTag("subroutineBody");

    }

    public void compileVarDec() {

        while(tokenizer.command().equals("var")) {

            saveOpenTag("varDec",true);

            saveTerminal("keyword",tokenizer.command());

            compileType();

            compileIdentifier();

            while( tokenizer.command().equals(",") ) {
                compileSymbol(',');
            
                compileIdentifier();
            }

            compileSymbol(';');

            saveCloseTag("varDec");

        }

    }

    public void compileStatements() {

        saveOpenTag("statements",true);

        while(isStatement()) {

            if( tokenizer.command().equals("let") ) {
                saveOpenTag("letStatement",true);
                compileLet();
                saveCloseTag("letStatement");
            }

            if( tokenizer.command().equals("if") ) {
                saveOpenTag("ifStatement",true);
                compileIf();
                saveCloseTag("ifStatement");
            }

            if( tokenizer.command().equals("while") ) {
                saveOpenTag("whileStatement",true);
                compileWhile();
                saveCloseTag("whileStatement");
            }

            if( tokenizer.command().equals("do") ) {
                saveOpenTag("doStatement",true);
                compileDo();
                saveCloseTag("doStatement");
            }

            if( tokenizer.command().equals("return") ) {
                saveOpenTag("returnStatement",true);
                compileReturn();
                saveCloseTag("returnStatement");
            }

        }

        saveCloseTag("statements");
    }

    public void compileDo() {
        //'do' subroutineCall ';'

        saveTerminal("keyword",tokenizer.command());

        // subroutineCall

        compileIdentifier();  //subroutineName | (className|varName)
        
        if( tokenizer.command().equals("(") ) {  //'(' expressionList ')'

            compileSymbol('(');
    
            compileExpressionList();

            compileSymbol(')');

        } else { // '.' subroutineName '(' expressionList ')'

            compileSymbol('.');

            compileIdentifier();

            compileSymbol('(');
    
            compileExpressionList();

            compileSymbol(')');

        }

        compileSymbol(';');

    }

    public void compileLet() {
        //'let' varName ('[' expression ']')? '=' expression ';'

        saveTerminal("keyword",tokenizer.command());

        compileIdentifier();
            
        if( tokenizer.command().equals("[") ) {
        
            compileSymbol('[');
        
            compileExpression();

            compileSymbol(']');
    
        }

        compileSymbol('=');
        
        compileExpression();

        compileSymbol(';');

    }

    public void compileWhile() {

        //'while' '(' expression ')' '{' statements '}'

        saveTerminal("keyword",tokenizer.command());

        compileSymbol('(');

        compileExpression();

        compileSymbol(')');

        compileSymbol('{');

        compileStatements();

        compileSymbol('}');

    }

    public void compileReturn() {
        // 'return' expression? ';'

        saveTerminal("keyword",tokenizer.command());

        if( isTerm() ) {

            compileExpression();

        }

        compileSymbol(';');

    }

    public void compileIf() {
        
        // 'if' '(' expression ')' '{' statements '}'
        // ('else' '{' statements '}')?

        saveTerminal("keyword",tokenizer.command());

        compileSymbol('(');

        compileExpression();

        compileSymbol(')');

        compileSymbol('{');

        compileStatements();

        compileSymbol('}');

        if(tokenizer.command().equals("else")) {

            saveTerminal("keyword",tokenizer.command());

            compileSymbol('{');

            compileStatements();

            compileSymbol('}');

        }

    }

    public boolean isKeywordConstant() {
        return (tokenizer.command().equals("true") ||
            tokenizer.command().equals("false") ||
            tokenizer.command().equals("null") ||
            tokenizer.command().equals("this")); 
    }

    public boolean isUnaryOp() {
        return (tokenizer.command().equals("-") ||
            tokenizer.command().equals("~")); 
    }



    public boolean isTerm() {

//integerConstant | stringConstant | keywordConstant |
// varName | varName '[' expression ']' | subroutineCall |
// '(' expression ')' | unaryOp term
        return(
            tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.INT_CONST ||
            tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.STRING_CONST ||
            isKeywordConstant() ||
            tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.IDENTIFIER || //varName ou subroutineName
            tokenizer.command().equals("(") ||
            isUnaryOp()
        );

    }

    public boolean isOp() {
        return (tokenizer.command().equals("+") ||
            tokenizer.command().equals("-") ||
            tokenizer.command().equals("*") ||
            tokenizer.command().equals("/") ||
            tokenizer.command().equals("&") ||
            tokenizer.command().equals("|") ||
            tokenizer.command().equals("<") ||
            tokenizer.command().equals(">") ||
            tokenizer.command().equals("=")
        );
    }

    public void compileExpression() {
        saveOpenTag("expression",true);
        compileTerm();

        while(isOp()) {

            saveTerminal("symbol",tokenizer.command());

            compileTerm();

        }
        saveCloseTag("expression");
    }

    public void compileTerm() {
        saveOpenTag("term",true);
        
        if(tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.IDENTIFIER) {
            compileIdentifier();
        }            
        saveCloseTag("term");
    }

    public void compileExpressionList() {
        // (expression (',' expression)* )?

        saveOpenTag("expressionList",true);

        if( isTerm() ) {

            compileExpression();

            while( tokenizer.command().equals(",") ) {
                compileSymbol(',');
            
                compileExpression();
            }

        }

        saveCloseTag("expressionList");

    }


    // fecha o arquivo de escrita
    public void close() throws IOException {
        this.outputFile.close();
    }

}
