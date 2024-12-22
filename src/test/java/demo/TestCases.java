package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in
     * instructions
     */

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);
        driver.get("http://www.flipkart.com/");
        driver.manage().window().maximize();

    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }

    @Test(description = "Search Product by Popularity")
    public void testCase01() {
        System.out.println("Start TestCase001");

        Wrappers wrapper = new Wrappers(driver);

        
        

        By searchproduct = By.xpath("//input[@name='q']");
        By click = By.xpath("//button[@type='submit']");
        wrapper.searchProduct(searchproduct, click, "Washing Machine");

        By filter = By.xpath("//div[contains(text(),'Popularity')]");
        wrapper.sortingpopulairty(filter);

        By ratings = By.xpath("//div[@class='XQDdHH']");
        wrapper.ratingcalculator(ratings);

        System.out.println("End TestCase001");

    }

    @Test(description = "Search IPhone Product")
    public void testCase02() {
        System.out.println("Start TestCase002");

        Wrappers wrapper = new Wrappers(driver);
        By searchproduct = By.xpath("//input[@name='q']");
        By click = By.xpath("//button[@type='submit']");
        wrapper.searchProduct(searchproduct, click, "iPhone");

        int discountlimit = 17;
        By phonetitle = By.xpath("//div[@class='KzDlHZ']");
        By discounttitle = By.xpath("//div[@class='UkUFwK']");
        wrapper.printDiscountedItems(phonetitle, discounttitle, discountlimit);
        System.out.println("End TestCase002");

    }

    @Test(description = "Search Coffee Mug")
    public void testCase03() {
        System.out.println("Start TestCase003");

        Wrappers wrapper = new Wrappers(driver);
        By searchproduct = By.xpath("//input[@name='q']");
        By click = By.xpath("//button[@type='submit']");
        wrapper.searchProduct(searchproduct, click, "Coffee Mug"); 

        By ratingfilter = By.xpath("(//div[@class='XqNaEv'])[1]");
        wrapper.applyfilter(ratingfilter);

        

        By title = By.xpath("//a[@class='wjcEIp']");
        By review = By.xpath("//span[@class='Wphh3N']");
        
        By image = By.xpath("//img[@loading='eager']");
        

        wrapper.extractAndPrintDetails(title, review, image);
        
        System.out.println("End TestCase003");
    }

}