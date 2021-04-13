package censusanalyser;

public class CensusAnalyserException extends Exception {

    enum ExceptionType {
        INCORRECT_FILE,UNABLE_TO_PARSE,INVALID_FILE_EXTENSION,NO_DATA,INVALID_DELIMETER_OR_HEADER,NO_CENSUS_DATA;
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
