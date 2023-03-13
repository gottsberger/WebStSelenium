import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class SearchTest {
    WebDriver driver;

    @BeforeAll
    static void setupClass(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest(){
        driver = new ChromeDriver();
        //Added implicit wait, some pages such as the cart required a little extra time
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.webstaurantstore.com/");
    }

    @AfterEach
    void teardown(){
        driver.quit();
    }

    @Test
    @DisplayName("Search returns result descriptions correctly")
    void searchAndValidateDescriptions(){
        int numberOfPages = 0;
        boolean isLastPage = false;
        String itemToSearch = "stainless work table";
        String descriptionToCheck = "Table";

        //Arrange
        SearchPage searchPage = new SearchPage(driver);
        //Act
        searchPage.searchForItem(itemToSearch);

        //This loop checks descriptions for all returned items
        while(!isLastPage) {
            numberOfPages++;
            List<String> searchResults = searchPage.returnSearchDescriptions();

            //Assert
            for (int i = 0; i < searchResults.size(); i++) {
                assertThat(searchResults.get(i)).as("Description number: " + (i+1)  +
                        " on page: " + numberOfPages
                        + " doesn't match").contains(descriptionToCheck);
            }
            if(searchPage.clickNextSearchPage()){
                //continue
            }else {
                isLastPage = true;
            }
        }
    }

    @Test
    @DisplayName("Add the last item from cart and remove it")
    void addAndRemoveLastItemFromCart(){
        WebElement lastItem;
        int expectedItemsInCart = 1;

        //Arrange
        SearchPage searchPage = new SearchPage(driver);
        ItemPage itemPage;
        CartPage cartPage;

        //Act
        searchPage.searchForItem("stainless work table");
        lastItem = searchPage.returnLastElementOfLastPage();

        //Assert
        assertNotNull(lastItem,
                "We expected a item to be returned from search but did not get one.");

        itemPage = searchPage.selectItem(lastItem);
        itemPage.addItemToCart();

        assertTrue(itemPage.isCartCountExpected(expectedItemsInCart),
                "Item count in cart was not as expected.");

        cartPage = itemPage.goToCart();
        cartPage.emptyCart();
        cartPage.clickConfirmButton();
        assertTrue(cartPage.isCartEmpty(), "Cart is not empty and should be.");
    }
}
