package censusanalyser;

import com.google.gson.Gson;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class CensusAnalyser {
    List<IndiaCensusDAO> censusList = null;
    List<IndiaCensusDAO> stateList = null;

    public CensusAnalyser() {
        this.censusList = new ArrayList<IndiaCensusDAO>();
        this.stateList = new ArrayList<IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (csvFileIterator.hasNext()) {
                this.censusList.add(new IndiaCensusDAO(csvFileIterator.next()));
            }
            return censusList.size();
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
            while (csvFileIterator.hasNext()) {
                this.stateList.add(new IndiaCensusDAO(csvFileIterator.next()));
            }
            return stateList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_FILE_EXTENSION);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.INVALID_DELIMETER_OR_HEADER);
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

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEnteries;

    }

    public String SortStateCensus() throws CensusAnalyserException {
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        censusList = this.censusList.stream().sorted(censusComparator).collect(Collectors.toList());
        String sortedState = new Gson().toJson(censusList);
        return sortedState;
    }

    public String sortStateCode(){
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.stateCode);
        stateList = this.stateList.stream().sorted(censusComparator).collect(Collectors.toList());
        String sortedStateCode = new Gson().toJson(stateList);
        return sortedStateCode;
    }

    public String sortByPopulation(){
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        censusList = this.censusList.stream().sorted(censusComparator.reversed()).collect(Collectors.toList());
        String sortedPopulation = new Gson().toJson(censusList);
        return sortedPopulation;
    }

    public String sortByDensity(){
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        censusList = this.censusList.stream().sorted(censusComparator.reversed()).collect(Collectors.toList());
        String sortedDensity = new Gson().toJson(censusList);
        return sortedDensity;
    }

    public String sortbyarea(){
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        censusList = this.censusList.stream().sorted(censusComparator.reversed()).collect(Collectors.toList());
        String sortedDensityArea = new Gson().toJson(censusList);
        return sortedDensityArea;
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
