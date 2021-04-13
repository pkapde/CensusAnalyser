package censusanalyser;

class CSVBuilderFactory {

    public static OpenCsvBuilder createCSVBuilder() {
        return new OpenCsvBuilder();
    }
}
