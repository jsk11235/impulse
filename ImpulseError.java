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
        String errorString = "Impulse ran into an error while executing your program.\n";
        if (this.errorType != null) {
            errorString += "\t" + this.errorType;
        }
        if (this.message != null) {
            errorString += ": " + this.message;
        }
        if (this.fileName != null) {
            errorString += "\n\t(" + this.fileName.split("/")[this.fileName.split("/").length - 1] + ".ipl" + (this.lineNumber != -1 ? ":" + this.lineNumber : "") /* + (this.columnNumber != -1 ? ":" + this.columnNumber : "") */ + ")";
        }
        System.out.println(errorString);
        System.exit(this.errorCode);
    }
}