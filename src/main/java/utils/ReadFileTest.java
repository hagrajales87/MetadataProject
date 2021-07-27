package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import java.io.*;


public class ReadFileTest{

    private static final int StartRowDataExcelFile = 1;
    private static final int URLColumn = 0;
    private static final int RecommendedH1TitleColumn = 3;
    private static final int RecommendedH2SubTitleColumn = 4;
    private static final int RecommendedMetaTitleColumn = 1;
    private static final int RecommendedMetaDescriptionColumn = 2;
    private static final int Results = 14;
    private XSSFSheet sheet;
    private XSSFWorkbook workbook;

    FileOutputStream fileOut;

    public ReadFileTest() throws FileNotFoundException {
    }

    public int getURLColumn() {
        return URLColumn;
    }

    public int getStartRowDataExcelFile() {
        return StartRowDataExcelFile;
    }

    public int getRecommendedH1TitleColumn() {
        return RecommendedH1TitleColumn;
    }

    public int getRecommendedH2SubTitleColumn() {
        return RecommendedH2SubTitleColumn;
    }
    public int getRecommendedMetaTitleColumn(){
        return RecommendedMetaTitleColumn;
    }

    public int getRecommendedMetaDescriptionColumn() {
        return RecommendedMetaDescriptionColumn;
    }
    //@DataProvider
    public XSSFSheet ReadExcelFiles(String locationFile) throws Exception, IOException {
        File file = new File(locationFile);
        file.getName();
        workbook = (XSSFWorkbook) WorkbookFactory.create(file);
        sheet = workbook.getSheetAt(1);
        fileOut = new FileOutputStream(file.getName());
        System.out.println(file.getName());
        return sheet;
    }

    /**
     * This method return a String object reading all the data of the excel file using the DataProvider.
     * @return
     */

    public static   Object [] [] ReadExcelFileAndReturnStringObject(String locationFile) throws Exception, IOException{
        File file = new File(locationFile);
        XSSFWorkbook workbook1 = (XSSFWorkbook) WorkbookFactory.create(file);
        XSSFSheet sheet1 = workbook1.getSheetAt(1);

        Object Data [] [] = new Object[sheet1.getPhysicalNumberOfRows()-1][RecommendedH2SubTitleColumn+1];
        for (int i = StartRowDataExcelFile ; i < sheet1.getPhysicalNumberOfRows() ; i++ ){
            Data[i-1][URLColumn] = sheet1.getRow(i).getCell(URLColumn).getStringCellValue();
            Data[i-1][RecommendedMetaTitleColumn] = sheet1.getRow(i).getCell(RecommendedMetaTitleColumn).getStringCellValue().trim();
            Data[i-1][RecommendedMetaDescriptionColumn] = sheet1.getRow(i).getCell(RecommendedMetaDescriptionColumn).getStringCellValue().trim();
            Data[i-1][RecommendedH1TitleColumn] = sheet1.getRow(i).getCell(RecommendedH1TitleColumn).getStringCellValue().trim();
            Data[i-1][RecommendedH2SubTitleColumn] = sheet1.getRow(i).getCell(RecommendedH2SubTitleColumn).getStringCellValue().trim();
        }

        return Data;

    }



    public int getRowCount(){
        return sheet.getPhysicalNumberOfRows();
    }



    public void insertResult(int goRow , int goCell, String result) throws IOException {
        //Get Row at index 'goRow'
        Row row = sheet.getRow(goRow);
        // Get the Cell at index 'goCell' from the above row
        Cell cell=row.getCell(goCell);
        if(cell==null){
            cell = row.createCell(goCell);
        }
        cell.setCellValue(result);
    }

    public void finishTest() throws IOException {
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }

}

