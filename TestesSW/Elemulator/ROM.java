//package elemulator;

//import br.edu.insper.elemulator.util.Converter;

public class ROM {
    private Register[] rom;
    private Converter converter = new Converter();

    public ROM () {
        this.rom = new Register[32768];
    }

    public void setSelectedInstruction(boolean[] instruction, int index) {
        this.rom[index] = new Register();
        this.rom[index].loadRegister(instruction, true);
    }

    public boolean[] getSelectedInstruction(boolean[] index) {
        int decIndex = converter.booleanToInt(index);

        return this.rom[decIndex].getRegister();
    }
}
