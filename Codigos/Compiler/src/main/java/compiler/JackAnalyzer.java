/**
 * Curso: Elementos de Sistemas
 * Arquivo: VMTranslator.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 2/05/2017
 */

package vmtranslator;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

/**
 * Classe principal que orquestra a tradução do arquivo em linguagem de máquina virtual à pilha.
 * Opções:
 *   <arquivo vm>         primeiro parametro é o nome do arquivo vm a ser aberto 
 *   -o <arquivo nasm>    parametro indica onde será salvo o arquivo gerado .nasm
 *   -n                   parametro indica não colocar rotina de bootstrap (conveniente para testar)
 */
class VMTranslator {

    public static void main(String[] args) {

        if (args.length < 1)  // checa se arquivo foi passado
            Error.error("informe o nome do arquivo vm");

        String inputFilename = null;
        String outputFilename = null;
        boolean debug = false;
        boolean bootstrap = true;

        for (int i = 0; i < args.length; i++) {
            switch (args[i].charAt(0)) {
            case '-':
                if (args[i].charAt(1) == 'h') {
                    System.out.println("Opções");
                    System.out.println("<arquivo> : programa em linguagem de máquina a ser carregado");
                    System.out.println("-o <arquivo> : nome do arquivo para salvar no formato NASM");
                    System.out.println("-n : não colocar rotina de bootstrap (conveniente para testar)");

                } else
                if (args[i].charAt(1) == 'o') {
                    outputFilename = args[i+1]; // arquivo output
                    i++;
                } else
                if (args[i].charAt(1) == 'n') {
                    bootstrap = false; // não insere rotina de bootstrap
                } else {
                    Error.error("Argumento não reconhecido: "+args[i]);
                }
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

                if(outputFilename==null) {
                    outputFilename = path.toString()+
                                 File.separator+
                                 path.getName(indexName).toString()+".nasm";
                }
                
                DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);
                for (Path p : directoryStream) {

                    String extension = "";
                    int i = p.toString().lastIndexOf('.');
                    if (i > 0) {
                        extension = p.toString().substring(i+1);
                    }
                    if(extension.equals("vm")) {
                        files.add(p.toString());
                    }
                }
            } else {
                files.add(inputFilename);
                if(outputFilename==null) {
                    outputFilename = inputFilename.substring(0, inputFilename.lastIndexOf('.')) + ".nasm";
                }
            }

            Code code = new Code(outputFilename);

            if(bootstrap) {
              code.writeInit();  
            }

            for (String file : files) {

                Parser parser = new Parser(file);

                code.vmfile(file);

                // Avança enquanto houver linhas para traduzir
                while (parser.advance()){
                    if(debug) {System.out.println(parser.command());}
                    
                    if( parser.commandType(parser.command())==Parser.CommandType.C_PUSH ||
                        parser.commandType(parser.command())==Parser.CommandType.C_POP
                    ) {
                        code.writePushPop(parser.commandType(parser.command()),
                                                      parser.arg1(parser.command()),
                                                      parser.arg2(parser.command())
                                                      );
                    } else if( parser.commandType(parser.command())==Parser.CommandType.C_LABEL) {
                        code.writeLabel(parser.arg1(parser.command()));

                    } else if( parser.commandType(parser.command())==Parser.CommandType.C_GOTO) {
                        code.writeGoto(parser.arg1(parser.command()));
                    
                    } else if( parser.commandType(parser.command())==Parser.CommandType.C_IF) {
                        code.writeIf(parser.arg1(parser.command()));

                    } else if( parser.commandType(parser.command())==Parser.CommandType.C_FUNCTION) {
                        code.writeFunction(parser.arg1(parser.command()),parser.arg2(parser.command()));

                    } else if( parser.commandType(parser.command())==Parser.CommandType.C_RETURN) {
                        code.writeReturn();
                    
                    } else if( parser.commandType(parser.command())==Parser.CommandType.C_CALL) {
                        code.writeCall(parser.arg1(parser.command()),parser.arg2(parser.command()));
                    
                    } else if( parser.commandType(parser.command())==Parser.CommandType.C_ARITHMETIC) {
                        code.writeArithmetic(parser.command());
                    } else {
                        Error.error("Comando não reconhecido");
                    }

                }
                parser.close();
            }
            code.close();

        } catch (FileNotFoundException e){
            Error.error("Arquivo \'" + inputFilename + "\' nao encontrado");
            System.exit(1);
        } catch (IOException e) {
            Error.error("uma excessao de i/o foi lancada");
            System.exit(1);
        }
    }
}
