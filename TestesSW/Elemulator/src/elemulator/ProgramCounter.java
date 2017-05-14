/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by lucas on 28/11/2016.
 * Update by Luciano Soares on 04/02/2017
 */

package elemulator;

public class ProgramCounter extends Register {

    int bits;
    public ProgramCounter(int bits) {
        super(bits);
        this.bits = bits;
    }

    public void execute (boolean[] register, boolean load, boolean reset) {
        if (reset) reset();
        else if (load) loadRegister(register, load);
        else increment();
    }

    private void increment() {

        // by Luciano
        boolean[] carry = new boolean[bits];
        carry[0]=true;
        for (int i = 0; i<bits; i++) {
            if(i < bits-1) {
                carry[i+1] = register[i] & carry[i];
            }
            register[i] = register[i] ^ carry[i];
        }

        // by Lucas
        //int decimal = converter.booleanToInt(register);
        //decimal++;
        //register = converter.intToBoolean(decimal,bits);
    }

    private void reset () {
        for  (int i = 0; i< register.length; i++) {
            register[i] = false;
        }
    }
}
