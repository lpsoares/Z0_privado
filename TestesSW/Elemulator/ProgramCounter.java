/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by lucas on 28/11/2016.
 * Update by Luciano Soares on 04/02/2017
 */


public class ProgramCounter extends Register {

    public void execute (boolean[] register, boolean load, boolean reset) {
        if (reset) reset();
        else if (load) loadRegister(register, load);
        else increment();
    }

    private void increment() {
        int decimal = converter.booleanToInt(register);
        decimal++;
        register = converter.intToBoolean(decimal);
    }

    private void reset () {
        for  (int i = 0; i< register.length; i++) {
            register[i] = false;
        }
    }
}
