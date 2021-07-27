package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RedirectToPage {

    WebDriver driver;

    private By H1Title = By.cssSelector(".main h1");
    private By H2Title = By.cssSelector(".main h2");
    private By metaTitle = By.cssSelector("meta[property='og:title']");
    private By metaDescription = By.cssSelector("meta[name='description']");

    public RedirectToPage(WebDriver driver){
        this.driver = driver;
    }

    public String getTitle(){
        return driver.findElement(H1Title).getText();
    }

    public String getSubTitle(){
        try {
            return driver.findElement(H2Title).getText().trim();
        }catch (Exception e){
            e.printStackTrace();
            return "Sub title Locator does not exit on this page.";
        }
    }

    public String getMetaTitle() {
        return driver.findElement(metaTitle).getAttribute("content");
    }

    public String getMetaDescription() {
        return driver.findElement(metaDescription).getAttribute("content").toString();
    }
}
