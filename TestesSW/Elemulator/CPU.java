//package elemulator;

public class CPU {

    private boolean[] outM, addressM, pcOut, muxAM; //outs
    private boolean writeM; //outs
    private ALU alu;

    public Register registerA, registerD;
    private ProgramCounter pc;
    private InstruDec id;
    private Mux mux;

    public CPU () {
        this.alu = new ALU();
        this.id = new InstruDec();
        this.registerA = new Register();
        this.registerD = new Register();
        this.mux = new Mux();
        this.pc = new ProgramCounter();

        this.addressM = registerA.getRegister();
        this.pcOut = pc.getRegister();
        this.writeM = id.isLoadM();
        this.outM = alu.getOut();
    }

    public void execute (boolean[] inM, boolean[] instruction, boolean reset) {

        Log.print("PC        ",pcOut);

        id.execute(instruction);
        this.writeM = id.isLoadM();

        this.addressM = registerA.getRegister();

        
        muxAM = mux.execute(inM, registerA.getRegister(), id.isMuxAMsel());
        alu.execute(registerD.getRegister(), muxAM, id.isZx(), id.isNx(), id.isZy(), id.isNy(), id.isF(), id.isNo());
        this.outM = alu.getOut();
        
        Log.print("ALU       ",alu.getOut(),"ng",alu.getNg(),"zr",alu.getZr());

        registerA.loadRegister(mux.execute(alu.getOut(), instruction, id.isMuxIOsel()), id.isLoadA());
        Log.print("REG-A     ",registerA.getRegister());
        
        registerD.loadRegister(alu.getOut(), id.isLoadD());
        Log.print("REG-D     ",registerD.getRegister());
        
        id.executeJump(instruction, alu.getZr(), alu.getNg());
        pc.execute(registerA.getRegister(), id.isLoadPC(), reset);
        this.pcOut = pc.getRegister();

    }

    public boolean[] getOutM() {
        return outM;
    }

    public boolean[] getAddressM() {
        return addressM;
    }

    public boolean[] getPcOut() {
        return pcOut;
    }

    public boolean isWriteM() {
        return writeM;
    }
}
