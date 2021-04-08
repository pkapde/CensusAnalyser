package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        String extension = findExtenstionTypeOfFile(csvFilePath);
        boolean delimeterStatus = findDelemeterOfCSVFile(csvFilePath);
        System.out.println("Delimeter Status: "+delimeterStatus);
        int namOfEateries = 0;
        try {
            if (delimeterStatus == true) {
                if (extension.equalsIgnoreCase("csv")) {
                    Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
                    CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
                    csvToBeanBuilder.withType(IndiaCensusCSV.class);
                    csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
                    CsvToBean<IndiaCensusCSV> csvToBean = csvToBeanBuilder.build();
                    Iterator<IndiaCensusCSV> censusCSVIterator = csvToBean.iterator();
                    while (censusCSVIterator.hasNext()) {
                        namOfEateries++;
                        IndiaCensusCSV censusData = censusCSVIterator.next();
                    }
                } else {
                    throw new CensusAnalyserException("Invalid Extension type of file", CensusAnalyserException.ExceptionType.INVALID_DELIMETER);
                }
            }
        }
        catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INVALID_DELIMETER);
        }
        return Integer.parseInt(extension);
    }
    public Boolean findDelemeterOfCSVFile(String filePath) {
        String line = "";
        String cvsSplitBy = ",";
        boolean delimeterStatus=false;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                if (line.contains(",")) {
                    cvsSplitBy = ",";
                    delimeterStatus = true;
                } else if (line.contains(";")) {
                    cvsSplitBy = ";";
                    delimeterStatus = true;
                } else {
                    System.out.println("Wrong separator!");
                    delimeterStatus = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return delimeterStatus;
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