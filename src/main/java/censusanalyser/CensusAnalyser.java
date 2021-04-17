package censusanalyser;

import com.google.gson.Gson;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class CensusAnalyser {
    HashMap<String, IndiaCensusDAO> map = new HashMap<String, IndiaCensusDAO>();
    Map<String, IndiaCensusDAO> usStateMap = new HashMap<>();
    List<IndiaCensusDAO> censusList = null;
    List<IndiaCensusDAO> stateList = null;
    List<IndiaCensusDAO> usList = null;

    public CensusAnalyser() {
        this.censusList = new ArrayList<IndiaCensusDAO>();
        this.stateList = new ArrayList<IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath)  {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> censusCSVIterable = () -> csvFileIterator;
            StreamSupport.stream(censusCSVIterable.spliterator(), false)
                    .forEach(csvState -> map.put(csvState.state, new IndiaCensusDAO(csvState)));
            return this.map.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_EXTENSION);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INVALID_DELIMETER_OR_HEADER);
        }
    }

    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<StateCodeCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, StateCodeCSV.class);
            Iterable<StateCodeCSV> censusCSVIterable = () -> csvFileIterator;
            StreamSupport.stream(censusCSVIterable.spliterator(), false)
                    .forEach(csvStateCode -> map.put(csvStateCode.stateCode, new IndiaCensusDAO(csvStateCode)));

            return this.map.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_EXTENSION);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INVALID_DELIMETER_OR_HEADER);
        }
    }

    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<USCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, USCensusCSV.class);
            Iterable<USCensusCSV> censusCSVIterable = () -> csvFileIterator;
            StreamSupport.stream(censusCSVIterable.spliterator(), false)
                    .forEach(csvState -> this.map.put(csvState.state, new IndiaCensusDAO(csvState)));
            return this.map.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_EXTENSION);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public int loadIndiaCensusAndStateCodeInCommonsCSV(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            Iterator<CSVRecord> csvRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
                    .parse(reader).iterator();
            return this.getCount(csvRecords);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_EXTENSION);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    public String getPopulationWiseSortedUSCensusData() throws CensusAnalyserException {
        if (map == null || map.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        String sortedStateCensusJson = new Gson().toJson(usList);
        return sortedStateCensusJson;
    }

    public String getDensityWiseSortedUSCensusData() throws CensusAnalyserException {
        if (map == null || map.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        String sortedStateCensusJson = new Gson().toJson(usList);
        return sortedStateCensusJson;
    }

    public String getAreaWiseSortedUSCensusData() throws CensusAnalyserException {
        if (map == null || map.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        String sortedStateCensusJson = new Gson().toJson(usList);
        return sortedStateCensusJson;
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEnteries;

    }

    public String SortStateCensus() throws CensusAnalyserException {
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        String sortedState = new Gson().toJson(censusList);
        return sortedState;
    }

    public String sortStateCode(){
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
        String sortedStateCode = new Gson().toJson(stateList);
        return sortedStateCode;
    }

    public String sortByPopulation(){
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        String sortedPopulation = new Gson().toJson(censusList);
        return sortedPopulation;
    }

    public String sortByDensity(){
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        String sortedDensity = new Gson().toJson(censusList);
        return sortedDensity;
    }

    public String sortbyarea(){
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        String sortedDensityArea = new Gson().toJson(censusList);
        return sortedDensityArea;
    }

    public String getSortedJsonString(Comparator<IndiaCensusDAO> censusComparator) {
        ArrayList sortedList = this.map.values().stream().sorted(censusComparator).collect(Collectors.toCollection(ArrayList::new));
        String sortedState = new Gson().toJson(sortedList);
        System.out.println(sortedList);
        return sortedState;
    }

    /*private <E extends StateCodeCSV> void sort(List<E> censusCSVList, Comparator<E> censusComparator) {
        for (int i = 0; i < censusCSVList.size() - 1; i++) {
            for (int j = 0; j < censusCSVList.size() - 1 - i; j++) {
                E censusCsv = censusCSVList.get(j);
                E StateCsv = censusCSVList.get(j + 1);
                if (censusComparator.compare(censusCsv, StateCsv) > 0) {
                    censusCSVList.set(j, StateCsv);
                    censusCSVList.set(j + 1, censusCsv);
                }
            }
        }
    }*/
}
