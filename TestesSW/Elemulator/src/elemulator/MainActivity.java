/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by lucas on 28/11/2016.
 * Update by Luciano on 04/02/2017
 */

package elemulator;

import java.util.*;
import java.io.*;
import java.lang.*;

public class MainActivity {

    public static void main(String[] args) {

        String code_file = "";
        String memory_load = null;
        String memory_dump = null;
        String display_dump = null;
        int clocks = 100;
        int bits = 16;
        int[] resolution = {512,256};

        for (int i = 0; i < args.length; i++) {
            switch (args[i].charAt(0)) {
            case '-':
                if (args[i].charAt(1) == 'h') {
                    System.out.println("Opções");
                    System.out.println("<arquivo> : programa em linguagem de máquina a ser carregado");
                    System.out.println("-d : Mostra informações de debug na tela");
                    System.out.println("-i <arquivo> : carrega arquivo com dados para memória RAM");
                    System.out.println("-o <arquivo> : salva arquivo com dados da memória RAM");
                    System.out.println("-p <arquivo> : salva arquivo com dados do display");
                    System.out.println("-c <clocks) : define quantidade de ciclos para rodar programa");
                    System.out.println("-r <x> <y>) : resolução do display em X e Y");
                    System.out.println("-b <bits>) : define bits da arquitetura, padrão 16");
                } else
                if (args[i].charAt(1) == 'd') {
                    Log.debug = true;
                } else
                if (args[i].charAt(1) == 'i') {
                    memory_load = args[i+1]; // arquivo entrada para ram
                    i++;
                } else
                if (args[i].charAt(1) == 'o') {
                    memory_dump = args[i+1]; // arquivo output
                    i++;
                } else
                if (args[i].charAt(1) == 'p') {
                    display_dump = args[i+1]; // arquivo output
                    i++;
                } else
                if (args[i].charAt(1) == 'c') {
                    clocks = Integer.parseInt(args[i+1]); // tics de clock
                    i++;
                } else
                if (args[i].charAt(1) == 'r') {
                    resolution[0] = Integer.parseInt(args[i+1]); // resolução em X
                    resolution[1] = Integer.parseInt(args[i+2]); // resolução em Y
                    i += 2;
                } else
                if (args[i].charAt(1) == 'b') {
                    bits = Integer.parseInt(args[i+1]); // resolução em X
                    i += 1;
                } else {
                    throw new IllegalArgumentException("Argumento não reconhecido: "+args[i]);
                }
                break;
            default:
                code_file = args[i];
                break;
            }
        }

        try {

            File initialFile = new File(code_file);
            InputStream targetStream = new FileInputStream(initialFile);

            final Screen screen = new Screen(resolution);
            final DisplayDriver dd = new DisplayDriver(screen);
            final Hack hack = new Hack(targetStream, dd, bits);
            //final Converter converter = new Converter();

            hack.load_ram(memory_load);

            int count = 0;

            while( (hack.pc_value <= hack.current_line-1) && (count < clocks) ) {
                hack.execute();
                count++;
            }
            
            hack.dump_ram(memory_dump);
            if(display_dump!=null) {
                screen.saveDisplay(display_dump);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
