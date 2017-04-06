/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by lucas on 28/11/2016.
 * Update by Luciano Soares on 04/02/2017
 */

public class Register {

    protected Converter converter = new Converter();

    protected boolean[] register;

    public Register() {
        this.register = new boolean[16];
    }

    public Register(int decimal) {
        this.register = converter.intToBoolean(decimal);
    }

    public boolean[] getRegister() {
        return this.register;
    }

    public void loadRegister(boolean[] register, boolean load) {
        if (load) {
            for (int i = 0; i<register.length; i++) this.register[i] = register[i];
        }
    }
}
