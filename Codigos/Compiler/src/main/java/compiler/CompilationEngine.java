/**
 * Curso: Elementos de Sistemas
 * Arquivo: CompilationEngine.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 6/05/2017
 */

package compiler;

import java.io.*;

/** 
 * Classe principal do compilador que é responsável por dar rítmo no processo de compilação.
 * Essa classe é responsável por instanciar o Tokenizer, VMWrites e SymbolTable.
 * O processo é gerenciado por funções recursivas.
 */
public class CompilationEngine {

    JackTokenizer tokenizer;

    PrintWriter outputFileXML = null;

    String className;
    String subroutineName;

    SymbolTable classSymbolTable;
    SymbolTable subroutineSymbolTable;

    String subroutineCall1; // armazena o primeiro nome de uma chamada

    VMWriter vm;

    int counterIf = 0;
    int counterWhile = 0;

    boolean voidType;
    boolean constructor;
    boolean method;
    boolean function;

    /** 
     * Construtor da classe, responsável por iniciar/instanciar os objetos do processo.
     * @param inputfilename nome do arquivo Jack que será compilador.
     * @param outputfilename nome do arquivo VM que receberá o código gerado.
     */   
    public CompilationEngine(String inputfilename, String outputfilename) {
        subroutineSymbolTable = new SymbolTable();      // Gerencia a tabela de símbolos de variáveis.
        tokenizer = new JackTokenizer(inputfilename);   // Para fazer a varredura no arquivo fonte.
        vm = new VMWriter(new File(outputfilename));    // Para gravar o arquivo VM.
    }

    /**
     * Realiza as chamadas recursivas para criar a árvores sintática a partir do token "class".
     * Grava no arquivo de saida as instruções (se necessárias) para as intruções baseado na gramática do Jack.
     * Realiza as chamdas para navegar no arquivo fonte (.jack) avançando nos tokens já consumidos.
     * Respectivas gramática:  'class' className '{' classVarDec* subroutineDec* '}'
     */
    public void compileClass() {

        classSymbolTable = new SymbolTable();

        saveOpenTag("class",true);
        if(!tokenizer.advance()) return; // pega primeiro token
        if(tokenizer.tokenType(tokenizer.token())!=JackTokenizer.TokenType.KEYWORD ||
           tokenizer.keyWord(tokenizer.token())!=JackTokenizer.KeywordType.CLASS) {
            Error.error("Não encontrada class. Encontrado: "+tokenizer.token());
        }
        saveTerminal("keyword",tokenizer.token());

        className = tokenizer.token();
        compileIdentifier();
        
        compileSymbol('{');
        while( tokenizer.token().equals("static") || tokenizer.token().equals("field")) {
            saveOpenTag("classVarDec",true);
            compileClassVarDec();    
            saveCloseTag("classVarDec");
        }
        while( tokenizer.token().equals("constructor") || tokenizer.token().equals("function") || tokenizer.token().equals("method") ) {
            saveOpenTag("subroutineDec",true);
            compileSubroutineDec();    
            saveCloseTag("subroutineDec");
        }
        compileSymbol('}');
        saveCloseTag("class");

    }

