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
        if (this.lineNumber == -1 || this.columnNumber == -1) {
            System.out.println(
                    "Impulse had an error: " +
                            "\n\t" + errorType + ": " + message +
                            "\n\t" + "in file " + fileName + ".ipl"
            );
            System.exit(errorCode);
        } else {
            System.out.println(
                    "Impulse had an error: " +
                            "\n\t" + errorType + ": " + message +
                            "\n\t" + "at line " + lineNumber + ":" + columnNumber +
                            "\n\t" + "in file " + fileName + ".ipl"
            );
            System.exit(errorCode);
        }
    }
}