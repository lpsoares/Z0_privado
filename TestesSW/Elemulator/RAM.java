//package elemulator;

//import br.edu.insper.elemulator.controller.DisplayDriver;
//import br.edu.insper.elemulator.util.Converter;

import java.io.*;
import java.util.Scanner;

public class RAM {
    private Register[] ram;
    private Converter converter = new Converter();
    private DisplayDriver displayDriver;

    public RAM (DisplayDriver displayDriver) {
        this.ram = new Register[32768];
        this.displayDriver = displayDriver;
    }

    public boolean[] getSelectedValue (boolean[] index) {
        int decIndex = converter.booleanToInt(index);
        if (this.ram[decIndex] == null) {
            //this.ram[decIndex] = new Register();
            return new boolean[16];
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
                    boolean[] data = converter.stringToBoolean(s.next());
                    if (this.ram[decIndex] == null) this.ram[decIndex] = new Register();
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
        for(int i=0;i<32768;i++) {
            if(this.ram[i]!=null) {
                Log.print(String.format("%5d",i),this.ram[i].getRegister());
            }
        }
        if(memory_dump!=null) {
            try(  PrintWriter out = new PrintWriter(memory_dump)  ){
                for(int j=0;j<32768;j++) {
                    if(this.ram[j]!=null) {
                        out.print(String.format("%5d : ",j));
                        for (int i = 15; i>=0;i--) {
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

        if (load) {
            Log.print(String.format("MEM(%5d)",decIndex),register);
            if (this.ram[decIndex] == null) this.ram[decIndex] = new Register();
            this.ram[decIndex].loadRegister(register, load);
            if (converter.booleanToInt(index) >= 16384) {
                displayDriver.update(register, index);
            }
        }
    }


}