    /**
     * Realiza as chamadas recursivas para criar a árvores sintática a partir das declarações da classe.
     * Grava no arquivo de saida as instruções (se necessárias) para as intruções baseado na gramática do Jack.
     * Realiza as chamadas para navegar no arquivo fonte (.jack) avançando nos tokens já consumidos.
     * Respectivas gramática:  ('static' | 'field') type varName (',' varName)* ';' 
     */
    public void compileClassVarDec() {
        if(tokenizer.tokenType(tokenizer.token())!=JackTokenizer.TokenType.KEYWORD ||
           !(tokenizer.keyWord(tokenizer.token())==JackTokenizer.KeywordType.STATIC ||
             tokenizer.keyWord(tokenizer.token())==JackTokenizer.KeywordType.FIELD)) {
            Error.error("Não encontrada static ou field. Encontrado: "+tokenizer.token());
        }
        
        Symbol.Kind kind;
        if(tokenizer.keyWord(tokenizer.token())==JackTokenizer.KeywordType.STATIC) {
            kind = Symbol.Kind.STATIC;
        } else {
            kind = Symbol.Kind.FIELD;
        }
        saveTerminal("keyword",tokenizer.token());  // static ou field

        String type = tokenizer.token();
        compileType();

        String identifier = tokenizer.token();
        compileIdentifier();

        classSymbolTable.define(identifier, type, kind);

        while( tokenizer.token().equals(",") ) {
            compileSymbol(',');
            identifier = tokenizer.token();
            compileIdentifier();
            classSymbolTable.define(identifier, type, kind);
        }
        compileSymbol(';');
    }

