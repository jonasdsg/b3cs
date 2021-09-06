package br.jonasdsg.b3cs.shared;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SharedConfiguration {
    
    public static WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver");
        return new ChromeDriver();
    }
    
    public static void sleep(Integer seconds){
        try { Thread.sleep(seconds * 1000); } 
        catch (InterruptedException e) { throw new RuntimeException(e); }    
    }
}
