//package elemulator;

public class Log {

	static boolean debug = false;

    public static void print(String message, boolean[] code) {

    	if(debug) {
	        System.out.print(message+" : ");
	        for (int i = 15; i>=0;i--) {
	            if (code[i]) System.out.print("1");
	            else System.out.print("0");
	        }
	        System.out.println("");
	    }
    }

    public static void print(String message, boolean[] code, String sng, boolean ng, String szr, boolean zr) {

    	if(debug) {
	        System.out.print(message+" : ");
	        for (int i = 15; i>=0;i--) {
	            if (code[i]) System.out.print("1");
	            else System.out.print("0");
	        }
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