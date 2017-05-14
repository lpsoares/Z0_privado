/**
 * Curso: Elementos de Sistemas
 * Arquivo: CPU.java
 * Created by lucas on 28/11/2016.
 * Update by Luciano on 04/02/2017
 */

package elemulator;

import java.util.Arrays;

public class CPU {

    private boolean[] outM, addressM, pcOut, muxAM; //outs
    private boolean writeM; //outs
    private ALU alu;

    public Register registerA, registerD;
    private ProgramCounter pc;
    private InstruDec id;
    private Mux mux;
    int bits;

    public CPU (int bits) {

        this.bits = bits;

        this.alu = new ALU(bits);
        this.id = new InstruDec(bits);
        this.registerA = new Register(bits);
        this.registerD = new Register(bits);
        this.mux = new Mux();
        this.pc = new ProgramCounter(bits);

        this.addressM = registerA.getRegister();
        this.pcOut = pc.getRegister();
        this.writeM = id.isLoadM();
        this.outM = alu.getOut();
    }

    public void execute (boolean[] inM, boolean[] instruction, boolean reset) {

        //Log.print("PC        ",pcOut);

        id.execute(instruction);
        this.writeM = id.isLoadM();

        this.addressM = registerA.getRegister();

        
        muxAM = mux.execute(inM, registerA.getRegister(), id.isMuxAMsel());
        alu.execute(registerD.getRegister(), muxAM, id.isZx(), id.isNx(), id.isZy(), id.isNy(), id.isF(), id.isNo());
        this.outM = alu.getOut();
        
        //Log.print("ALU       ",alu.getOut(),"ng",alu.getNg(),"zr",alu.getZr());

        registerA.loadRegister(mux.execute(alu.getOut(), instruction, id.isMuxIOsel()), id.isLoadA());
        //Log.print("REG-A     ",registerA.getRegister());
        
        registerD.loadRegister(alu.getOut(), id.isLoadD());
        //Log.print("REG-D     ",registerD.getRegister());
        
        id.executeJump(instruction, alu.getZr(), alu.getNg());
        pc.execute(registerA.getRegister(), id.isLoadPC(), reset);
        this.pcOut = pc.getRegister();

        //System.out.println(id.isLoadPC());

    }

    public boolean[] getOutM() {
        return outM;
    }

    public boolean[] getAddressM(int start, int end) {
        return Arrays.copyOfRange(addressM, start, end);
    }

    public boolean[] getPcOut() {
        return pcOut;
    }

    public boolean isWriteM() {
        return writeM;
    }
}
