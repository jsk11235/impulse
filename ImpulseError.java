public class ImpulseError {
    private String message;
    private int lineNumber;
    private int columnNumber;
    private String fileName;
    private String errorType;
    private int errorCode;

    // Constructor
    public ImpulseError(String errorType, String message, int lineNumber, int columnNumber, String fileName,  int errorCode) {
        this.message = message;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.fileName = fileName;
        this.errorType = errorType;
        this.errorCode = errorCode;
    }

    public ImpulseError(String errorType, String message, int lineNumber, int columnNumber, String fileName) {
        this(errorType, message, lineNumber, columnNumber, fileName, 1);
    }

    public void exit() {
        String errorString1 = Colors.bold(Colors.red("Impulse ran into an error while executing your program.\n") + Colors.reset());
        String errorString2 = "";
        if (this.errorType != null) {
            errorString2 += "  " + this.errorType;
        }
        if (this.message != null) {
            errorString2 += ": " + this.message;
        }
        if (this.fileName != null) {
            errorString2 += "\n  (" + this.fileName.split("/")[this.fileName.split("/").length - 1]  + (this.lineNumber != -1 ? ":" + this.lineNumber : "") /* + (this.columnNumber != -1 ? ":" + this.columnNumber : "") */ + ")";
        }
        System.out.println(errorString1 + Colors.red(Colors.italic(errorString2)) + Colors.reset());
        System.exit(this.errorCode);
    }

    public void print() {
        // Does what exit() does, but prints without exiting.
        String errorString1 = Colors.bold(Colors.red("Impulse ran into an error while executing your program.\n") + Colors.reset());
        String errorString2 = "";
        if (this.errorType != null) {
            errorString2 += "  " + this.errorType;
        }
        if (this.message != null) {
            errorString2 += ": " + this.message;
        }
        if (this.fileName != null) {
            errorString2 += "\n  (" + this.fileName.split("/")[this.fileName.split("/").length - 1]  + (this.lineNumber != -1 ? ":" + this.lineNumber : "") /* + (this.columnNumber != -1 ? ":" + this.columnNumber : "") */ + ")";
        }
        System.out.println(errorString1 + Colors.red(Colors.italic(errorString2)) + Colors.reset());
    }
}