//package elemulator;

import java.util.ArrayList;

//import br.edu.insper.elemulator.util.Converter;

/**
 * Created by lucas on 28/11/2016.
 */

public class DisplayDriver {
    private Screen screen;
    private Converter converter;


    public DisplayDriver (Screen screen) {
        this.screen = screen;
        this.converter = new Converter();
    }

    public void update(boolean[] register, boolean[] index) {
        int cx = converter.booleanToInt(index) - 16384;
        int y = cx/32;
        ArrayList<Integer> x = new ArrayList<>();

        for (int i = 0; i < register.length;i++) {
            if (register[i]) {
                x.add((cx%32)*16 + i);
            }
        }

        screen.markPixel(x,y);

    }

}
