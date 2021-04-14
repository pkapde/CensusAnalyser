package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_FILE_EXTENSION = "./src/main/resources/IndiaStateCensusData.txt";
    private static final String WRONG_DELIMITER_CSV_FILE_PATH = "src/test/resources/IndiaCensusWrong_delimeter.csv";
    private static final String WRONG_HEADER_CSV_FILE_PATH = "src/test/resources/IndiaCensusWrongHeader.csv";
    private static final String STATE_CODE_CSV_PATH = "src/test/resources/IndiaStateCode.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords()  {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_EXTENSION,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_FILE_EXTENSION);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_EXTENSION,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WithWrongDelimeter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_DELIMITER_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_DELIMETER_OR_HEADER,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WithWrongHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_HEADER_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            assertEquals(CensusAnalyserException.ExceptionType.INVALID_DELIMETER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenStateCodeData_ReturnExactRecordCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = (int) censusAnalyser.loadIndianStateCode(STATE_CODE_CSV_PATH);
            Assert.assertEquals(37, numOfRecords);
        }catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenStateCodeData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_EXTENSION,e.type);
        }
    }

    @Test
    public void givenStateCodeData_WithWrongFileType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_FILE_EXTENSION);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_EXTENSION,e.type);
        }
    }

    @Test
    public void givenStateCodeData_WithWrongDelimeter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_DELIMITER_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_DELIMETER_OR_HEADER,e.type);
        }
    }

    @Test
    public void givenStateCodeData_WithWrongHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_HEADER_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            assertEquals(CensusAnalyserException.ExceptionType.INVALID_DELIMETER_OR_HEADER, e.type);
        }
    }

    @Test
    public void givenIndianCensusCommonCSVFileReturnsCorrectRecords() throws CSVBuilderException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusAndStateCodeInCommonsCSV(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException_WhenCommonCSVUsed() throws CSVBuilderException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusAndStateCodeInCommonsCSV(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_EXTENSION, e.type);
        }
    }

    @Test
    public void givenIndianStateCommonCSV_ShouldReturnExactCount() throws CensusAnalyserException, CSVBuilderException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int stateCode = censusAnalyser.loadIndiaCensusAndStateCodeInCommonsCSV(STATE_CODE_CSV_PATH);
            Assert.assertEquals(37, stateCode);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianStateCSV_WithWrongFile_ShouldThrowException_WhenCommonCSVUsed() throws CSVBuilderException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusAndStateCodeInCommonsCSV(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INVALID_FILE_EXTENSION, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.SortStateCensus();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedOnStateCode_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndianStateCode(STATE_CODE_CSV_PATH);
            String sortedCensusData = censusAnalyser.sortStateCode();
            StateCodeCSV[] stateCodeCSV = new Gson().fromJson(sortedCensusData, StateCodeCSV[].class);
            Assert.assertEquals("AD", stateCodeCSV[0].stateCode);
        } catch (CensusAnalyserException e) {
        }
    }
}
