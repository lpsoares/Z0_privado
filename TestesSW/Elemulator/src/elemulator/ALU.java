/**
 * Curso: Elementos de Sistemas
 * Arquivo: ALU.java
 * Created by lucas on 28/11/2016.
 * Update by Luciano on 04/02/2017
 */

package elemulator;

public class ALU {
    private boolean[] out, outx, outy;
    private boolean zr;
    private boolean ng;

    int bits;

    public ALU(int bits) {
        this.bits = bits;
    }

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
        boolean[] result = new boolean[bits];
        // por padrão já é falso
        //  for (int j = 0; j<result.length; j++) {
        //    result[j] = false;
        //}
        return result;
    }

    private boolean[] negate (boolean[] a) {
        boolean[] result = new boolean[bits];
        for (int i = 0; i<bits; i++) {
            result[i] = !a[i];
        }
        return result;
    }

    private boolean[] and (boolean[] x, boolean[] y) {
        boolean[] result = new boolean[bits];
        for (int i = 0; i<bits; i++) {
            result[i] = x[i] && y[i];
        }
        return result;
    }

    private boolean[] adder (boolean[] x, boolean[] y) {
        boolean[] result = new boolean[bits];
        for (int i = 0; i<bits; i++) {
            // by Luciano
            if(i < bits-1) {
                result[i+1] = (x[i] & y[i]) | (x[i] & result[i]) | ( y[i] & result[i]);
            }
            result[i] = (x[i] ^ y[i]) ^ result[i];

            // by Lucas
            /*
            int count = 0;
            if (x[i]) count++;
            if (y[i]) count++;
            if (result[i]) count++;
            if (count == 0) result[i] = false;
            else if (count == 1) result[i] = true;
            else if (count == 2) {
                result[i] = false;
                if(i != (bits-1)) {
                    result[i+1] = true;
                }
            }
            else if (count == 3) {
                result[i] = true;
                if(i != (bits-1)) {
                    result[i+1] = true;
                }
            }
            */
            
        }

        // System.out.print("\n\nC"); for (int i = bits-1; i>=0; i--) System.out.print(carry[i]?"1":"0");
        // System.out.print("\nX");   for (int i = bits-1; i>=0; i--) System.out.print(x[i]?"1":"0"); 
        // System.out.print("\nY");   for (int i = bits-1; i>=0; i--) System.out.print(y[i]?"1":"0");
        // System.out.print("\nR");   for (int i = bits-1; i>=0; i--) System.out.print(result[i]?"1":"0");

        return result;
    }

    private void compareNg (boolean[] a) {
        if (a[bits-1]) ng = true;
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
