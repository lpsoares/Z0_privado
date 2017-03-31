import java.io.*;

class AssemblerZ0 {

    public static void main(String[] args) {

        if (args.length < 1)  // checa se arquivo foi passado
            Error.error("informe o nome do arquivo assembly Z0 nasm");

        String inputFile = null;
        String outputFile = null;
        boolean debug = false;

        for (int i = 0; i < args.length; i++) {
            switch (args[i].charAt(0)) {
            case '-':
                if (args[i].charAt(1) == 'h') {
                    System.out.println("Opções");
                    System.out.println("<arquivo> : programa em linguagem de máquina a ser carregado");
                    System.out.println("-d : Mostra informações de debug na tela");
                    System.out.println("-o <arquivo> : salva arquivo com dados da memória RAM");
                } else
                if (args[i].charAt(1) == 'd') {
                    debug = true;
                } else
                if (args[i].charAt(1) == 'o') {
                    outputFile = args[i+1]; // arquivo output
                    i++;
                } else {
                    throw new IllegalArgumentException("Argumento não reconhecido: "+args[i]);
                }
                break;
            default:
                inputFile = args[i];
                break;
            }
        }

        try {
            Assemble assembler = new Assemble(inputFile, outputFile, debug);
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
