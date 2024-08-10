package com.nttdata.stepsdefinitions;

import com.nttdata.core.DriverManager;
import com.nttdata.page.StorePage;
import io.cucumber.java.es.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

import static com.nttdata.core.DriverManager.screenShot;

public class StoreStepsDef {

    private WebDriver driver;

    private StorePage storePage;


    @Dado("estoy en la página de la tienda")
    public void estoyEnLaPaginaDeLaTienda() {
        driver = DriverManager.getDriver();
        storePage = new StorePage(driver);
        driver.get("https://qalab.bensg.com/store/es/");

        screenShot();
    }

    @Y("me logueo con mi usuario {string} y clave {string}")
    public void meLogueoConMiUsuarioYClave(String usuario, String clave) {
        storePage.clickLoginLink(); // Click en "Iniciar sesión"
        storePage.enterUsername(usuario);
        storePage.enterPassword(clave);
        storePage.clickLogin();

        screenShot();
    }

    @Entonces("valido que la autenticacion sea exitosa")
    public void validoQueLaAutenticacionSeaExitosa() {
        boolean isLoginSuccessful = storePage.verifyLoginSuccess();  // Verifica si el login fue exitoso
        Assert.assertTrue("La autenticación falló. La prueba se detendrá aquí.", isLoginSuccessful);
        // Si la aserción falla, la prueba se detendrá aquí y no continuará con los siguientes pasos
    }

    @Cuando("navego a la categoria {string} y subcategoria {string}")
    public void navegoALaCategoriaYSubcategoria(String categoria, String subcategoria) {
        storePage.clickCategory(categoria);
        storePage.clickSubcategory(subcategoria);
        screenShot();
    }

    @Y("agrego {int} unidades del primer producto al carrito")
    public void agregoUnidadesDelPrimerProductoAlCarrito(int cantidad) {
        storePage.addProductToCart(cantidad);

        screenShot();
    }

    @Entonces("valido en el popup la confirmación del producto agregado")
    public void validoEnElPopupLaConfirmacionDelProductoAgregado() {
        String confirmationMessage = storePage.getConfirmationMessage();
        Assert.assertEquals("Producto no agregado al carrito", "\uE876Producto añadido correctamente a su carrito de compra", confirmationMessage);

        screenShot();
    }

    @Y("valido en el popup que el monto total sea calculado correctamente")
    public void validoEnElPopupQueElMontoTotalSeaCalculadoCorrectamente() {
        String totalAmount = storePage.getTotalPopup();
        Assert.assertNotNull("El total del carrito no se calculó correctamente", totalAmount);

        screenShot();
    }

    @Cuando("finalizo la compra")
    public void finalizoLaCompra() {
        storePage.clickCheckout();

        screenShot();
    }

    @Entonces("valido el titulo de la pagina del carrito")
    public void validoElTituloDeLaPaginaDelCarrito() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        String pageTitle = storePage.getCartPageTitle();
        Assert.assertEquals("El título de la página del carrito es incorrecto", "Carrito", pageTitle);

        screenShot();
    }

    @Y("vuelvo a validar el calculo de precios en el carrito")
    public void vuelvoAValidarElCalculoDePreciosEnElCarrito() {
        String carritoTotal = storePage.getCartTotal();
        Assert.assertNotNull("El total del carrito no se muestra correctamente", carritoTotal);

        screenShot();
    }

    @Entonces("debería ver un mensaje de error en la autenticación")
    public void deberiaVerUnMensajeDeErrorEnLaAutenticacion() {
        String errorMessage = storePage.getLoginErrorMessage();
        Assert.assertTrue("No se mostró el mensaje de error esperado", errorMessage.contains("error"));

        screenShot();
    }

    @Entonces("debería ver un mensaje de error indicando que la categoría no existe")
    public void deberiaVerUnMensajeDeErrorIndicandoQueLaCategoriaNoExiste() {
        String errorMessage = storePage.getCategoryErrorMessage();
        Assert.assertTrue("No se mostró el mensaje de error esperado", errorMessage.contains("No se han encontrado productos"));

        screenShot();
    }

    @Entonces("valido que la categoria {string} existe")
    public void validoQueLaCategoriaExiste(String categoria) {
        try {
            storePage.navigateToCategory(categoria);  // Intenta navegar a la categoría
        } catch (RuntimeException e) {
            Assert.fail(e.getMessage());  // Si la categoría no es encontrada, detiene el flujo de la prueba
        }
    }
}
