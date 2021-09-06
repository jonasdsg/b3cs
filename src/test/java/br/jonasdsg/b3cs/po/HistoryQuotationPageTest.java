package br.jonasdsg.b3cs.po;

import static br.jonasdsg.b3cs.shared.SharedConfiguration.getDriver;
import static br.jonasdsg.b3cs.shared.SharedConfiguration.sleep;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

public class HistoryQuotationPageTest {
    private static HistoryQuotationPage history;

    @BeforeAll
    public static void beforeAll() {
        history = new HistoryQuotationPage(getDriver());
    }

    @BeforeEach
    public void beforeEach() {
        history.open();
    }

    @AfterEach
    public void afterEach() {
        history.close();
    }

    @Test
    public void goToPageHistoryTest() {
        assertDoesNotThrow(() -> history.goToPageHistory().click());
    }

    @Test
    public void getYearsTest() {
        assertNotNull(history.getSelectOfYears());
    }

    @Test
    public void getMonthsUrisTest() {
        assertDoesNotThrow(() -> {
            assertNotNull(history.getMonthsUris());
        });
    }

    @Test
    public void getMonthsUrisTestUris() {
        history.getMonthsUris().forEach(m -> {
            WebDriver tmpDriver = getDriver();
            tmpDriver.get(m);
            sleep(2);
            tmpDriver.close();
        });
    }

    @Test
    public void getTablesTest() {
        assertDoesNotThrow(() -> {
            history.getTables();
        });
    }

}
