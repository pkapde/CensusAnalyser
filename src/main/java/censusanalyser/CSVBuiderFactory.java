package censusanalyser;

class CSVBuilderFactory {

    public static ICSVBuilder createCSVBuilder() {
        return new OpenCsvBuilder();
    }
}
