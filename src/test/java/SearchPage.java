import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchPage {
    public WebDriver driver;

    private final By searchField = By.name("searchval");
    private final By searchButton = By.xpath("//button[text()='Search']");
    private final By searchResults = By.cssSelector("[data-testid=\"product-listing-container\"]");
    private final By item = By.cssSelector("[data-testid=\"productBoxContainer\"]");
    private final By itemDescription = By.cssSelector("[data-testid=\"itemDescription\"]");
    private final By nextPageIcon = By.cssSelector("use[*|href$='right-open']");

    public void searchForItem(String itemToSearch){
        driver.findElement(searchField).sendKeys(itemToSearch);
        driver.findElement(searchButton).click();
    }

    public List<String> returnSearchDescriptions(){
        WebElement result = driver.findElement(searchResults);
        List<WebElement> resultDescriptions = result.findElements(itemDescription);

        List<String> descriptions = new ArrayList<>();
        for (WebElement e:resultDescriptions
             ) {
            descriptions.add(e.getText());
        }
        return descriptions;
    }

    public WebElement returnLastElementOfLastPage(){
        boolean isLastPage = false;
        WebElement lastItem = null;

        while(!isLastPage) {
            if (this.clickNextSearchPage()) {
                //keep clicking next page
            } else {
                isLastPage = true;
                lastItem = this.returnLastElement();
            }
        }
        return lastItem;
    }

    public WebElement returnLastElement(){
        WebElement result = driver.findElement(searchResults);
        List<WebElement> resultItems = result.findElements(item);

        return resultItems.get(resultItems.size()-1);
    }

    public boolean clickNextSearchPage(){
        if(this.isElementPresent(nextPageIcon)){
            driver.findElement(nextPageIcon).click();
            return true;
        }
        return false;
    }

    public ItemPage selectItem(WebElement item){
        item.click();
        return new ItemPage(driver);
    }

    public boolean isElementPresent(By toFind){
        try{
            driver.findElement(toFind);
        }catch (NoSuchElementException e){
            return false;
        }
        return true;
    }

    public SearchPage(WebDriver driver){
        this.driver = driver;
    }
}
