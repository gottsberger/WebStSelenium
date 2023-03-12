import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage {
    WebDriver driver;

    private final By emptyCartButton = By.xpath("//button[text()='Empty Cart']");
    private final By confirmButton = By.xpath("//button[text()='Empty']");
    private final By emptyCartPage = By.cssSelector("div[class= 'cartEmpty']");

    public CartPage(WebDriver driver){ this.driver = driver; }

    public void emptyCart(){
        WebElement emptyCart = driver.findElement(emptyCartButton);
        emptyCart.click();
    }

    public void clickConfirmButton(){
        WebElement confirmEmptyButton = driver.findElement(confirmButton);
        confirmEmptyButton.click();
    }

    public boolean isCartEmpty(){
        try{
            driver.findElement(emptyCartPage);
        }catch (NoSuchElementException e){
            return false;
        }
        return true;
    }
}
