/**
 * Curso: Elementos de Sistemas
 * Arquivo: Hack.java
 * Created by lucas on 28/11/2016.
 * Update by Luciano on 04/02/2017
 */

package elemulator;

import java.io.InputStream;
import java.util.Scanner;

public class Hack {

    boolean reset;
    RAM ram;
    ROM rom;
    CPU cpu;
    Converter converter;
    int current_line;
    int pc_value;
    int bits;

    public Hack(InputStream file, DisplayDriver dd, int bits) {
        this.ram = new RAM(dd,bits);
        this.rom = new ROM(bits);
        this.cpu = new CPU(bits);
        this.converter = new Converter();
        this.reset = false;

        this.current_line = 0;

        Scanner s = new Scanner(file);

        while (s.hasNext()){
            String nextValue = s.next();
            if(nextValue.length() != bits) {
                Error.error("Tamanho da instrução diferente de "+String.valueOf(bits)+" bits : "+nextValue.length());
            }
            boolean[] instruction = converter.stringToBoolean(nextValue,bits);
            rom.setSelectedInstruction(instruction, current_line);
            current_line++;
        }
        s.close();

    }

    public void execute() {
        pc_value = converter.booleanToInt(cpu.getPcOut());

        Log.print("\nInstrução ",rom.getSelectedInstruction(cpu.getPcOut()));

        cpu.execute(ram.getSelectedValue(cpu.getAddressM(0, 15)), rom.getSelectedInstruction(cpu.getPcOut()), reset);

        ram.setSelectedValue(cpu.getOutM(), cpu.getAddressM(0, 15), cpu.isWriteM());
        this.reset = false;

        pc_value = converter.booleanToInt(cpu.getPcOut());

    }

    public void load_ram(String memory_load) {
        ram.load(memory_load);
    }

    public void dump_ram(String memory_dump) {
        ram.dump(memory_dump);
    }

}
