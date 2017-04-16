/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 04/02/2017
 */

package assembler;


public class Code {

    // Identifica o destino
    private static int checkDest(String s) throws InvalidDestException {
        if (s.equals("%A"))
            return(0);
        else if (s.equals("%D"))
            return(1);
        else if (s.equals("(%A)"))
            return(2);
        else {
            Error.error("Instrução invalida");
            throw new InvalidDestException();
        }
    }

    // retorna o opcode (3 bits) a partir do mnemonico de destino
    public static String dest(String[] array) throws InvalidDestException {

        if ( array.length == 0 || array[0].equals("")) {
            Error.error("Instrução invalida");
            throw new InvalidDestException();
        }

        char[] dests = "000".toCharArray();

        if (array[0].startsWith("mov")) {
            if(array.length==3) dests[checkDest(array[2])]='1';
            if(array.length==4) dests[checkDest(array[3])]='1';
            if(array.length==5) dests[checkDest(array[4])]='1';
            if(array.length > 5) Error.error("Mais de três destinos em MOV");
        } else if (array[0].startsWith("add")) {
            if(array.length==4) dests[checkDest(array[3])]='1';
            if(array.length==5) dests[checkDest(array[4])]='1';
            if(array.length==6) dests[checkDest(array[5])]='1';
            if(array.length > 6) Error.error("Mais de três destinos em ADD");
        } else if (array[0].startsWith("sub")) {
            if(array.length==4) dests[checkDest(array[3])]='1';
            if(array.length==5) dests[checkDest(array[4])]='1';
            if(array.length==6) dests[checkDest(array[5])]='1';
            if(array.length > 6) Error.error("Mais de três destinos em SUB");
        } else if (array[0].startsWith("rsub")) {
            if(array.length==4) dests[checkDest(array[3])]='1';
            if(array.length==5) dests[checkDest(array[4])]='1';
            if(array.length==6) dests[checkDest(array[5])]='1';
            if(array.length > 6) Error.error("Mais de três destinos em RSUB");
        } else if (array[0].startsWith("inc")) {
            if(array.length==2) dests[checkDest(array[1])]='1';
            if(array.length > 2) Error.error("Mais de um destino em INC");
        } else if (array[0].startsWith("dec")) {
            if(array.length==2) dests[checkDest(array[1])]='1';
            if(array.length > 2) Error.error("Mais de um destino em DEC");
        } else if (array[0].startsWith("not")) {
            if(array.length==2) dests[checkDest(array[1])]='1';
            if(array.length > 2) Error.error("Mais de um destino em NOT");
        } else if (array[0].startsWith("neg")) {
            if(array.length==2) dests[checkDest(array[1])]='1';
            if(array.length > 2) Error.error("Mais de um destino em NEG");
        } else if (array[0].startsWith("and")) {
            if(array.length==4) dests[checkDest(array[3])]='1';
            if(array.length==5) dests[checkDest(array[4])]='1';
            if(array.length==6) dests[checkDest(array[5])]='1';
            if(array.length > 6) Error.error("Mais de três destinos em AND");
        } else if (array[0].startsWith("or")) {
            if(array.length==4) dests[checkDest(array[3])]='1';
            if(array.length==5) dests[checkDest(array[4])]='1';
            if(array.length==6) dests[checkDest(array[5])]='1';
            if(array.length > 6) Error.error("Mais de três destinos em OR");
        } else {
            // instrução não possui destino
        }

        String value_dest = String.valueOf(dests);

        return value_dest;
    }

