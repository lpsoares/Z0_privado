//package elemulator;

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
