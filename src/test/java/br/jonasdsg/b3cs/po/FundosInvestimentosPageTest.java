package br.jonasdsg.b3cs.po;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.jonasdsg.b3cs.dto.FundAboutDTO;

public class FundosInvestimentosPageTest {
    private FundosInvestimentosPage mainPage;

    @BeforeAll
    public static void BeforeAll() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver");
    }

    @BeforeEach
    public void beforeEach() {
        mainPage = new FundosInvestimentosPage(getDriver());
        mainPage.open();
        sleep();
    }

    @AfterEach
    public void afterEach() {
        mainPage.close();
    }

    @Test
    public void goToFiiListadosPageAndSearchAFoundTest() {
        assertDoesNotThrow(() -> {
            mainPage.goToFiiListadosPage();
            mainPage.searchFor("BLCA").click();
        });
    }

    @Test
    public void goToPageFoundInformationTest() {
        assertDoesNotThrow(() -> {
            mainPage.goToPageFoundInformation("BLCA").click();
        });
    }

    @Test
    public void test(){
        assertDoesNotThrow(()->{
            FundAboutDTO about = mainPage.getFundsInformationsAbout("BLCA");
            assertNotNull(about.contact);
            assertNotNull(about.data);
            assertNotNull(about.historyURI);
        });
    }

    private WebDriver getDriver() {
        return new ChromeDriver();
    }

    private void sleep() {
        try { Thread.sleep(3000); } 
        catch (InterruptedException e) { throw new RuntimeException(e); }
    }
}
