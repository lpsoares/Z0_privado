/**
 * Curso: Elementos de Sistemas
 * Arquivo: CompilationEngine.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 6/05/2017
 */

//package vmtranslator;

/** 
 * Traduz da linguagem vm para códigos assembly.
 */
public class CompilationEngine {

    /** 
     * Abre o arquivo de entrada VM e se prepara para analisá-lo.
     * @param filename nome do arquivo NASM que receberá o código traduzido.
     */
    public CompilationEngine(String filename) {
        
    }

    /**
     * Grava no arquivo de saida as instruções em Assembly para executar o comando aritmético.
     * @param  command comando aritmético a ser analisado.
     */
    public void writeArithmetic(String command) {

    }


    // fecha o arquivo de escrita
    public void close() throws IOException {
        this.outputFile.close();
    }

}
