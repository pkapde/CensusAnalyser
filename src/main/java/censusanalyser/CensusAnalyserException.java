package censusanalyser;

public class CensusAnalyserException extends RuntimeException{

    public CensusAnalyserException(String message, String name) {
    }

    enum ExceptionType {
        UNABLE_TO_PARSE, INVALID_FILE_EXTENSION, INVALID_DELIMETER_OR_HEADER,FILE_TYPE_OR_DELIMITER_OR_HEADER_PROBLEM;
    }

    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
