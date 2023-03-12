import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ItemPage {
    public WebDriver driver;

    private final By addToCart = By.id("buyButton");
    private final By viewCart = By.cssSelector("[data-testid=\"cart-nav-link\"]");
    private final By viewCartCount = By.id("cartItemCountSpan");
    public ItemPage(WebDriver driver){
        this.driver = driver;
    }

    public void addItemToCart(){
        WebElement addToCartButton = driver.findElement(addToCart);
        addToCartButton.click();
    }

    public boolean isCartCountExpected(int expectedCount){
        //Give adding an item to cart some time to persist
        return new WebDriverWait(driver, Duration.ofSeconds(5)).
                until(ExpectedConditions.textToBe(viewCartCount,
                        Integer.toString(expectedCount)));
    }

    public CartPage goToCart(){
        WebElement goToCartButton = driver.findElement(viewCart);
        goToCartButton.click();
        return new CartPage(driver);
    }
}
