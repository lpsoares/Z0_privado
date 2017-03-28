class Error {
    public static void error(String message, String fileName, int lineNum, String line) {
        System.err.println(fileName + ":" + lineNum + ": Erro: " + message);
        System.err.println("\t" + line);
        System.exit(1);
    }
    
    public static void error(String message, String fileName, int lineNum) {
        System.err.println(fileName + ":" + lineNum + ": Erro: " + message);
        System.exit(1);
    }
    
    public static void error(String message) {
        System.err.println("Erro: " + message);
        System.exit(1);
    }
}

// exceptions
class InvalidDestException extends Exception {}
class InvalidCompException extends Exception {}
class InvalidJumpException extends Exception {}
