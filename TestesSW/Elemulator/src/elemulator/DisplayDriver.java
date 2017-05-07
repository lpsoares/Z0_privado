/**
 * Curso: Elementos de Sistemas
 * Arquivo: DisplayDriver.java
 * Created by lucas on 28/11/2016.
 * Update by Luciano on 04/02/2017
 */

package elemulator;

import java.util.ArrayList;

public class DisplayDriver {
    private Screen screen;
    private Converter converter;

    public DisplayDriver (Screen screen) {
        this.screen = screen;
        this.converter = new Converter();
    }

    public void update(boolean[] register, boolean[] index) {
        int cx = converter.booleanToInt(index) - 16384;
        int x = cx/(screen.getResolution()[0]/16);
        int y = (cx%(screen.getResolution()[0]/16))*16;

        screen.markPixel(x,y,register);

    }

}
