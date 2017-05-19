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


    public void compileIdentifier() {
        if(tokenizer.tokenType(tokenizer.command())!=JackTokenizer.CommandType.IDENTIFIER) 
        {
            Error.error("Não encontrado identifier. Encontrado: "+tokenizer.command());
        }

        System.out.print("<identifier>");
        System.out.print(" "+tokenizer.command()+" ");
        System.out.println("</identifier>"); 
    }

    public void compileSymbol(Character symbol) {
        if(tokenizer.tokenType(tokenizer.command())!=JackTokenizer.CommandType.SYMBOL) 
        {
            Error.error("Não encontrado symbol"+Character.toString(symbol)+". Encontrado: "+tokenizer.command());
        }
        if(tokenizer.symbol(tokenizer.command())!=symbol) 
        {
            Error.error("Não encontrado symbol"+Character.toString(symbol)+". Encontrado: "+tokenizer.command());
        }

        System.out.print("<symbol>");
        System.out.print(" "+tokenizer.command()+" ");
        System.out.println("</symbol>"); 
    }

    public void compileType() {
        if(!(tokenizer.command().equals("int") ||
             tokenizer.command().equals("char") ||
             tokenizer.command().equals("boolean") ||
             tokenizer.tokenType(tokenizer.command())==JackTokenizer.CommandType.IDENTIFIER) ) 
        {
            Error.error("Não encontrado keyword ou identifier. Encontrado: "+tokenizer.command());
        }

        System.out.print("<keyword>");
        System.out.print(" "+tokenizer.command()+" ");
        System.out.println("</keyword>"); 
    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para executar o comando aritmético.
     * @param  command comando aritmético a ser analisado.
     */
    public void compileClass() {

//        outputFile.println("<tokens>");

        System.out.println("<class>");

        if(!tokenizer.advance()) return;
        if(tokenizer.tokenType(tokenizer.command())!=JackTokenizer.CommandType.KEYWORD ||
           tokenizer.keyWord(tokenizer.command())!=JackTokenizer.KeywordType.CLASS)
        {
            Error.error("Não encontrada class. Encontrado: "+tokenizer.command());
        }

        System.out.print("<keyword>");
        System.out.print(" class ");
        System.out.println("</keyword>");        

        if(!tokenizer.advance()) return;
        compileIdentifier();

        if(!tokenizer.advance()) return;
        compileSymbol('{');

        if(!tokenizer.advance()) return;
        while( tokenizer.command().equals("static") || tokenizer.command().equals("field")) {
            compileClassVarDec();    
        }

        System.out.println("</class>");

        outputFile.close();


    }

    public void compileClassVarDec() {

        System.out.println("<classVarDec>");

        if(tokenizer.tokenType(tokenizer.command())!=JackTokenizer.CommandType.KEYWORD ||
           !(tokenizer.keyWord(tokenizer.command())==JackTokenizer.KeywordType.STATIC ||
             tokenizer.keyWord(tokenizer.command())==JackTokenizer.KeywordType.FIELD))
        {
            Error.error("Não encontrada static ou field. Encontrado: "+tokenizer.command());
        }

        if(tokenizer.keyWord(tokenizer.command())==JackTokenizer.KeywordType.STATIC) {
            System.out.print("<keyword>");
            System.out.print(" static ");
            System.out.println("</keyword>");
        } else {
            System.out.print("<keyword>");
            System.out.print(" field ");
            System.out.println("</keyword>");
        }

        if(!tokenizer.advance()) return;
        compileType();

        if(!tokenizer.advance()) return;
        compileIdentifier();
        
        if(!tokenizer.advance()) return;
        while( tokenizer.command().equals(",") ) {
            compileSymbol(',');
            if(!tokenizer.advance()) return;
            compileIdentifier();
        }

        compileSymbol(';');

        System.out.println("</classVarDec>");

    }

    public void compileSubroutine() {
    }

    public void compileParameterList() {
    }

    public void compileVarDec() {
    }

    public void compileStatements() {
    }

    public void compileDo() {
    }

    public void compileLet() {
    }

    public void compileWhile() {
    }

    public void compileReturn() {
    }

    public void compileIf() {
    }

    public void compileExpression() {
    }

    public void compileTerm() {
    }

    public void compileExpressionList() {
    }
    // fecha o arquivo de escrita
    public void close() throws IOException {
        this.outputFile.close();
    }

}
