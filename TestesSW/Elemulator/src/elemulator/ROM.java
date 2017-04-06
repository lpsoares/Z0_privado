/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by lucas on 28/11/2016.
 * Update by Luciano Soares on 04/02/2017
 */

package elemulator;

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
