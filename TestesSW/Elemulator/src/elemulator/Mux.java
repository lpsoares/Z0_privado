/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by lucas on 28/11/2016.
 * Update by Luciano Soares on 04/02/2017
 */

public class Mux {
    public boolean[] execute (boolean[] a, boolean[] b, boolean load) {
        if(load) {return a;}
        else {return b;}
    }
}
