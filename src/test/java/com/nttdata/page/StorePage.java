package com.nttdata.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class StorePage {

    private WebDriver driver;

    // Locators
    private By loginLink = By.cssSelector("a[href='https://qalab.bensg.com/store/es/iniciar-sesion?back=https%3A%2F%2Fqalab.bensg.com%2Fstore%2Fes%2F']");
    private By usernameField = By.id("field-email");
    private By passwordField = By.id("field-password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By selectProduct = By.cssSelector("a[href='https://qalab.bensg.com/store/es/men/1-1-hummingbird-printed-t-shirt.html#/1-tamano-s/8-color-blanco']");
    private By selectQuantityProduct = By.cssSelector("button.bootstrap-touchspin-up");
    private By addToCartButton = By.cssSelector("button.add-to-cart");
    private By totalPopup = By.cssSelector("div.cart-content span.value");
    private By checkoutButton = By.cssSelector("a[href='//qalab.bensg.com/store/es/carrito?action=show']");
    private By cartTotal = By.cssSelector("span.value");
    private By loginErrorMessage = By.cssSelector("div.notice.error");
    private By categoryErrorMessage = By.cssSelector("div.no-products");

    // Constructor
    public StorePage(WebDriver driver) {
        this.driver = driver;
    }

    // Methods to interact with elements
    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }
    public void clickCategory(String category) {
        // Usar XPath para encontrar cualquier texto dentro del <a>, ignorando los nodos internos
        By categoryLocator = By.xpath("//a[contains(normalize-space(.),'" + category + "')]");
        driver.findElement(categoryLocator).click();
    }
    public void clickSubcategory(String subcategory) {
        driver.findElement(By.linkText(subcategory)).click();
    }

    public void addProductToCart(int quantity) {
        driver.findElement(selectProduct).click();
        for (int i = 0; i < quantity-1; i++) {
            driver.findElement(selectQuantityProduct).click();
        }
        driver.findElement(addToCartButton).click();
    }

    public String getConfirmationMessage() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebElement element = driver.findElement(By.cssSelector("h4#myModalLabel"));
        String text = element.getAttribute("textContent").trim();
        System.out.println("Texto capturado con textContent: " + text);
        return text;
        //return driver.findElement(confirmationMessageLocator).getText();
    }



    public String getTotalPopup() {
        return driver.findElement(totalPopup).getText();
    }

    // Método para forzar el cierre del modal
    public void forceCloseModal() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeScript("document.querySelector('.modal .close').click();");
        } catch (Exception e) {
            // Si no funciona, oculta el modal directamente
            js.executeScript("document.querySelector('#blockcart-modal').style.display='none';");
        }
    }

    // Método para forzar el clic en el enlace dentro del modal
    public void forceClickLinkInModal() {
        WebElement element = driver.findElement(checkoutButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // Método para interactuar con el carrito
    public void clickCheckout() {
        forceCloseModal();  // Intenta cerrar el modal o ocultarlo
        forceClickLinkInModal();  // Forzar el clic en el enlace del carrito
    }
/*
    public void clickCheckout() {
        clickCartLink();
        By closeModalButton = By.cssSelector(".modal .close");
        driver.findElement(closeModalButton).click();
        //driver.findElement(checkoutButton).click();
    }
*/
    public String getCartPageTitle() {
        return driver.getTitle();
    }

    public String getCartTotal() {
        return driver.findElement(cartTotal).getText();
    }

    public String getLoginErrorMessage() {
        return driver.findElement(loginErrorMessage).getText();
    }

    public String getCategoryErrorMessage() {
        return driver.findElement(categoryErrorMessage).getText();
    }
}
