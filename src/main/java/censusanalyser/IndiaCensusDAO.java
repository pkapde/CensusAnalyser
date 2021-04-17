package censusanalyser;

public class IndiaCensusDAO {

    public String stateName;
    public String stateCode;
    public  int densityPerSqKm;
    public  int population;
    public  int areaInSqKm;
    public String state;

    public IndiaCensusDAO(USCensusCSV csvState) {
    }

    @Override
    public String toString() {
        return "IndiaCensusCSVDAO{" +
                ", densityPerSqKm=" + densityPerSqKm +
                ", population=" + population +
                ", areaInSqKm=" + areaInSqKm +
                ", state='" + state + '\'' +
                ", statecode='" + stateCode + '\''+
                '}';
    }

    public IndiaCensusDAO(IndiaCensusCSV indiacensusCSV) {
        state = indiacensusCSV.state;
        areaInSqKm = indiacensusCSV.areaInSqKm;
        population = indiacensusCSV.population;
        densityPerSqKm = indiacensusCSV.densityPerSqKm;
    }

    public IndiaCensusDAO(StateCodeCSV stateCodeCSV) {
        stateCode = stateCodeCSV.stateCode;
        stateName = stateCodeCSV.stateName;
    }
}
