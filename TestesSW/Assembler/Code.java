public class Code {

    // returns binary code (3 bits) of dest mnemnonic
    public static String dest(String mnemnonic) throws InvalidDestException {
        String d1 = "0";
        String d2 = "0";
        String d3 = "0";
        
        String[] array = mnemnonic.split(" ");
        for (String s: array) {
          if (s.equals("%A"))
              d1 = "1";
          if (s.equals("%D"))
              d2 = "1";
          if (s.equals("(%A)"))
              d3 = "1";
        }

        if ((d1+d2+d3).equals("000") && !mnemnonic.equals("")) {
            throw new InvalidDestException();
        }

        return d1 + d2 + d3;
    }

    // retorna um binario de 7 bits da instrucao
    public static String comp(String mnemnonic) throws InvalidCompException {
        String a = "0";
        if (mnemnonic.contains("(%A)")) {
            a = "1";
            mnemnonic = mnemnonic.replace("(%A)", "%A");
        }
        String c = "000000";
        switch (mnemnonic) {
            case "$0":   c = "101010"; break;
            case "$1":   c = "111111"; break;
            case "$-1":  c = "111010"; break;
            case "%D":   c = "001100"; break;
            case "%A":   c = "110000"; break;
            case "!%D":  c = "001101"; break;
            case "!%A":  c = "110001"; break;
            case "-%D":  c = "001111"; break;
            case "-%A":  c = "110011"; break;
            case "%D+1": c = "011111"; break;
            case "%A+1": c = "110111"; break;
            case "%D-1": c = "001110"; break;
            case "%A-1": c = "110010"; break;
            case "%D+%A": c = "000010"; break;
            case "%D-%A": c = "010011"; break;
            case "%A-%D": c = "000111"; break;
            case "%D&%A": c = "000000"; break;
            case "%D|%A": c = "010101"; break;
            default: throw new InvalidCompException();
        }

        return a + c;
    }

    // returns binary code (3 bits) of jump mnemnonic
    public static String jump(String mnemnonic) throws InvalidJumpException
    {
        switch (mnemnonic)
        {
            case "":    return "000";
            case "jg": return "001";
            case "je": return "010";
            case "jge": return "011";
            case "jl": return "100";
            case "jne": return "101";
            case "jle": return "110";
            case "jmp": return "111";
            default:    throw new InvalidJumpException();
        }
    }

    // converte um simbolo numerico para binario de 15 bits
    public static String toBinary(String symbol) {
        int value = Integer.valueOf(symbol);
        String binary = Integer.toBinaryString(value);

        return String.format("%1$15s", binary).replace(" ", "0");
    }
}