    /**
     * Realiza as chamadas recursivas para criar a árvores sintática a partir das subrotinas da classe.
     * Grava no arquivo de saida as instruções (se necessárias) para as intruções baseado na gramática do Jack.
     * Realiza as chamadas para navegar no arquivo fonte (.jack) avançando nos tokens já consumidos.
     * Respectivas gramática:  ('constructor' | 'function' | 'method')
     *                         ('void' | type) subroutineName '(' parameterList ')'
     *                         subroutineBody 
     */
    public void compileSubroutineDec() {

        subroutineSymbolTable.startSubroutine();
        counterIf = 0;
        counterWhile = 0;

        if(tokenizer.tokenType(tokenizer.token())!=JackTokenizer.TokenType.KEYWORD ||
           !(tokenizer.keyWord(tokenizer.token())==JackTokenizer.KeywordType.CONSTRUCTOR ||
             tokenizer.keyWord(tokenizer.token())==JackTokenizer.KeywordType.METHOD ||
             tokenizer.keyWord(tokenizer.token())==JackTokenizer.KeywordType.FUNCTION) ) {
            Error.error("Não encontrada constructor, method ou function. Encontrado: "+tokenizer.token());
        }

        method = false;
        constructor = false;
        function = false;
        if(tokenizer.keyWord(tokenizer.token())==JackTokenizer.KeywordType.METHOD) {
            subroutineSymbolTable.define("this", className, Symbol.Kind.ARG);
            method = true; 
        } else if(tokenizer.keyWord(tokenizer.token())==JackTokenizer.KeywordType.CONSTRUCTOR) {
            constructor = true;
        } else {
            function = true;
        }

        saveTerminal("keyword",tokenizer.token());

        voidType = false;
        if( tokenizer.token().equals("void") ) {
            voidType = true;
            saveTerminal("keyword",tokenizer.token());
        } else {
            compileType();    
        }
        
        subroutineName = tokenizer.token();
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

    /**
     * Realiza as chamadas recursivas para criar a árvores sintática a partir dos parâmetros da subrotina.
     * Grava no arquivo de saida as instruções (se necessárias) para as intruções baseado na gramática do Jack.
     * Realiza as chamadas para navegar no arquivo fonte (.jack) avançando nos tokens já consumidos.
     * Respectivas gramática:  ((type varName) (',' type varName)*)?
     */
    public void compileParameterList() {
        String type = tokenizer.token();
        compileType();

        String identifier = tokenizer.token();
        compileIdentifier();

        subroutineSymbolTable.define(identifier, type, Symbol.Kind.ARG);    

        while( tokenizer.token().equals(",") ) {
            compileSymbol(',');

            type = tokenizer.token();
            compileType();

            identifier = tokenizer.token();
            compileIdentifier();

            subroutineSymbolTable.define(identifier, type, Symbol.Kind.ARG);    

        }
    }

    /**
     * Realiza as chamadas recursivas para criar a árvores sintática a partir das declarações da subrotina.
     * Grava no arquivo de saida as instruções (se necessárias) para as intruções baseado na gramática do Jack.
     * Realiza as chamadas para navegar no arquivo fonte (.jack) avançando nos tokens já consumidos.
     * Respectivas gramática:  'var' type varName (',' varName)* ';' 
     */
    public void compileVarDec() {

        while(tokenizer.token().equals("var")) {
            saveOpenTag("varDec",true);
            saveTerminal("keyword",tokenizer.token());

            String type = tokenizer.token();
            compileType();

            String identifier = tokenizer.token();
            compileIdentifier();

            subroutineSymbolTable.define(identifier, type, Symbol.Kind.VAR);

            while( tokenizer.token().equals(",") ) {
                compileSymbol(',');

                identifier = tokenizer.token();
                compileIdentifier();

                subroutineSymbolTable.define(identifier, type, Symbol.Kind.VAR);                
            }
            compileSymbol(';');
            saveCloseTag("varDec");
        }
    }

    /**
     * Realiza as chamadas recursivas para criar a árvores sintática a partir do corpo da subrotina.
     * Grava no arquivo de saida as instruções (se necessárias) para as intruções baseado na gramática do Jack.
     * Realiza as chamadas para navegar no arquivo fonte (.jack) avançando nos tokens já consumidos.
     * Respectivas gramática:  '{' varDec* statements '}'
     */
    public void compileSubroutineBody() {
        saveOpenTag("subroutineBody",true);
        compileSymbol('{');
        compileVarDec();

        vm.writeFunction(className+"."+subroutineName,subroutineSymbolTable.varCount(Symbol.Kind.VAR));
        if(constructor) {
            vm.writePush(VMWriter.Segment.CONST,classSymbolTable.varCount(Symbol.Kind.FIELD));
            vm.writeCall("Memory.alloc",1);
            vm.writePop(VMWriter.Segment.POINTER,0);
        }

        if(method) {
            vm.writePush(VMWriter.Segment.ARG,0);
            vm.writePop(VMWriter.Segment.POINTER,0);   
        }

        compileStatements();
        compileSymbol('}');
        saveCloseTag("subroutineBody");
    }

    /**
     * Realiza as chamadas recursivas para criar a árvores sintática a partir da lista de procedimentos da subrotina.
     * As chaves "{" e "}" não são tratadas nessa rotina de compileStatements.
     * Grava no arquivo de saida as instruções (se necessárias) para as intruções baseado na gramática do Jack.
     * Realiza as chamadas para navegar no arquivo fonte (.jack) avançando nos tokens já consumidos.
     * Respectivas gramática:  statement*
     */
    public void compileStatements() {
        saveOpenTag("statements",true);
        while(isStatement()) {

            if( tokenizer.token().equals("let") ) {
                saveOpenTag("letStatement",true);
                compileLet();
                saveCloseTag("letStatement");
            }

            if( tokenizer.token().equals("if") ) {
                saveOpenTag("ifStatement",true);
                compileIf();
                saveCloseTag("ifStatement");
            }

            if( tokenizer.token().equals("while") ) {
                saveOpenTag("whileStatement",true);
                compileWhile();
                saveCloseTag("whileStatement");
            }

            if( tokenizer.token().equals("do") ) {
                saveOpenTag("doStatement",true);
                compileDo();
                saveCloseTag("doStatement");
            }

            if( tokenizer.token().equals("return") ) {
                saveOpenTag("returnStatement",true);
                compileReturn();
                saveCloseTag("returnStatement");
            }
        }
        saveCloseTag("statements");
    }

    /**
     * Realiza as chamadas recursivas para criar a árvores sintática a partir de procedimentos "do".
     * Grava no arquivo de saida as instruções (se necessárias) para as intruções baseado na gramática do Jack.
     * Realiza as chamadas para navegar no arquivo fonte (.jack) avançando nos tokens já consumidos.
     * Respectivas gramática:  'do' subroutineCall ';'
     */
    public void compileDo() {
        saveTerminal("keyword",tokenizer.token());
        subroutineCall1 = tokenizer.token();
        compileIdentifier();  //subroutineName | (className|varName)
        compileSubroutineCallCont();
        vm.writePop(VMWriter.Segment.TEMP,0);
        compileSymbol(';');
    }

    /**
     * Realiza as chamadas recursivas para criar a árvores sintática a partir de procedimentos "let".
     * Grava no arquivo de saida as instruções (se necessárias) para as intruções baseado na gramática do Jack.
     * Realiza as chamadas para navegar no arquivo fonte (.jack) avançando nos tokens já consumidos.
     * Respectivas gramática:  'let' varName ('[' expression ']')? '=' expression ';'
     */
    public void compileLet() {
        
        saveTerminal("keyword",tokenizer.token());

        String identifier = tokenizer.token();
        compileIdentifier();

        boolean array = compileArray(identifier);

        compileSymbol('=');
        compileExpression();

        if(array) {
            vm.writePop(VMWriter.Segment.TEMP,0);
            vm.writePop(VMWriter.Segment.POINTER,1);
            vm.writePush(VMWriter.Segment.TEMP,0);
            vm.writePop(VMWriter.Segment.THAT,0);
        } else {
            pop(identifier);    
        }

        compileSymbol(';');

    }

    /**
     * Realiza as chamadas recursivas para criar a árvores sintática a partir de procedimentos "while".
     * Grava no arquivo de saida as instruções (se necessárias) para as intruções baseado na gramática do Jack.
     * Realiza as chamadas para navegar no arquivo fonte (.jack) avançando nos tokens já consumidos.
     * Respectivas gramática:  'while' '(' expression ')' '{' statements '}'
     */
    public void compileWhile() {

        int tmpCounterWhile = counterWhile++;

        vm.writeLabel("WHILE_EXP"+String.valueOf(tmpCounterWhile));

        saveTerminal("keyword",tokenizer.token());
        compileSymbol('(');
        compileExpression();
        compileSymbol(')');

        vm.writeArithmetic(VMWriter.Command.NOT);
        vm.writeIf("WHILE_END"+String.valueOf(tmpCounterWhile));

        compileSymbol('{');
        compileStatements();
        compileSymbol('}');

        vm.writeGoto("WHILE_EXP"+String.valueOf(tmpCounterWhile));
        vm.writeLabel("WHILE_END"+String.valueOf(tmpCounterWhile));

    }

    /**
     * Realiza as chamadas recursivas para criar a árvores sintática a partir do "return".
     * Grava no arquivo de saida as instruções (se necessárias) para as intruções baseado na gramática do Jack.
     * Realiza as chamadas para navegar no arquivo fonte (.jack) avançando nos tokens já consumidos.
     * Respectivas gramática:  'return' expression? ';'
     */
    public void compileReturn() {
        
        saveTerminal("keyword",tokenizer.token());

        if( isTerm() ) {
            compileExpression();
        }
        compileSymbol(';');

        if(voidType) {
            vm.writePush(VMWriter.Segment.CONST,0);    
        } 
        vm.writeReturn();

    }

    /**
     * Realiza as chamadas recursivas para criar a árvores sintática a partir do procedimento "if".
     * Grava no arquivo de saida as instruções (se necessárias) para as intruções baseado na gramática do Jack.
     * Realiza as chamadas para navegar no arquivo fonte (.jack) avançando nos tokens já consumidos.
     * Respectivas gramática:  'if' '(' expression ')' '{' statements '}'
     *                         ('else' '{' statements '}')?
     */
    public void compileIf() {

        int tmpCounterIf = counterIf++;

        saveTerminal("keyword",tokenizer.token());
        compileSymbol('(');
        compileExpression();
        compileSymbol(')');

        vm.writeIf("IF_TRUE"+String.valueOf(tmpCounterIf));
        vm.writeGoto("IF_FALSE"+String.valueOf(tmpCounterIf));
        vm.writeLabel("IF_TRUE"+String.valueOf(tmpCounterIf));

        compileSymbol('{');
        compileStatements();
        compileSymbol('}');

        if(tokenizer.token().equals("else")) {
            vm.writeGoto("IF_END"+String.valueOf(tmpCounterIf));
        }

        vm.writeLabel("IF_FALSE"+String.valueOf(tmpCounterIf));

        if(tokenizer.token().equals("else")) {
            saveTerminal("keyword",tokenizer.token());
            compileSymbol('{');
            compileStatements();
            compileSymbol('}');
            vm.writeLabel("IF_END"+String.valueOf(tmpCounterIf));
        }

    }

    /**
     * Realiza as chamadas recursivas para criar a árvores sintática a partir de um expressão.
     * Grava no arquivo de saida as instruções (se necessárias) para as intruções baseado na gramática do Jack.
     * Realiza as chamadas para navegar no arquivo fonte (.jack) avançando nos tokens já consumidos.
     * Respectivas gramática:  term (op term)*
     */
    public void compileExpression() {
        saveOpenTag("expression",true);
        
        compileTerm();

        while(isOp()) {

            VMWriter.Command command = null;
            boolean multiply = false;
            boolean divide = false;

            if(tokenizer.token().equals("<")) {
                command = VMWriter.Command.LT;
                saveTerminal("symbol","&lt;");
            } else if(tokenizer.token().equals(">")) {
                command = VMWriter.Command.GT;
                saveTerminal("symbol","&gt;");
            } else if(tokenizer.token().equals("&")) {
                command = VMWriter.Command.AND;
                saveTerminal("symbol","&amp;");
            } else if(tokenizer.token().equals("|")) {
                command = VMWriter.Command.OR;
                saveTerminal("symbol",tokenizer.token());
            } else if(tokenizer.token().equals("-")) {
                command = VMWriter.Command.SUB;
                saveTerminal("symbol",tokenizer.token());
            } else if(tokenizer.token().equals("+")) {
                command = VMWriter.Command.ADD;
                saveTerminal("symbol",tokenizer.token());
            } else if(tokenizer.token().equals("=")) {
                command = VMWriter.Command.EQ;
                saveTerminal("symbol",tokenizer.token());
            } else if(tokenizer.token().equals("*")) {
                multiply = true;
                saveTerminal("symbol",tokenizer.token());
            } else if(tokenizer.token().equals("/")) {
                divide = true;
                saveTerminal("symbol",tokenizer.token());
            } else {
                saveTerminal("symbol",tokenizer.token());
            }

            compileTerm();

            if(command!=null) {
                vm.writeArithmetic(command);
            } else if(multiply) {
                vm.writeCall("Math.multiply",2);
            } else if(divide) {
                vm.writeCall("Math.divide",2);
            }

        }

        saveCloseTag("expression");
    }

    /**
     * Realiza as chamadas recursivas para criar a árvores sintática a partir de uma lista de expressões (possivelmente vazia).
     * Grava no arquivo de saida as instruções (se necessárias) para as intruções baseado na gramática do Jack.
     * Realiza as chamadas para navegar no arquivo fonte (.jack) avançando nos tokens já consumidos.
     * Respectivas gramática:  (expression (',' expression)* )?
     */
    public int compileExpressionList() {

        int nParameters = 0;

        saveOpenTag("expressionList",true);
        if( isTerm() ) {
            nParameters++;
            compileExpression();
            while( tokenizer.token().equals(",") ) {
                nParameters++;
                compileSymbol(',');
                compileExpression();
            }
        }
        saveCloseTag("expressionList");

        return nParameters;

    }

    /**
     * Realiza as chamadas recursivas para criar a árvores sintática a partir de um termo encontrado.
     * Essa rotina é mais complexas que as demais e no caso dos identificadores precisa tomar decisões
     * que podem levar a verificar tokens mais a frente.
     * Grava no arquivo de saida as instruções (se necessárias) para as intruções baseado na gramática do Jack.
     * Realiza as chamadas para navegar no arquivo fonte (.jack) avançando nos tokens já consumidos.
     * Respectivas gramática:  integerConstant | stringConstant | keywordConstant | varName | varName '['
     *                         expression ']' | subroutineCall | '(' expression ')' | unaryOp term
     */
    public void compileTerm() {
        saveOpenTag("term",true);
        if(tokenizer.tokenType(tokenizer.token())==JackTokenizer.TokenType.IDENTIFIER) {

            String identifier = tokenizer.token();
            compileIdentifier();

            boolean array = compileArray(identifier);   //if(tokenizer.token().equals("[")) { // array

            if(array) {
                vm.writePop(VMWriter.Segment.POINTER,1);
                vm.writePush(VMWriter.Segment.THAT,0);
            } else if( tokenizer.token().equals(".") || tokenizer.token().equals("(") ) { //subroutine

                subroutineCall1 = identifier;

                compileSubroutineCallCont();
            } else { // só variavel mesmo.

                push(identifier);

            }

        } else if(tokenizer.tokenType(tokenizer.token())==JackTokenizer.TokenType.INT_CONST) {

            vm.writePush(VMWriter.Segment.CONST,Integer.valueOf(tokenizer.token()));
            saveTerminal("integerConstant",tokenizer.token());

        } else if(tokenizer.tokenType(tokenizer.token())==JackTokenizer.TokenType.STRING_CONST) {

            vm.writeString(JackTokenizer.stringVal(tokenizer.token()));

            saveTerminal("stringConstant",tokenizer.stringVal(tokenizer.token()));
        } else if(tokenizer.token().equals("(")) {
            saveTerminal("symbol",tokenizer.token());
            compileExpression();
            saveTerminal("symbol",tokenizer.token());
        } else if(isUnaryOp()) {

            VMWriter.Command command = null;
            if(tokenizer.token().equals("-"))
                command = VMWriter.Command.NEG;
            if(tokenizer.token().equals("~"))
                command = VMWriter.Command.NOT;

            saveTerminal("symbol",tokenizer.token());
            compileTerm();

            if(command!=null) {
                vm.writeArithmetic(command);
            }

        } else {
            
            if(tokenizer.token().equals("true")) {
                vm.writePush(VMWriter.Segment.CONST,0);
                vm.writeArithmetic(VMWriter.Command.NOT);
            }
            else if(tokenizer.token().equals("false")) {
                vm.writePush(VMWriter.Segment.CONST,0);
            }
            else if(tokenizer.token().equals("null")) {
                vm.writePush(VMWriter.Segment.CONST,0);
            }
            else if(tokenizer.token().equals("this")) {
                vm.writePush(VMWriter.Segment.POINTER,0);
            }
            saveTerminal("keyword",tokenizer.token());

        }
        saveCloseTag("term");
    }



    /**
     * Realiza as chamadas recursivas para criar a árvores sintática a partir da .
     * Grava no arquivo de saida as instruções (se necessárias) para as intruções baseado na gramática do Jack.
     * Realiza as chamadas para navegar no arquivo fonte (.jack) avançando nos tokens já consumidos.
     * Respectivas gramática:  statement*
     */
    public void compileSubroutineCallCont() {

        int temp; 

        boolean isObj = false;

        String tmpS = subroutineSymbolTable.typeOf(subroutineCall1);
        Symbol.Kind kind = subroutineSymbolTable.kindOf(subroutineCall1);
        Integer index = subroutineSymbolTable.indexOf(subroutineCall1);

        if(tmpS==null) { // não encontrou no método
            tmpS  = classSymbolTable.typeOf(subroutineCall1);
            kind  = classSymbolTable.kindOf(subroutineCall1);
            index = classSymbolTable.indexOf(subroutineCall1);
        }
        if(tmpS==null) { // não encontrou em lugar nenhum, deve ser uma classe statica
            tmpS = subroutineCall1;
        } else {
            isObj = true;
        }
        if( tokenizer.token().equals("(") ) {  //'(' expressionList ')'
            // supondo sempre um método aqui.
            vm.writePush(VMWriter.Segment.POINTER,0);
            compileSymbol('(');
            temp = compileExpressionList();
            compileSymbol(')');
            vm.writeCall(className+"."+tmpS,temp+1);
        } else { // '.' subroutineName '(' expressionList ')'
            compileSymbol('.');
            String identifier = tokenizer.token();
            compileIdentifier();
            if(isObj) {
                VMWriter.Segment segment = convertKind(kind);
                vm.writePush(segment,index);
            }
            compileSymbol('(');
            temp = compileExpressionList();
            compileSymbol(')');
            if(isObj) {
                temp++;
            }
            vm.writeCall(tmpS+"."+identifier,temp);
        }

    }





/* ------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------ */
/* ------------------------------------------------------------------------------------------------ */







    public void compileIdentifier() {
        isIdentifier();
        saveTerminal("identifier",tokenizer.token());
    }

    public void compileSymbol(Character symbol) {
        isSymbol(symbol);
        if(tokenizer.symbol(tokenizer.token())!=symbol) {
            Error.error("Não encontrado symbol"+Character.toString(symbol)+". Encontrado: "+tokenizer.token());
        }
        saveTerminal("symbol",tokenizer.token());
    }

    public void compileType() {
        if(!isType()) {
            Error.error("Não encontrado keyword ou identifier. Encontrado: "+tokenizer.token());
        }
        if(tokenizer.tokenType(tokenizer.token())==JackTokenizer.TokenType.IDENTIFIER) {
            saveTerminal("identifier",tokenizer.token());    
        } else {
            saveTerminal("keyword",tokenizer.token());
        }
    }



    /**
     * Realiza as chamadas recursivas para criar a árvores sintática a partir de procedimentos "do".
     * Grava no arquivo de saida as instruções (se necessárias) para as intruções baseado na gramática do Jack.
     * Realiza as chamadas para navegar no arquivo fonte (.jack) avançando nos tokens já consumidos.
     * Respectivas gramática:  'do' subroutineCall ';'
     */
    public boolean compileArray(String identifier) {
        if( tokenizer.token().equals("[") ) {

            compileSymbol('[');
            compileExpression();
            compileSymbol(']');

            push(identifier);
            vm.writeArithmetic(VMWriter.Command.ADD);

            return true;
        }

        return false;
    }






    public boolean isIdentifier() {
        if(tokenizer.tokenType(tokenizer.token())!=JackTokenizer.TokenType.IDENTIFIER) {
            Error.error("Não encontrado identifier. Encontrado: "+tokenizer.token());
            return false;
        }
        return true;
    }

    public boolean isSymbol(Character symbol) {
        if(tokenizer.tokenType(tokenizer.token())!=JackTokenizer.TokenType.SYMBOL) {
            Error.error("Não encontrado symbol"+Character.toString(symbol)+". Encontrado: "+tokenizer.token());
            return false;
        }
        return true;
    }

    public boolean isType() {
        return (tokenizer.token().equals("int") ||
                tokenizer.token().equals("char") ||
                tokenizer.token().equals("boolean") ||
                tokenizer.tokenType(tokenizer.token())==JackTokenizer.TokenType.IDENTIFIER);
    }

    public boolean isStatement() {
        return (tokenizer.token().equals("let") ||
                tokenizer.token().equals("if") ||
                tokenizer.token().equals("while") ||
                tokenizer.token().equals("do") ||
                tokenizer.token().equals("return"));
                
    }

    public boolean isKeywordConstant() {
        return (tokenizer.token().equals("true") ||
            tokenizer.token().equals("false") ||
            tokenizer.token().equals("null") ||
            tokenizer.token().equals("this")); 
    }

    public boolean isUnaryOp() {
        return (tokenizer.token().equals("-") ||
            tokenizer.token().equals("~")); 
    }

//integerConstant | stringConstant | keywordConstant |
// varName | varName '[' expression ']' | subroutineCall |
// '(' expression ')' | unaryOp term
    public boolean isTerm() {
        return(
            tokenizer.tokenType(tokenizer.token())==JackTokenizer.TokenType.INT_CONST ||
            tokenizer.tokenType(tokenizer.token())==JackTokenizer.TokenType.STRING_CONST ||
            isKeywordConstant() ||
            tokenizer.tokenType(tokenizer.token())==JackTokenizer.TokenType.IDENTIFIER || //varName ou subroutineName
            tokenizer.token().equals("(") ||
            isUnaryOp()
        );
    }

    public boolean isOp() {
        return (tokenizer.token().equals("+") ||
            tokenizer.token().equals("-") ||
            tokenizer.token().equals("*") ||
            tokenizer.token().equals("/") ||
            tokenizer.token().equals("&") ||
            tokenizer.token().equals("|") ||
            tokenizer.token().equals("<") ||
            tokenizer.token().equals(">") ||
            tokenizer.token().equals("=")
        );
    }

    // convert 
    public static VMWriter.Segment convertKind(Symbol.Kind kind) {
        VMWriter.Segment segment = null;
        if(kind==Symbol.Kind.STATIC)
            segment = VMWriter.Segment.STATIC;
        if(kind==Symbol.Kind.FIELD)
            segment = VMWriter.Segment.THIS;
        if(kind==Symbol.Kind.ARG)
            segment = VMWriter.Segment.ARG;
        if(kind==Symbol.Kind.VAR)
            segment = VMWriter.Segment.LOCAL;
        return segment;
    }

    public void push(String identifier) {
        Symbol.Kind kind = subroutineSymbolTable.kindOf(identifier);
        Integer index = subroutineSymbolTable.indexOf(identifier);
        if(kind==null) {
            kind = classSymbolTable.kindOf(identifier);
            index = classSymbolTable.indexOf(identifier);
        }
        VMWriter.Segment segment = convertKind(kind);
        vm.writePush(segment,index);
    }

    public void pop(String identifier) {
        Symbol.Kind kind = subroutineSymbolTable.kindOf(identifier);
        Integer index = subroutineSymbolTable.indexOf(identifier);
        if(kind==null) {
            kind = classSymbolTable.kindOf(identifier);
            index = classSymbolTable.indexOf(identifier);
        }
        VMWriter.Segment segment = convertKind(kind);
        vm.writePop(segment,index);
    }

    // fecha o arquivo de escrita
    public void close() throws IOException {
        if(this.outputFileXML!=null)
            this.outputFileXML.close();
        if(this.vm!=null)
            this.vm.close();
    }

    public CompilationEngine(String inputfilename, String outputfilename, String outputfilenameT) {
    // CODIGO MOVIDO PARA O JACK ANALIZER
    }

    // Salva o texto no arquivo de saída.
    public void print(String text) {
        if(this.outputFileXML!=null) {
            this.outputFileXML.print(text);    
        }
    }

    public void saveOpenTag(String tag, boolean linebreak) {    
        print("<"+tag+">"); if(linebreak) { print("\n"); }
    }

    public void saveCloseTag(String tag) {   
        print("</"+tag+">\n");
    }
    
    public void saveName(String name) { 
        print(" "+name+" ");
    }

    public void saveTerminal(String tag, String name) {
        saveOpenTag(tag, false);
        saveName(name);
        saveCloseTag(tag);
        if(!tokenizer.advance()) return;
    }

}
