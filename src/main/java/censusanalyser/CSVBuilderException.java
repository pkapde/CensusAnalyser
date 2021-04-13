package censusanalyser;

public class CSVBuilderException extends Exception{
    public enum ExceptionType {
        INCORRECT_FILE,UNABLE_TO_PARSE,INVALID_FILE_EXTENSION,INVALID_DELIMETER_OR_HEADER;
    }

    public ExceptionType type;

    public CSVBuilderException(ExceptionType type) {
        this.type = type;
    }

    public CSVBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CSVBuilderException(String message, Throwable cause, ExceptionType type) {
        super(message, cause);
        this.type = type;
    }

    public CSVBuilderException(Throwable cause, ExceptionType type) {
        super(cause);
        this.type = type;
    }

    public CSVBuilderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionType type) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.type = type;
    }
}
