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

    PrintWriter outputFileXML = null;

    String className;
    String subroutineName;

    SymbolTable classSymbolTable;
    SymbolTable subroutineSymbolTable;

    VMWriter vm;

    int counterIf = 0;
    int counterWhile = 0;

    public CompilationEngine(String inputfilename, String outputfilename) {

        subroutineSymbolTable = new SymbolTable();

        // Para gravar o vm
        try  {
            tokenizer = new JackTokenizer(inputfilename);
            vm = new VMWriter(outputfilename);

        } catch (FileNotFoundException e){
            Error.error("Arquivo \'" + inputfilename + "\' nao encontrado");
            System.exit(1);
        } catch (IOException e) {
            Error.error("uma excessao de i/o foi lancada");
            System.exit(1);
        }
    }

    /** 
     * Abre o arquivo de entrada VM e se prepara para analisá-lo.
     * @param filename nome do arquivo NASM que receberá o código traduzido.
     */
    public CompilationEngine(String inputfilename, String outputfilename, String outputfilenameT) {
        
        subroutineSymbolTable = new SymbolTable();

        try  {
            tokenizer = new JackTokenizer(inputfilename);

            PrintWriter outputFileT = null;
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

                if(tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.STRING_CONST) {
                    outputFileT.print(tokenizer.stringVal(tokenizer.command()));
                } else if(tokenizer.command().equals("<")) {
                    outputFileT.print("&lt;");
                } else if(tokenizer.command().equals(">")) {
                    outputFileT.print("&gt;");
                } else if(tokenizer.command().equals("&")) {
                    outputFileT.print("&amp;");
                } else {
                    outputFileT.print(" "+tokenizer.command());
                }
                

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
            outputFileXML = new PrintWriter(new FileWriter(file));

        } catch (FileNotFoundException e){
            Error.error("Arquivo \'" + inputfilename + "\' nao encontrado");
            System.exit(1);
        } catch (IOException e) {
            Error.error("uma excessao de i/o foi lancada");
            System.exit(1);
        }

    }

    public void print(String text) {
        //System.out.print(text);
        //outputFileXML.print(text);        
    }

    public void saveOpenTag(String tag, boolean linebreak) {    
        print("<"+tag+">");
        if(linebreak) { print("\n"); }
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

        classSymbolTable = new SymbolTable();

        saveOpenTag("class",true);
        if(!tokenizer.advance()) return; // pega primeiro token
        if(tokenizer.tokenType(tokenizer.command())!=JackTokenizer.CommandType.KEYWORD ||
           tokenizer.keyWord(tokenizer.command())!=JackTokenizer.KeywordType.CLASS) {
            Error.error("Não encontrada class. Encontrado: "+tokenizer.command());
        }
        saveTerminal("keyword",tokenizer.command());

        className = tokenizer.command();
        compileIdentifier();
        
        compileSymbol('{');
        while( tokenizer.command().equals("static") || tokenizer.command().equals("field")) {
            saveOpenTag("classVarDec",true);
            compileClassVarDec();    
            saveCloseTag("classVarDec");
        }
        while( tokenizer.command().equals("constructor") || tokenizer.command().equals("function") || tokenizer.command().equals("method") ) {
            saveOpenTag("subroutineDec",true);
            compileSubroutineDec();    
            saveCloseTag("subroutineDec");
        }
        compileSymbol('}');
        saveCloseTag("class");

    }

    public void compileClassVarDec() {
        if(tokenizer.tokenType(tokenizer.command())!=JackTokenizer.CommandType.KEYWORD ||
           !(tokenizer.keyWord(tokenizer.command())==JackTokenizer.KeywordType.STATIC ||
             tokenizer.keyWord(tokenizer.command())==JackTokenizer.KeywordType.FIELD)) {
            Error.error("Não encontrada static ou field. Encontrado: "+tokenizer.command());
        }
        
        Symbol.Kind kind;
        if(tokenizer.keyWord(tokenizer.command())==JackTokenizer.KeywordType.STATIC) {
            kind = Symbol.Kind.STATIC;
        } else {
            kind = Symbol.Kind.FIELD;
        }
        saveTerminal("keyword",tokenizer.command());  // static ou field

        String type = tokenizer.command();
        compileType();

        String identifier = tokenizer.command();
        compileIdentifier();

        classSymbolTable.define(identifier, type, kind);

        while( tokenizer.command().equals(",") ) {
            compileSymbol(',');

            identifier = tokenizer.command();
            compileIdentifier();

            classSymbolTable.define(identifier, type, kind);

        }
        compileSymbol(';');
    }

    boolean voidType;
    boolean constructor;
    boolean method;
    boolean function;


    public void compileSubroutineDec() {

        subroutineSymbolTable.startSubroutine();
        counterIf = 0;
        counterWhile = 0;

        if(tokenizer.tokenType(tokenizer.command())!=JackTokenizer.CommandType.KEYWORD ||
           !(tokenizer.keyWord(tokenizer.command())==JackTokenizer.KeywordType.CONSTRUCTOR ||
             tokenizer.keyWord(tokenizer.command())==JackTokenizer.KeywordType.METHOD ||
             tokenizer.keyWord(tokenizer.command())==JackTokenizer.KeywordType.FUNCTION) ) {
            Error.error("Não encontrada constructor, method ou function. Encontrado: "+tokenizer.command());
        }

        method = false;
        constructor = false;
        function = false;
        if(tokenizer.keyWord(tokenizer.command())==JackTokenizer.KeywordType.METHOD) {
            subroutineSymbolTable.define("this", className, Symbol.Kind.ARG);
            method = true; 
        } else if(tokenizer.keyWord(tokenizer.command())==JackTokenizer.KeywordType.CONSTRUCTOR) {
            constructor = true;
        } else {
            function = true;
        }

        saveTerminal("keyword",tokenizer.command());

        voidType = false;
        if( tokenizer.command().equals("void") ) {
            voidType = true;
            saveTerminal("keyword",tokenizer.command());
        } else {
            compileType();    
        }
        
        subroutineName = tokenizer.command();
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
        String type = tokenizer.command();
        compileType();

        String identifier = tokenizer.command();
        compileIdentifier();

        subroutineSymbolTable.define(identifier, type, Symbol.Kind.ARG);    

        while( tokenizer.command().equals(",") ) {
            compileSymbol(',');

            type = tokenizer.command();
            compileType();

            identifier = tokenizer.command();
            compileIdentifier();

            subroutineSymbolTable.define(identifier, type, Symbol.Kind.ARG);    

        }
    }

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

    public void compileVarDec() {

        while(tokenizer.command().equals("var")) {
            saveOpenTag("varDec",true);
            saveTerminal("keyword",tokenizer.command());

            String type = tokenizer.command();
            compileType();

            String identifier = tokenizer.command();
            compileIdentifier();

            subroutineSymbolTable.define(identifier, type, Symbol.Kind.VAR);

            while( tokenizer.command().equals(",") ) {
                compileSymbol(',');

                identifier = tokenizer.command();
                compileIdentifier();

                subroutineSymbolTable.define(identifier, type, Symbol.Kind.VAR);                
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

    String subroutineCall1; // armazena o primeiro nome de uma chamada

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

        if( tokenizer.command().equals("(") ) {  //'(' expressionList ')'

            // supondo sempre um método aqui.
            vm.writePush(VMWriter.Segment.POINTER,0);

            compileSymbol('(');
            temp = compileExpressionList();
            compileSymbol(')');
    
            vm.writeCall(className+"."+tmpS,temp+1);

        } else { // '.' subroutineName '(' expressionList ')'
            compileSymbol('.');

            String identifier = tokenizer.command();

            compileIdentifier();

            if(isObj) {
                
                // if(function) {
                //     vm.writePush(VMWriter.Segment.LOCAL,0);    
                // } else {
                //     vm.writePush(VMWriter.Segment.THIS,1110);
                // }

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

    public void compileDo() {
        //'do' subroutineCall ';'
        saveTerminal("keyword",tokenizer.command());

        subroutineCall1 = tokenizer.command();
        compileIdentifier();  //subroutineName | (className|varName)
        
        compileSubroutineCallCont();

        vm.writePop(VMWriter.Segment.TEMP,0);

        compileSymbol(';');
    }

    public boolean compileArray(String identifier) {
        if( tokenizer.command().equals("[") ) {

            
            compileSymbol('[');
            compileExpression();
            compileSymbol(']');

            push(identifier);
            vm.writeArithmetic(VMWriter.Command.ADD);

            return true;
        }

        return false;
    }

    public void compileLet() {
        //'let' varName ('[' expression ']')? '=' expression ';'
        saveTerminal("keyword",tokenizer.command());

        String identifier = tokenizer.command();
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

    public void compileWhile() {
        //'while' '(' expression ')' '{' statements '}'

        int tmpCounterWhile = counterWhile++;

        vm.writeLabel("WHILE_EXP"+String.valueOf(tmpCounterWhile));

        saveTerminal("keyword",tokenizer.command());
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


    public void compileReturn() {
        // 'return' expression? ';'
        saveTerminal("keyword",tokenizer.command());

        if( isTerm() ) {
            compileExpression();
        }
        compileSymbol(';');

        if(voidType) {
            vm.writePush(VMWriter.Segment.CONST,0);    
        } 
        vm.writeReturn();

    }

    public void compileIf() {
        // 'if' '(' expression ')' '{' statements '}'
        // ('else' '{' statements '}')?

        int tmpCounterIf = counterIf++;

        saveTerminal("keyword",tokenizer.command());
        compileSymbol('(');
        compileExpression();
        compileSymbol(')');

        vm.writeIf("IF_TRUE"+String.valueOf(tmpCounterIf));
        vm.writeGoto("IF_FALSE"+String.valueOf(tmpCounterIf));
        vm.writeLabel("IF_TRUE"+String.valueOf(tmpCounterIf));

        compileSymbol('{');
        compileStatements();
        compileSymbol('}');

        if(tokenizer.command().equals("else")) {
            vm.writeGoto("IF_END"+String.valueOf(tmpCounterIf));
        }

        vm.writeLabel("IF_FALSE"+String.valueOf(tmpCounterIf));

        if(tokenizer.command().equals("else")) {
            saveTerminal("keyword",tokenizer.command());
            compileSymbol('{');
            compileStatements();
            compileSymbol('}');
            vm.writeLabel("IF_END"+String.valueOf(tmpCounterIf));
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

    public VMWriter.Segment convertKind(Symbol.Kind kind) {
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

    public void compileTerm() {
        saveOpenTag("term",true);
        if(tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.IDENTIFIER) {

            String identifier = tokenizer.command();
            compileIdentifier();

            boolean array = compileArray(identifier);   //if(tokenizer.command().equals("[")) { // array

            if(array) {
                vm.writePop(VMWriter.Segment.POINTER,1);
                vm.writePush(VMWriter.Segment.THAT,0);
            } else if( tokenizer.command().equals(".") || tokenizer.command().equals("(") ) { //subroutine

                subroutineCall1 = identifier;

                compileSubroutineCallCont();
            } else { // só variavel mesmo.

                push(identifier);

            }

        } else if(tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.INT_CONST) {

            vm.writePush(VMWriter.Segment.CONST,Integer.valueOf(tokenizer.command()));
            saveTerminal("integerConstant",tokenizer.command());

        } else if(tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.STRING_CONST) {

            vm.writeString(tokenizer.command());

            saveTerminal("stringConstant",tokenizer.stringVal(tokenizer.command()));
        } else if(tokenizer.command().equals("(")) {
            saveTerminal("symbol",tokenizer.command());
            compileExpression();
            saveTerminal("symbol",tokenizer.command());
        } else if(isUnaryOp()) {

            VMWriter.Command command = null;
            if(tokenizer.command().equals("-"))
                command = VMWriter.Command.NEG;
            if(tokenizer.command().equals("~"))
                command = VMWriter.Command.NOT;

            saveTerminal("symbol",tokenizer.command());
            compileTerm();

            if(command!=null) {
                vm.writeArithmetic(command);
            }

        } else {
            
            if(tokenizer.command().equals("true")) {
                vm.writePush(VMWriter.Segment.CONST,0);
                vm.writeArithmetic(VMWriter.Command.NOT);
            }
            else if(tokenizer.command().equals("false")) {
                vm.writePush(VMWriter.Segment.CONST,0);
            }
            else if(tokenizer.command().equals("null")) {
                vm.writePush(VMWriter.Segment.CONST,0);
            }
            else if(tokenizer.command().equals("this")) {
                vm.writePush(VMWriter.Segment.POINTER,0);
            }
            saveTerminal("keyword",tokenizer.command());

        }
        saveCloseTag("term");
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

            VMWriter.Command command = null;
            boolean multiply = false;
            boolean divide = false;

            if(tokenizer.command().equals("<")) {
                command = VMWriter.Command.LT;
                saveTerminal("symbol","&lt;");
            } else if(tokenizer.command().equals(">")) {
                command = VMWriter.Command.GT;
                saveTerminal("symbol","&gt;");
            } else if(tokenizer.command().equals("&")) {
                command = VMWriter.Command.AND;
                saveTerminal("symbol","&amp;");
            } else if(tokenizer.command().equals("|")) {
                command = VMWriter.Command.OR;
                saveTerminal("symbol",tokenizer.command());
            } else if(tokenizer.command().equals("-")) {
                command = VMWriter.Command.SUB;
                saveTerminal("symbol",tokenizer.command());
            } else if(tokenizer.command().equals("+")) {
                command = VMWriter.Command.ADD;
                saveTerminal("symbol",tokenizer.command());
            } else if(tokenizer.command().equals("=")) {
                command = VMWriter.Command.EQ;
                saveTerminal("symbol",tokenizer.command());
            } else if(tokenizer.command().equals("*")) {
                multiply = true;
                saveTerminal("symbol",tokenizer.command());
            } else if(tokenizer.command().equals("/")) {
                divide = true;
                saveTerminal("symbol",tokenizer.command());
            } else {
                saveTerminal("symbol",tokenizer.command());
            }

            //saveTerminal("symbol",tokenizer.command());

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

    public int compileExpressionList() {
        // (expression (',' expression)* )?

        int nParameters = 0;

        saveOpenTag("expressionList",true);
        if( isTerm() ) {
            nParameters++;
            compileExpression();
            while( tokenizer.command().equals(",") ) {
                nParameters++;
                compileSymbol(',');
                compileExpression();
            }
        }
        saveCloseTag("expressionList");

        return nParameters;

    }

    // fecha o arquivo de escrita
    public void close() throws IOException {
        if(this.outputFileXML!=null)
            this.outputFileXML.close();
        if(this.vm!=null)
            this.vm.close();
    }


}
