/**
 * Curso: Elementos de Sistemas
 * Arquivo: ALU.java
 * Created by lucas on 28/11/2016.
 * Update by Luciano on 04/02/2017
 */


public class ALU {
    private boolean[] out, outx, outy;
    private boolean zr;
    private boolean ng;

    public void execute(boolean[] x, boolean[] y, boolean zx, boolean nx, boolean zy, boolean ny, boolean f, boolean no) {

        zr = true;
        ng = false;

        outx = x;
        outy = y;

        if (zx) outx = clear();
        if (nx) outx = negate(outx);
        if (zy) outy = clear();
        if (ny) outy = negate(outy);

        if (f) out = and(outx,outy);
        else out = adder(outx,outy);

        if (no) out = negate(out);

        compareZr(out);
        compareNg(out);
    }

    private boolean[] clear () {
        boolean[] result = new boolean[16];
        for (int j = 0; j<result.length; j++) {
            result[j] = false;
        }
        return result;
    }

    private boolean[] negate (boolean[] a) {
        boolean[] result = new boolean[16];
        for (int i = 0; i<result.length; i++) {
            result[i] = !a[i];
        }
        return result;
    }

    private boolean[] and (boolean[] x, boolean[] y) {
        boolean[] result = new boolean[16];
        for (int i = 0; i<x.length; i++) {
            result[i] = x[i] && y[i];
        }
        return result;
    }

    private boolean[] adder (boolean[] x, boolean[] y) {
        boolean[] result = new boolean[16];
        for (int i = 0; i<x.length; i++) {
            int count = 0;
            if (x[i]) count++;
            if (y[i]) count++;
            if (result[i]) count++;
            if (count == 0) result[i] = false;
            else if (count == 1) result[i] = true;
            else if (count == 2) {
                result[i] = false;
                if(i != 15) {
                    result[i+1] = true;
                }
            }
            else if (count == 3) {
                result[i] = true;
                if(i != 15) {
                    result[i+1] = true;
                }
            }
        }
        return result;
    }

    private void compareNg (boolean[] a) {
        if (a[15]) ng = true;
    }

    private void compareZr (boolean[] a) {
        for  (int i = 0; i<a.length; i++) {
            if (a[i]){
                zr = false;
                break;
            }
        }
    }

    public boolean[] getOut () {
        return out;
    }

    public boolean getNg() {
        return ng;
    }

    public boolean getZr() {
        return zr;
    }
}
