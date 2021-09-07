package br.jonasdsg.b3cs.po;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.jonasdsg.b3cs.dto.YearDTO;
import br.jonasdsg.b3cs.dto.HistoryDTO;
import br.jonasdsg.b3cs.dto.MonthDTO;
import br.jonasdsg.b3cs.shared.SharedConfiguration;

public class HistoryQuotationPage {
    private static final String INNER_HTML = "innerHTML";
    private static final String TABLE_DAILY_PRICES = "//*[@id=\"tblResDiario\"]/tbody/tr[2]/td/table";
    private static final String ID_MERC_VISTA = "//*[@id=\"MercVista\"]";
    private static final String MERCADOS_BUTTOM = "//*[@id=\"RES_MERCADOS\"]";
    private static final String YEAR_SELECT_PATH = "//*[@id=\"cboAno\"]";
    private static final String HISTORY_PATH = "/html/body/table[2]/tbody/tr[3]/td/table[2]/tbody/tr[2]/td/table/tbody/tr[1]/td[1]/a";
    private WebDriver driver;

    public HistoryQuotationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(String uri) {
        driver.get(uri);
    }

    public void close() {
        driver.close();
    }

    protected WebDriver getDriver() {
        return driver;
    }

    public WebElement goToPageHistory() {
        return driver.findElement(By.xpath(HISTORY_PATH));
    }

    public List<WebElement> getSelectOfYears() {
        goToPageHistory().click();
        SharedConfiguration.sleep(2);
        return driver.findElement(By.xpath(YEAR_SELECT_PATH)).findElements(By.tagName("option"));
    }

    public List<String> getMonthsUris() {
        List<String> uris = new ArrayList<>();
        getSelectOfYears().forEach(year -> {
            year.click();
            driver.findElement(By.xpath("//*[@id=\"id" + year.getText() + "\"]")).findElements(By.tagName("a"))
                    .forEach(a -> uris.add(a.getAttribute("href")));
        });
        return uris;
    }

    protected List<WebElement> getMontsOf(WebElement year) {
        year.click();
        return year.findElement(By.xpath(String.format("//*[@id=\"id" + year.getText() + "\"]")))
                .findElements(By.tagName("a"));
    }

    public WebElement getTablePrices() {
        try {
            getDriver().findElement(By.xpath(MERCADOS_BUTTOM)).click();
            getDriver().findElement(By.xpath(ID_MERC_VISTA)).click();
            return getDriver().findElement(By.xpath(TABLE_DAILY_PRICES));

        } catch (Exception e) {
            System.out.println("botão não encontrado!");
            return null;
        }
    }

    public List<String> getTables() {
        List<String> tables = new ArrayList<>();
        getMonthsUris().forEach(m -> {
            getDriver().get(m);
            WebElement table = getTablePrices();
            if (table != null) {
                tables.add(table.getAttribute(INNER_HTML));
            }
        });

        return tables;
    }

    public HistoryDTO getHistoryDTO() {
        HistoryDTO history = new HistoryDTO();
        history.years = new ArrayList<>();
        SharedConfiguration.sleep(3);

        getSelectOfYears().forEach(year -> {
            YearDTO yearDTO = new YearDTO();
            yearDTO.table = new ArrayList<MonthDTO>();
            yearDTO.year = year.getText();
            year.click();
            getMontsOf(year).forEach(month -> {
                MonthDTO monthDTO = new MonthDTO();
                monthDTO.month = month.getText();
                HistoryQuotationPage ho = new HistoryQuotationPage(SharedConfiguration.getDriver());
                try {
                    ho.open(month.getAttribute("href"));
                    ho.getDriver().findElement(By.xpath("//*[@id=\"RES_MERCADOS\"]")).click();
                    ho.getDriver().findElement(By.xpath("//*[@id=\"MercVista\"]")).click();
                    monthDTO.table = ho.getDriver().findElement(By.xpath("//*[@id=\"tblResDiario\"]"))
                            .getAttribute(INNER_HTML);

                } catch (Exception e) {
                    System.out.println("botão mercados não encontrado");
                } finally {
                    yearDTO.table.add(monthDTO);
                    ho.close();
                    SharedConfiguration.sleep(1);
                }
            });
            history.years.add(yearDTO);
        });

        return history;
    }

}
