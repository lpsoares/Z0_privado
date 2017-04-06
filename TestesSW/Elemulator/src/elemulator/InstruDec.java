/**
 * Curso: Elementos de Sistemas
 * Arquivo: InstruDec.java
 * Created by lucas on 28/11/2016.
 * Update by Luciano on 04/02/2017
 */

package elemulator;

public class InstruDec {

    private boolean muxIOsel, muxAMsel, zx, nx, zy, ny, f, no, loadA, loadD, loadM, loadPC;

    public void execute (boolean[] instruction) {

        muxIOsel = instruction[15];
        muxAMsel = instruction[12];
        zx = instruction[11];
        nx = instruction[10];
        zy = instruction[9];
        ny = instruction[8];
        f = !instruction[7];
        no = instruction[6];
        loadA = (!instruction[15]) || (instruction[15] && instruction[5]);
        loadD = instruction[4] && instruction[15];
        loadM = instruction[3] && instruction[15];
    }

    public void executeJump(boolean[] instruction, boolean zr, boolean ng) {

        boolean loadPC1, loadPC2, loadPC3, nng, nzr, gt;

        nng = !ng;
        nzr = !zr;
        gt = nng && nzr;
        
        loadPC1 = gt && instruction[0];
        loadPC2 = zr && instruction[1];
        loadPC3 = ng && instruction[2];
        
        loadPC = (loadPC1 || loadPC2 || loadPC3) && instruction[15];

    }


    public boolean isMuxIOsel() {return muxIOsel;}
    public boolean isMuxAMsel() {return muxAMsel;}
    public boolean isZx() {return zx;}
    public boolean isNx() {return nx;}
    public boolean isZy() {return zy;}
    public boolean isNy() {return ny;}
    public boolean isF() {return f;}
    public boolean isNo() {return no;}
    public boolean isLoadA() {return loadA;}
    public boolean isLoadD() {return loadD;}
    public boolean isLoadM() {return loadM;}
    public boolean isLoadPC() {return loadPC;}

}
