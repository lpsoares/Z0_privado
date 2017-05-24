/**
 * Curso: Elementos de Sistemas
 * Arquivo: JackAnalyzer.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 4/05/2017
 */

/*
******************************   Arquivo não mais usado   ******************************

**********************   Serviu para inciar estudo de árvore sintática *****************
*/

package compiler;

// import java.io.*;
// import java.nio.file.*;
// import java.util.ArrayList;

/**
 * Classe principal que orquestra a tradução do arquivo em linguagem de máquina virtual à pilha.
 * Opções:
 *   <arquivo vm>         primeiro parametro é o nome do arquivo jack a ser aberto 
 *   -o <arquivo xml>     parametro indica onde será salvo o arquivo gerado .xml
 */
class JackAnalyzer {
/*    public static void main(String[] args) {
        if (args.length < 1)  // checa se arquivo foi passado
            Error.error("informe o nome do arquivo vm");
        String inputFilename = null;
        String outputFilename = null;
        String outputFilenameX = null;
        String outputFilenameT = null;
        boolean debug = false;
        for (int i = 0; i < args.length; i++) {
            switch (args[i].charAt(0)) {
            case '-':
                if (args[i].charAt(1) == 'h') {
                    System.out.println("Opções");
                    System.out.println("<arquivo> : programa em linguagem de máquina a ser carregado");
                    System.out.println("-o <arquivo> : nome do arquivo para salvar no formato NASM");
                } else if (args[i].charAt(1) == 'o') {
                    outputFilename = args[i+1]; // arquivo output
                    i++;
                } else {Error.error("Argumento não reconhecido: "+args[i]);}
                break;
            default:
                inputFilename = args[i];
                break;
            }
        }

        try {
            Path path = new File(inputFilename).toPath().toAbsolutePath();
            ArrayList<String> files = new ArrayList<String>();
            // Cria um arquivo de saída dependendo se diretório.
            if(Files.isDirectory(path)) {
                int indexName = path.getNameCount()-1;
                if(path.getName(indexName).toString().equals(".")) {
                    indexName--;
                }
                DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);
                for (Path p : directoryStream) {
                    String extension = "";
                    int i = p.toString().lastIndexOf('.');
                    if (i > 0) {extension = p.toString().substring(i+1);}
                    if(extension.equals("jack")) {files.add(p.toString());}
                }
            } else {
                files.add(inputFilename);
                if(outputFilename==null) {
                    outputFilenameX = inputFilename.substring(0, inputFilename.lastIndexOf('.')) + ".xml";
                    outputFilenameT = inputFilename.substring(0, inputFilename.lastIndexOf('.')) + "T.xml";
                } else {
                    outputFilenameX = outputFilename;
                    outputFilenameT = outputFilename.replace(".xml","T.xml");
                }
            }
            for (String file : files) {
                if(outputFilename==null) {
                    outputFilenameX = file.replace(".jack",".xml");
                    outputFilenameT = file.replace(".jack","T.xml");
                }                
                CompilationEngine code = new CompilationEngine(file,outputFilenameX,outputFilenameT);
                code.compileClass();
                code.close();
            }
        } catch (IOException e) {
            Error.error("uma excessao de i/o foi lancada");
            System.exit(1);
        }
    }
    */
}
