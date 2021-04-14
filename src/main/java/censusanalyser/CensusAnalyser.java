package censusanalyser;

import com.google.gson.Gson;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class CensusAnalyser {
    List<IndiaCensusCSV> censusCSVList = null;
    List<StateCodeCSV> stateCodeCSVList = null;

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return censusCSVList.size();
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
            Iterator<StateCodeCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, StateCodeCSV.class);
            return this.getCount(censusCSVIterator);
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
        Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
        censusCSVList = this.censusCSVList.stream().sorted(censusComparator).collect(Collectors.toList());
        String sortedState = new Gson().toJson(censusCSVList);
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

    public String sortStateCode(){
        Comparator<StateCodeCSV> censusComparator = Comparator.comparing(census -> census.stateCode);
        stateCodeCSVList = this.stateCodeCSVList.stream().sorted(censusComparator).collect(Collectors.toList());
        String sortedStateCode = new Gson().toJson(stateCodeCSVList);
        return sortedStateCode;
    }

}
