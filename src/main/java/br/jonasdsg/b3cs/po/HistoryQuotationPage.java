package br.jonasdsg.b3cs.po;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
            driver
            .findElement(By.xpath("//*[@id=\"id"+year.getText()+"\"]"))
            .findElements(By.tagName("a"))
                .forEach(a -> uris.add(a.getAttribute("href")));
        });
        return uris;
    }

    public WebElement getTablePrices() {
        try{
            getDriver().findElement(By.xpath(MERCADOS_BUTTOM)).click();
            getDriver().findElement(By.xpath(ID_MERC_VISTA)).click();
            return getDriver().findElement(By.xpath(TABLE_DAILY_PRICES));

        } catch(Exception e){
            System.out.println("botão não encontrado!");
            return null;
        }
    }

    public List<String> getTables() {
        List<String> tables = new ArrayList<>();
        getMonthsUris().forEach(m->{
            getDriver().get(m);
            WebElement table = getTablePrices();
            if(table != null){
                tables.add(table.getAttribute(INNER_HTML));
            }
        });

        return tables;
    }

}
