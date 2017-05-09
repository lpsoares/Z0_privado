/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by lucas on 28/11/2016.
 * Update by Luciano Soares on 04/02/2017
 */

package elemulator;

import java.io.*;
import java.util.Scanner;
import java.util.Arrays;


public class RAM {

    public static final int RAM_SIZE = 32768;
    public static final int SCREEN = 16384;

    private Register[] ram;
    private Converter converter = new Converter();
    private DisplayDriver displayDriver;

    int bits;

    public RAM (DisplayDriver displayDriver, int bits) {
        this.bits = bits;
        this.ram = new Register[RAM_SIZE];
        this.displayDriver = displayDriver;
    }

    public boolean[] getSelectedValue (boolean[] index) {
        int decIndex = converter.booleanToInt(index);
        if(decIndex<0 || decIndex>=RAM_SIZE) {
            Error.error("Tentando acessar um endereço de memória RAM inválido: "+decIndex);
        }
        if (this.ram[decIndex] == null) {
            //this.ram[decIndex] = new Register();
            return new boolean[bits];
        } else {
            return this.ram[decIndex].getRegister();
        }
    }

    public void load(String memory_load) {
        if(memory_load!=null) {
            try {
                File memFile = new File(memory_load);
                InputStream memStream = new FileInputStream(memFile);
                Scanner s = new Scanner(memStream);
                while (s.hasNext()){
                    int decIndex = Integer.parseInt(s.next());
                    s.next();
                    boolean[] data = converter.stringToBoolean(s.next(),bits);
                    if (this.ram[decIndex] == null) this.ram[decIndex] = new Register(bits);
                    this.ram[decIndex].loadRegister(data, true);
                }
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void dump(String memory_dump) {
        Log.print("\n\nMemoria RAM\n");
        for(int i=0;i<RAM_SIZE;i++) {
            if(this.ram[i]!=null) {
                Log.print(String.format("%5d",i),this.ram[i].getRegister());
            }
        }
        if(memory_dump!=null) {
            try(  PrintWriter out = new PrintWriter(memory_dump)  ){
                for(int j=0;j<RAM_SIZE;j++) {
                    if(this.ram[j]!=null) {
                        out.print(String.format("%5d : ",j));
                        for (int i = (bits-1); i>=0;i--) {
                            if (this.ram[j].getRegister()[i]) out.print("1");
                            else out.print("0");
                        }
                        out.print("\n");
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void setSelectedValue(boolean[] register, boolean[] index, boolean load) { //o q vc quer gaurdar, onde e confirma
        int decIndex = converter.booleanToInt(index);
        if(decIndex<0 || decIndex>=RAM_SIZE) {
            Error.error("Tentando acessar um endereço de memória RAM inválido: "+decIndex);
        }
        if (load) {
            Log.print(String.format("MEM(%5d)",decIndex),register);
            if (this.ram[decIndex] == null) this.ram[decIndex] = new Register(bits);
            this.ram[decIndex].loadRegister(register, load);
            if (converter.booleanToInt(index) >= SCREEN) {
                //displayDriver.update(register, index);
                displayDriver.update(Arrays.copyOfRange(register, 0, 16), index);
            }
        }
    }


}
