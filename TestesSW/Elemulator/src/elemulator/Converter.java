/**
 * Curso: Elementos de Sistemas
 * Arquivo: Converter.java
 * Created by lucas on 28/11/2016.
 * Update by Luciano on 04/02/2017
 */

package elemulator;

public class Converter {

    public static int booleanToInt(boolean[] a) {
        String s = "";

        for (int i = a.length-1; i>=0; i--) {
            if (a[i] == false) s+='0';
            else if (a[i] == true) s+='1';
        }

        int decimal = Integer.parseInt(s, 2);

        return decimal;
    }

    public static boolean[] intToBoolean(int decimal, int bits) {
        boolean[] a = new boolean[bits];
        String s = Integer.toBinaryString(decimal);

        for (int j = 0; j<s.length();j++) {
            if (s.charAt(j)=='0') a[s.length()-1-j] = false;
            else if (s.charAt(j)=='1') a[s.length()-1-j] = true;
        }

        return a;
    }

    public static String booleanToString(boolean[] a) {
        String s = "";

        for (int i = a.length-1; i>=0; i--) {
            if (a[i] == false) s+='0';
            else if (a[i] == true) s+='1';
        }

        return s;
    }

    public static boolean[] stringToBoolean (String s, int bits) {
        boolean[] a = new boolean[bits];

        for (int j = 0; j<s.length();j++) {
            if (s.charAt(j)=='0') a[s.length()-1-j] = false;
            else if (s.charAt(j)=='1') a[s.length()-1-j] = true;
        }

        return a;
    }

}
