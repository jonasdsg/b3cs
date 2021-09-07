package br.jonasdsg.b3cs.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class FundosInvestimentosPage {
    private static final String HISTORY_PAGE_URL = "//*[@id=\"divContainerIframeB3\"]/app-funds-about/div/div[1]/div[3]/div/div/div/p/a";
    private static final String INNER_HTML = "innerHTML";
    private static final String CARDS_FUNDS_INFORMATION = "//*[@id=\"divContainerIframeB3\"]/app-funds-about/div/div[1]";
    private static final String FIRST_CARD_PATH = "//*[@id=\"nav-bloco\"]/div/div/a/div/div";
    private static final String PATH_FIND_BUTTOM = "/html/body/app-root/app-funds-home/div/form/div[1]/div/div/div[1]/div/div[2]/button";
    private static final String ID_SEARCH_INPUT = "palavrachave";
    private static final String FII_LISTADOS_PAGE = "https://sistemaswebb3-listados.b3.com.br/fundsPage/7";
    private static final String POPUP_LGPD_COMFIRM_BUTTOM = "/html/body/div[3]/div[3]/div/div[1]/div/div[2]/div/button[2]";
    private static final String INITIAL_PAGE = "http://www.b3.com.br/pt_br/produtos-e-servicos/negociacao/renda-variavel/fundos-de-investimento-imobiliario-fii.htm";
    private WebDriver driver;
    
    public FundosInvestimentosPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() throws WebDriverException{
        driver.get(INITIAL_PAGE);
        comfirmLgpdPopUP();
    }

    public void close() {
        driver.close();
    }

    private void comfirmLgpdPopUP(){
        driver.findElement(By.xpath(POPUP_LGPD_COMFIRM_BUTTOM)).click();
    }

    protected WebDriver getDriver() {
        return driver;
    }

    public void goToFiiListadosPage() {
        driver.get(FII_LISTADOS_PAGE);
        sleep();
    }

    public WebElement searchFor(String code) {
        driver.findElement(By.id(ID_SEARCH_INPUT)).sendKeys(code);        
        sleep();
        return driver.findElement(By.xpath(PATH_FIND_BUTTOM));
    }

    public WebElement goToPageFoundInformation(String code) {
        goToFiiListadosPage();
        searchFor(code).click();
        sleep();
        return driver.findElement(By.xpath(FIRST_CARD_PATH));
    }
 
    private void sleep(){
        try { Thread.sleep(1000); } 
        catch (InterruptedException e) { throw new RuntimeException(e);}
    }

    public FundAbout getFundsInformationsAbout(String code) {
        goToPageFoundInformation(code).click();
        sleep();
        FundAbout about = new FundAbout();
        driver.findElement(By.xpath(CARDS_FUNDS_INFORMATION)).findElements(By.tagName("div"))
            .forEach(div ->{
                if(div.getText().toLowerCase().contains("dados")){
                    about.data = div.getAttribute(INNER_HTML); 
                }
                if(div.getText().toLowerCase().contains("contatos")){
                    about.contact = div.getAttribute(INNER_HTML);
                }
            });

            about.history = driver.findElement(By.xpath(HISTORY_PAGE_URL)).getAttribute("href");

        return about;
    }
}