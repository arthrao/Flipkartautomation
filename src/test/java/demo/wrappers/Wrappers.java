package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

    private WebDriver driver;
    private WebDriverWait wait;

    public Wrappers(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    
    public void WrapperClass(String driverPath) {
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void launchWebsite(String url) {
        driver.get(url);
        driver.manage().window().maximize();
    }


    public void searchProduct(By searchlocator,By buttonlocator,String product){
        try {
            
        
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchlocator));
        Thread.sleep(500);
        searchInput.sendKeys(Keys.CONTROL + "a"); 
        searchInput.sendKeys(Keys.BACK_SPACE);
        //searchInput.clear();
        Thread.sleep(3000);
        searchInput.sendKeys(product);
        Thread.sleep(2000);
        WebElement search = wait.until(ExpectedConditions.visibilityOfElementLocated(buttonlocator));
        search.click();
    } catch (Exception e) {
        e.printStackTrace();
    }
        
    }

    public void sortingpopulairty(By locator){
        try {
            
       Thread.sleep(2000);
        WebElement filtertext = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        filtertext.click();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public void ratingcalculator(By locator){
        try {
            
        Thread.sleep(3000);
        List<WebElement> ratingElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        int count = 0;

        for (WebElement ratingElement : ratingElements) {
            String ratingText = ratingElement.getText();
            if (!ratingText.isEmpty()) {
                try {
                    double rating = Double.parseDouble(ratingText);
                    if (rating <= 4.0) {
                        count++;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid rating format: " + ratingText);
                }
            }
        }
        System.out.println("Count of items with rating <= 4 stars: " + count);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public void printDiscountedItems(By phonelocator, By discountLocator, int threshold) {
        try {
            
            Thread.sleep(3000);
        List<WebElement> titles = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(phonelocator));
        List<WebElement> discounts = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(discountLocator));
    
        System.out.println("Items with more than " + threshold + "% discount:");
    
        for (int i = 0; i < titles.size() && i < discounts.size(); i++) {
            String title = titles.get(i).getText();
            String discountText = discounts.get(i).getText().replaceAll("[^0-9]", "");
    
            try {
                int discount = Integer.parseInt(discountText);
                if (discount > threshold) {
                    System.out.println("Title: " + title + " | Discount: " + discount + "%");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid discount format for item: " + title);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    }

    public void applyfilter(By locator){
        try {
            WebElement ratingfilter = wait.until(ExpectedConditions.elementToBeClickable(locator));
            
            ratingfilter.click();
            System.out.println("Filter applied successfully.");

        } catch (Exception e) {
            System.err.println("Error while applying filter: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void extractAndPrintDetails(By titleLocator, By reviewLocator, By imageLocator) {
        try {
            Thread.sleep(3000); 

            
            List<WebElement> titleElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(titleLocator));
            List<WebElement> reviewElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(reviewLocator));
            List<WebElement> imageElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(imageLocator));

            List<Item> items = new ArrayList<>();

            for (int i = 0; i < titleElements.size(); i++) {
                try {
                    String title = titleElements.get(i).getText();
                    String reviews = reviewElements.get(i).getText();
                    String imageUrl = imageElements.get(i).getAttribute("src");

                    int reviewCount = parseReviews(reviews);

                    items.add(new Item(title, reviewCount, imageUrl));
                } catch (Exception e) {
                    System.out.println("Error extracting item details at index " + i + ": " + e.getMessage());
                }
            }

            
            List<Item> topItems = items.stream()
                    .sorted(Comparator.comparingInt(Item::getReviews).reversed())
                    .limit(5)
                    .collect(Collectors.toList());

            
            System.out.println("Top 5 Coffee Mug Items with Highest Reviews:");
            for (Item item : topItems) {
                System.out.println("Title: " + item.getTitle());
                System.out.println("Image URL: " + item.getImageUrl());
                System.out.println("Reviews: " + item.getReviews());
                
            }
            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("Error during extraction and printing: " + e.getMessage());
        }
    }

    
    private int parseReviews(String reviewsText) {
        try {
            return Integer.parseInt(reviewsText.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0; 
        }
    }

    
    private static class Item {
        private String title;
        private int reviews;
        private String imageUrl;

        public Item(String title, int reviews, String imageUrl) {
            this.title = title;
            this.reviews = reviews;
            this.imageUrl = imageUrl;
        }

        public String getTitle() {
            return title;
        }

        public int getReviews() {
            return reviews;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }

    

}


    

    



    