    // retorna o opcode (7 bits) a partir do mnemonico de cálculo
    public static String comp(String[] mnemnonic) throws InvalidCompException {

        if ( mnemnonic.length == 0 || mnemnonic[0].equals("")) {
            Error.error("Instrução invalida");
            throw new InvalidCompException();
        }

        String comp = "";

        if (mnemnonic[0].startsWith("mov")) {
            comp = mnemnonic[1];
        } else if (mnemnonic[0].startsWith("add")) {
            comp = mnemnonic[1]+"+"+mnemnonic[2];
        } else if (mnemnonic[0].startsWith("sub")) {
            comp = mnemnonic[1]+"-"+mnemnonic[2];
        } else if (mnemnonic[0].startsWith("rsub")) {
            comp = mnemnonic[2]+"-"+mnemnonic[1];
        } else if (mnemnonic[0].startsWith("inc")) {
            comp = mnemnonic[1]+"+1";
        } else if (mnemnonic[0].startsWith("dec")) {
            comp = mnemnonic[1]+"-1";
        } else if (mnemnonic[0].startsWith("not")) {
            comp = "!"+mnemnonic[1];
        } else if (mnemnonic[0].startsWith("neg")) {
            comp = "-"+mnemnonic[1];
        } else if (mnemnonic[0].startsWith("and")) {
            comp = mnemnonic[1]+"&"+mnemnonic[2];
        } else if (mnemnonic[0].startsWith("or")) {
            comp = mnemnonic[1]+"|"+mnemnonic[2];
        } else if (mnemnonic[0].startsWith("nop")) {
            comp = "$0";
        } else if ( mnemnonic[0].startsWith("jg")  ||
                    mnemnonic[0].startsWith("je")  ||
                    mnemnonic[0].startsWith("jge") ||
                    mnemnonic[0].startsWith("jl")  ||
                    mnemnonic[0].startsWith("jne") ||
                    mnemnonic[0].startsWith("jle") ||
                    mnemnonic[0].startsWith("jmp") ) {
            comp = "%D";
        } else {
            Error.error("Instrução invalida");
            throw new InvalidCompException();
        }

        String a = "0";
        if (comp.contains("(%A)")) {
            a = "1";
            comp = comp.replace("(%A)", "%A");
        }

        String c = "000000";
        switch (comp) {
            case "$0":    c = "101010"; break;
            case "$1":    c = "111111"; break;
            case "$-1":   c = "111010"; break;
            case "%D":    c = "001100"; break;
            case "%A":    c = "110000"; break;
            case "!%D":   c = "001101"; break;
            case "!%A":   c = "110001"; break;
            case "-%D":   c = "001111"; break;
            case "-%A":   c = "110011"; break;
            case "%D+1":  c = "011111"; break;
            case "%D+$1": c = "011111"; break;
            case "%A+1":  c = "110111"; break;
            case "%A+$1": c = "110111"; break;
            case "%D-1":  c = "001110"; break;
            case "%D-$1": c = "001110"; break;
            case "%A-1":  c = "110010"; break;
            case "%A-$1": c = "110010"; break;
            case "%D+%A": c = "000010"; break;
            case "%A+%D": c = "000010"; break;
            case "%D-%A": c = "010011"; break;
            case "%A-%D": c = "000111"; break;
            case "%D&%A": c = "000000"; break;
            case "%D|%A": c = "010101"; break;
            default: throw new InvalidCompException();
        }

        return a + c;

    }

    // retorna o opcode (3 bits) a partir do mnemonico de jump (salto)
    public static String jump(String[] mnemnonic) throws InvalidJumpException {
        
        if ( mnemnonic.length == 0 || mnemnonic[0].equals("")) {
            Error.error("Jump invalido");
            throw new InvalidJumpException();
        }

        switch (mnemnonic[0]){
            case "jg"  : return "001";
            case "je"  : return "010";
            case "jge" : return "011";
            case "jl"  : return "100";
            case "jne" : return "101";
            case "jle" : return "110";
            case "jmp" : return "111";
            default    : return "000";
        }

    }

    // converte um simbolo numerico para binario de 15 bits
    public static String toBinary(String symbol) {
        int value = Integer.valueOf(symbol);
        String binary = Integer.toBinaryString(value);
        return String.format("%1$15s", binary).replace(" ", "0");
    }
}
