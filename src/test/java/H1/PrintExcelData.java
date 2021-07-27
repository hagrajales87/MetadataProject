package H1;



import base.BaseTest;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.RedirectToPage;
import utils.ReadFileTest;




public class PrintExcelData extends BaseTest {
    private static String result;
    private static boolean thereIsAnError;
    private int row = 1;
    XSSFSheet sheet;



    @DataProvider
    public static Object[][] ExcelInformation() throws Exception {
        Object[][] testObjArray =ReadFileTest.ReadExcelFileAndReturnStringObject(getLocationFile());
        return testObjArray ;

    }

    @Test(dataProvider = "ExcelInformation")
    public void PrintExcelTest(String URL, String RecommendedMetaTitle, String RecommendedMetaDescription, String RecommendedH1, String RecommendedH2) throws Exception {
        if(getEnvironmentSelected().equalsIgnoreCase("QA")) {
            URL = URL.substring(0, 8) + "qa-" + URL.substring(8, URL.length());
        }
        else if(getEnvironmentSelected().equalsIgnoreCase("UAT")){
            URL = URL.substring(0, 8) + "uat-" + URL.substring(8, URL.length());
        }
        System.out.println(URL);
        ReadFileTest readFileTest = new ReadFileTest();
        sheet = readFileTest.ReadExcelFiles(getLocationFile());
        result = "";
        var redirectToPage = GoToThePage(URL);
        System.out.println(URL);


        System.out.println(RecommendedMetaTitle);
        metaTitleValidations(RecommendedMetaTitle.trim(),redirectToPage);


        System.out.println(RecommendedMetaDescription);
        metaDescriptionValidations(RecommendedMetaDescription.trim(),redirectToPage);


        System.out.println(RecommendedH1);
        titleValidations(RecommendedH1.trim(), redirectToPage);

        System.out.println(RecommendedH2);
        subTitleValidations(RecommendedH2.trim(),redirectToPage);

        if (thereIsAnError==false){
            result = "Ok";
        }
       readFileTest.insertResult(row, getCellEnvironment() , result);
        System.out.println(result);
        row++;
        readFileTest.finishTest();
        thereIsAnError = false;
    }

    public static void titleValidations(String recommendedTitleValue, RedirectToPage redirectToPage){

        if(!recommendedTitleValue.equalsIgnoreCase("KEEP Existing")) {
            try{
                Assert.assertEquals(redirectToPage.getTitle().trim(), recommendedTitleValue);
            }catch (AssertionError ae){
                result += "\nError on Title:\n"+"Expect: " + recommendedTitleValue + "\n" + "Current: " + redirectToPage.getTitle();
                thereIsAnError = true;
            }

        }
    }
    public static void subTitleValidations(String recommendedSubTitleValue,RedirectToPage redirectToPage){
        try {
            if (!(recommendedSubTitleValue.equalsIgnoreCase("KEEP Existing") || recommendedSubTitleValue.contentEquals("N/A"))) {
                Assert.assertEquals(redirectToPage.getSubTitle().trim(), recommendedSubTitleValue);
                System.out.println("This is the current SubTitle: " + redirectToPage.getSubTitle());
            }
        }catch (AssertionError ae){
            result += "\nError on Subtitle:\n"+"Expect: " + recommendedSubTitleValue + "\n" + "Current: " + redirectToPage.getSubTitle();
            thereIsAnError = true;
        }

    }
    public static void metaTitleValidations(String recommendedMetaTitleValue, RedirectToPage redirectToPage){
        try {
            if (!(recommendedMetaTitleValue.equalsIgnoreCase("KEEP Existing") || recommendedMetaTitleValue.contentEquals("N/A"))) {
                Assert.assertEquals(redirectToPage.getMetaTitle().trim(), recommendedMetaTitleValue);
            }
        }catch (AssertionError ae){
            result += "\nError on Meta Title:\n"+"Expect: " + recommendedMetaTitleValue + "\n" + "Current: " + redirectToPage.getMetaTitle();
            thereIsAnError = true;
        }

    }
    public static void metaDescriptionValidations(String recommendedMetaDescription, RedirectToPage redirectToPage) {

        if (!(recommendedMetaDescription.equalsIgnoreCase("KEEP Existing") || recommendedMetaDescription.contentEquals("N/A")))  {
            try {
                System.out.println("This is the Meta Description:" + redirectToPage.getMetaDescription());
                Assert.assertEquals(redirectToPage.getMetaDescription().trim(),recommendedMetaDescription);
            }catch (AssertionError ae){
                ae.getStackTrace();
                result += "\nError on Meta Description:\n"+"Expect: " + recommendedMetaDescription + "\n" + "Current: " + redirectToPage.getMetaDescription();
                thereIsAnError = true;
            }
        }
    }
}
