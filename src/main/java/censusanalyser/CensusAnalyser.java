package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        String extension = findExtenstionTypeOfFile(csvFilePath);
        int namOfEateries = 0;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaCensusCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaCensusCSV> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvToBean.iterator();;
            while (censusCSVIterator.hasNext()) {
                namOfEateries++;
                IndiaCensusCSV censusData = censusCSVIterator.next();
                if (extension.equalsIgnoreCase("csv")) {
                    reader = Files.newBufferedReader(Paths.get(csvFilePath));
                    CsvToBeanBuilder<IndiaCensusCSV> CsvToBeanBuilder = new CsvToBeanBuilder<>(reader);
                    csvToBeanBuilder.withType(IndiaCensusCSV.class);
                    csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                    CsvToBean<IndiaCensusCSV> CsvToBean = csvToBeanBuilder.build();
                    //Iterator<IndiaCensusCSV> censusCSVIterator = csvToBean.iterator();

                    while (censusCSVIterator.hasNext()) {
                        namOfEateries++;
                        censusData = censusCSVIterator.next();
                    }
                }
                else {
                    throw new CensusAnalyserException("Invalid Extension type of file",CensusAnalyserException
                            .ExceptionType
                            .INVALID_FILE_EXTENSION);
                }

            }
            return namOfEateries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
    public String findExtenstionTypeOfFile(String pathValue) {
        int index = pathValue.lastIndexOf('.');
        String extension = null;
        if( index > 0) {
            extension = pathValue.substring(index + 1);
            System.out.println("File extension is " + extension);
        }
        return extension;
    }
}
