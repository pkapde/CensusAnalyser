package censusanalyser;

import com.opencsv.bean.CsvBindByName;

class StateCodeCSV {
    @CsvBindByName(column = "State Name", required = true)
    public String stateName;

    @CsvBindByName(column = "TIN", required = true)
    private String TIN;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;


    @Override
    public String toString() {
        return "StateCodeCSV{" +
                "stateName='" + stateName + '\'' +
                ", TIN='" + TIN + '\'' +
                ", stateCode='" + stateCode + '\'' +
                '}';
    }
}