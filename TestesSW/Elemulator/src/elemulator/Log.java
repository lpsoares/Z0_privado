/**
 * Curso: Elementos de Sistemas
 * Arquivo: Log.java
 * Created by lucas on 28/11/2016.
 * Update by Luciano on 04/02/2017
 */

package elemulator;

public class Log {

	static boolean debug = false;

    public static void print(String message, boolean[] code) {

    	if(debug) {
	        System.out.print(message+" : ");
	        for (int i = 15; i>=0;i--) {
	            if (code[i]) System.out.print("1");
	            else System.out.print("0");
	        }
	        System.out.print(" - ");
	        System.out.println(Converter.booleanToInt(code));
	    }
    }

    public static void print(String message, boolean[] code, String sng, boolean ng, String szr, boolean zr) {

    	if(debug) {
	        System.out.print(message+" : ");
	        for (int i = 15; i>=0;i--) {
	            if (code[i]) System.out.print("1");
	            else System.out.print("0");
	        }
	        System.out.print(" - ");
	        System.out.print(Converter.booleanToInt(code));
	        System.out.print("     "+sng+" : "+(ng?"1":"0"));
	        System.out.print("     "+szr+" : "+(zr?"1":"0"));
	        System.out.println("");
	    }
    }

    public static void print(String message) {

    	if(debug) {
	        System.out.print(message);
	    }
    }

}