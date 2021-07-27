package H1;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.RedirectToPage;
import utils.ReadFileTest;


import org.apache.poi.xssf.usermodel.XSSFSheet;


public class CheckH1Title extends BaseTest {

    private static String result;
    XSSFSheet sheet;
    private static boolean thereIsAnError;

    @Test
    @Parameters("locationFile")
    public void CheckH1Title(String locationFile) throws Exception {

        String URLValue;
        String recommendedMetaTitleValue;
        String recommendedMetaDescription;
        String recommendedTitleValue;
        String recommendedSubTitleValue;


        ReadFileTest readFileTest = new ReadFileTest();

        sheet = readFileTest.ReadExcelFiles(locationFile);



        for (int i = readFileTest.getStartRowDataExcelFile() ; i < readFileTest.getRowCount() ; i++ ){
            URLValue = sheet.getRow(i).getCell(readFileTest.getURLColumn()).getStringCellValue().trim();
            System.out.println(URLValue);
            thereIsAnError = false;
            result = "";


            var redirectToPage = GoToThePage(URLValue);

            // Title validations
            recommendedTitleValue = sheet.getRow(i).getCell(readFileTest.getRecommendedH1TitleColumn()).getStringCellValue().trim();

            titleValidations(recommendedTitleValue, redirectToPage);

            // SubTitle Validation
            recommendedSubTitleValue = sheet.getRow(i).getCell(readFileTest.getRecommendedH2SubTitleColumn()).getStringCellValue().trim();
            subTitleValidations(recommendedSubTitleValue,redirectToPage);

            // Meta Title Validation
            recommendedMetaTitleValue = sheet.getRow(i).getCell(readFileTest.getRecommendedMetaTitleColumn()).getStringCellValue().trim();
            metaTitleValidations(recommendedMetaTitleValue,redirectToPage);

            // Meta Description
            recommendedMetaDescription = sheet.getRow(i).getCell(readFileTest.getRecommendedMetaDescriptionColumn()).getStringCellValue().trim();


            metaDescriptionValidations(recommendedMetaDescription,redirectToPage);
            if (thereIsAnError==false){
                result = "Ok";
            }
            readFileTest.insertResult(i,14, result);
        }

        readFileTest.finishTest();
    }
    public static void titleValidations(String recommendedTitleValue, RedirectToPage redirectToPage){

        if(!recommendedTitleValue.contentEquals("KEEP Existing")) {
            try{
                Assert.assertEquals(redirectToPage.getTitle().trim(), recommendedTitleValue);
            }catch (AssertionError ae){
                result = "Error on Title:\n"+"Expect: " + recommendedTitleValue + "\n" + "Current: " + redirectToPage.getTitle();
                thereIsAnError = true;
            }

        }
    }
    public static void subTitleValidations(String recommendedSubTitleValue,RedirectToPage redirectToPage){
        try {
            if (!(recommendedSubTitleValue.equalsIgnoreCase("KEEP Existing") || recommendedSubTitleValue.contentEquals("N/A"))) {
                Assert.assertEquals(redirectToPage.getSubTitle(), recommendedSubTitleValue);
                System.out.println("This is the current SubTitle: " + redirectToPage.getSubTitle());
            }
        }catch (AssertionError ae){
        result += "Error on Subtitle:\n"+"Expect: " + recommendedSubTitleValue + "\n" + "Current: " + redirectToPage.getSubTitle();
            thereIsAnError = true;
    }

    }
    public static void metaTitleValidations(String recommendedMetaTitleValue, RedirectToPage redirectToPage){
      try {

          Assert.assertEquals(redirectToPage.getMetaTitle(), recommendedMetaTitleValue);

      }catch (AssertionError ae){
          result += "\nError on Meta Title:\n"+"Expect: " + recommendedMetaTitleValue + "\n" + "Current: " + redirectToPage.getMetaTitle();
          thereIsAnError = true;
      }

    }
    public static void metaDescriptionValidations(String recommendedMetaDescription, RedirectToPage redirectToPage) {

        if (!(recommendedMetaDescription.equalsIgnoreCase("KEEP Existing"))) {
        try {
            System.out.println("This is the Meta Description:" + redirectToPage.getMetaDescription());
            Assert.assertEquals(redirectToPage.getMetaDescription(),recommendedMetaDescription);
            }catch (AssertionError ae){
            ae.getStackTrace();
             result += "\nError on Meta Description:\n"+"Expect: " + recommendedMetaDescription + "\n" + "Current: " + redirectToPage.getMetaDescription();
            thereIsAnError = true;
            }
        }
    }
}

