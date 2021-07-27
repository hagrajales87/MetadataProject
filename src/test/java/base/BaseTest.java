package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.Parameters;
import pages.RedirectToPage;


public class BaseTest {


    private WebDriver driver;
    private static String locationExcelFile;
    private static String environmentSelected;
    protected RedirectToPage redirectPage;

    @BeforeClass
    @Parameters({"locationFile","environment"})
    public void setUp(String locationFile, String environment){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        //ChromeOptions options = new ChromeOptions();
        //options.setHeadless(true); //Execute without open the browser

        //driver = new ChromeDriver(options);
        driver = new ChromeDriver();
        //driver.manage().window().maximize();

        //options.addArguments("headless");
        locationExcelFile = locationFile;
        environmentSelected = environment;

    }

   /** @BeforeMethod()
    @Parameters("locationFile")
    public  void readExcelFile(String locationFile){
        System.out.println("This is the location File: " + locationFile);
        locationExcelFile = locationFile;
    }
    **/
    @AfterClass
    public void tearDown(){
        driver.quit();
    }



    public RedirectToPage GoToThePage(String Url){
        //tearDown();
        //setUp();
        driver.get(Url);
        return new RedirectToPage(driver);
    }

    public static String getLocationFile(){
        return locationExcelFile;
    }

    public static String getEnvironmentSelected(){
        return environmentSelected;
    }

    public static int getCellEnvironment(){
        if(environmentSelected.equalsIgnoreCase("QA")){
            return 14;
        }
        else if((environmentSelected.equalsIgnoreCase("UAT"))){
            return 15;
        }
        else if((environmentSelected.equalsIgnoreCase("PROD"))) {
            return 16;
        }
        return 17;
    }

}
