//package elemulator;

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

    public Hack(InputStream file, DisplayDriver dd) {
        this.ram = new RAM(dd);
        this.rom = new ROM();
        this.cpu = new CPU();
        this.converter = new Converter();
        this.reset = false;

        this.current_line = 0;

        Scanner s = new Scanner(file);

        while (s.hasNext()){
            boolean[] instruction = converter.stringToBoolean(s.next());
            rom.setSelectedInstruction(instruction, current_line);
            current_line++;
        }
        s.close();

    }

    public void execute() {
        pc_value = converter.booleanToInt(cpu.getPcOut());

        Log.print("\nInstrução ",rom.getSelectedInstruction(cpu.getPcOut()));

        cpu.execute(ram.getSelectedValue(cpu.getAddressM()), rom.getSelectedInstruction(cpu.getPcOut()), reset);
        ram.setSelectedValue(cpu.getOutM(), cpu.getAddressM(), cpu.isWriteM());
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
