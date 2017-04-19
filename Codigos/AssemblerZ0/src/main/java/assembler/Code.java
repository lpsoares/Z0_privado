/**
 * Curso: Elementos de Sistemas
 * Arquivo: MainActivity.java
 * Created by Luciano Soares <lpsoares@insper.edu.br> 
 * Date: 04/02/2017
 */

package assembler;

/** 
 * Traduz mnemônicos da linguagem assembly para códigos binários da arquitetura Z0.
 */
public class Code {

    /**
     * Retorna o código binário do(s) registrador(es) que vão receber o valor da instrução.
     * @param  mnemnonic vetor de mnemônicos "instrução" a ser analisada.
     * @return Opcode (String de 3 bits) com código em linguagem de máquina para a instrução.
     */
    public static String dest(String[] mnemnonic) throws InvalidDestException {

        if ( mnemnonic.length == 0 || mnemnonic[0].equals("")) {
            Error.error("Instrução invalida");
            throw new InvalidDestException();
        }

        char[] dests = "000".toCharArray();

        if (mnemnonic[0].startsWith("mov")) {
            if(mnemnonic.length==3) dests[checkDest(mnemnonic[2])]='1';
            if(mnemnonic.length==4) dests[checkDest(mnemnonic[3])]='1';
            if(mnemnonic.length==5) dests[checkDest(mnemnonic[4])]='1';
            if(mnemnonic.length > 5) Error.error("Mais de três destinos em MOV");
        } else if (mnemnonic[0].startsWith("add")) {
            if(mnemnonic.length==4) dests[checkDest(mnemnonic[3])]='1';
            if(mnemnonic.length==5) dests[checkDest(mnemnonic[4])]='1';
            if(mnemnonic.length==6) dests[checkDest(mnemnonic[5])]='1';
            if(mnemnonic.length > 6) Error.error("Mais de três destinos em ADD");
        } else if (mnemnonic[0].startsWith("sub")) {
            if(mnemnonic.length==4) dests[checkDest(mnemnonic[3])]='1';
            if(mnemnonic.length==5) dests[checkDest(mnemnonic[4])]='1';
            if(mnemnonic.length==6) dests[checkDest(mnemnonic[5])]='1';
            if(mnemnonic.length > 6) Error.error("Mais de três destinos em SUB");
        } else if (mnemnonic[0].startsWith("rsub")) {
            if(mnemnonic.length==4) dests[checkDest(mnemnonic[3])]='1';
            if(mnemnonic.length==5) dests[checkDest(mnemnonic[4])]='1';
            if(mnemnonic.length==6) dests[checkDest(mnemnonic[5])]='1';
            if(mnemnonic.length > 6) Error.error("Mais de três destinos em RSUB");
        } else if (mnemnonic[0].startsWith("inc")) {
            if(mnemnonic.length==2) dests[checkDest(mnemnonic[1])]='1';
            if(mnemnonic.length > 2) Error.error("Mais de um destino em INC");
        } else if (mnemnonic[0].startsWith("dec")) {
            if(mnemnonic.length==2) dests[checkDest(mnemnonic[1])]='1';
            if(mnemnonic.length > 2) Error.error("Mais de um destino em DEC");
        } else if (mnemnonic[0].startsWith("not")) {
            if(mnemnonic.length==2) dests[checkDest(mnemnonic[1])]='1';
            if(mnemnonic.length > 2) Error.error("Mais de um destino em NOT");
        } else if (mnemnonic[0].startsWith("neg")) {
            if(mnemnonic.length==2) dests[checkDest(mnemnonic[1])]='1';
            if(mnemnonic.length > 2) Error.error("Mais de um destino em NEG");
        } else if (mnemnonic[0].startsWith("and")) {
            if(mnemnonic.length==4) dests[checkDest(mnemnonic[3])]='1';
            if(mnemnonic.length==5) dests[checkDest(mnemnonic[4])]='1';
            if(mnemnonic.length==6) dests[checkDest(mnemnonic[5])]='1';
            if(mnemnonic.length > 6) Error.error("Mais de três destinos em AND");
        } else if (mnemnonic[0].startsWith("or")) {
            if(mnemnonic.length==4) dests[checkDest(mnemnonic[3])]='1';
            if(mnemnonic.length==5) dests[checkDest(mnemnonic[4])]='1';
            if(mnemnonic.length==6) dests[checkDest(mnemnonic[5])]='1';
            if(mnemnonic.length > 6) Error.error("Mais de três destinos em OR");
        } else {
            // instrução não possui destino
        }

        String value_dest = String.valueOf(dests);

        return value_dest;
    }

    /**
     * Retorna o código binário do mnemônico para realizar uma operação de cálculo.
     * @param  mnemnonic vetor de mnemônicos "instrução" a ser analisada.
     * @return Opcode (String de 7 bits) com código em linguagem de máquina para a instrução.
     */
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
            case "%A&%D": c = "000000"; break;
            case "%D|%A": c = "010101"; break;
            case "%A|%D": c = "010101"; break;
            default: throw new InvalidCompException();
        }

        return a + c;

    }

    /**
     * Retorna o código binário do mnemônico para realizar uma operação de jump (salto).
     * @param  mnemnonic vetor de mnemônicos "instrução" a ser analisada.
     * @return Opcode (String de 3 bits) com código em linguagem de máquina para a instrução.
     */
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

    /**
     * Retorna o código binário de um valor decimal armazenado numa String.
     * @param  symbol valor numérico decimal armazenado em uma String.
     * @return Valor em binário (String de 15 bits) representado com 0s e 1s.
     */
    public static String toBinary(String symbol) {
        int value = Integer.valueOf(symbol);
        String binary = Integer.toBinaryString(value);
        return String.format("%1$15s", binary).replace(" ", "0");
    }

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

}
