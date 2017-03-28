import java.io.*;

class Assembler {

    public static void main(String[] args) {

        if (args.length != 1)  // checa se arquivo foi passado
            Error.error("informe o nome do arquivo nasm");

        String inputFile = args[0];

        try {
            Assemble assembler = new Assemble(inputFile);
            assembler.assemble1();
            assembler.assemble2();
            assembler.close();
        } catch (FileNotFoundException ex){
            Error.error("Arquivo \'" + inputFile + "\' nao encontrado");
        } catch (IOException ex) {
            Error.error("uma excessao de i/o foi lancada");
        }
    }
}
