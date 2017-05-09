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

    int bits;

    public ROM (int bits) {
        //this.rom = new Register[(int)(Math.round(Math.pow(2,bits-1)-1))];
        if(bits==16) {
            this.rom = new Register[32768];    
        } else {
            this.rom = new Register[1048576];    
        }
        this.bits = bits;
    }

    public void setSelectedInstruction(boolean[] instruction, int index) {
        this.rom[index] = new Register(bits);
        this.rom[index].loadRegister(instruction, true);
    }

    public boolean[] getSelectedInstruction(boolean[] index) {
        int decIndex = converter.booleanToInt(index);

        return this.rom[decIndex].getRegister();
    }
}
