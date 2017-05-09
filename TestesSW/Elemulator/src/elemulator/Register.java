/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by lucas on 28/11/2016.
 * Update by Luciano Soares on 04/02/2017
 */

package elemulator;

public class Register {

    protected Converter converter = new Converter();

    protected boolean[] register;

    public Register(int bits) {
        this.register = new boolean[bits];
    }

    //public Register(int decimal) {
    //    this.register = converter.intToBoolean(decimal);
    //}

    public boolean[] getRegister() {
        return this.register;
    }

    public void loadRegister(boolean[] register, boolean load) {
        if (load) {
            //System.out.print("\n load Register");
            for (int i = 0; i<register.length; i++) {
                this.register[i] = register[i];
                //System.out.print(this.register[i]?"1":"0");
            }
        }
    }
}
