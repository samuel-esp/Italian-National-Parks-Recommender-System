package com.example.sii_2021.services;

import com.example.sii_2021.configuration.CredentialsSingleton;
import com.example.sii_2021.configuration.SeleniumConfiguration;
import com.example.sii_2021.entities.Link;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
public class ScraperService {

    @Autowired
    private LinkService linkService;

    //private final SeleniumConfiguration seleniumConfiguration = new SeleniumConfiguration();

    public ChromeDriver scrapeLinks() throws InterruptedException {

        System.out.println("prova");

        ChromeOptions options = new ChromeOptions();
        String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.517 Safari/537.36";
        options.addArguments("window-size=1000,1200");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("general.useragent.override", userAgent);

        ChromeDriver driver = new ChromeDriver(options);
        driver.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
        log.info(driver.executeScript("return navigator.userAgent;").toString());

        //mi estraggo tutti i link e li salvo nel db (1)
        extractLinks(driver);

        return driver;

    }


    //TODO
    public void extractEntities(ChromeDriver driver){

        //estraggo informazione da tutti i link presenti nel db  (2)
        driver.quit();


    }

    private void extractLinks(ChromeDriver driver) throws InterruptedException {

        List<String> seeds = initializeSeeds();
        Collections.shuffle(seeds);

        userLogin(driver);
        Thread.sleep(4000);
        driver.get("https://www.alltrails.com/us/arizona");

        List<WebElement> trailsWebElement = showAllEntities(driver);
        createLinkEntities(trailsWebElement);


        log.info("completed");

    }

    private List<WebElement> showAllEntities(ChromeDriver driver) {
        int i = 0;
        while(driver.findElementsByXPath("//button[@title='Show more trails']").size()!=0 && i<1000){
            driver.findElementByXPath("//button[@title='Show more trails']").sendKeys(Keys.ENTER);
            i = i + 10;
        }
        List<WebElement> trailsWebElement = driver.findElementsByXPath("//a[@itemprop='url']");

        return trailsWebElement;
    }

    private void createLinkEntities(List<WebElement> trailsWebElement) {

        int savedEntities = 0;

        for(WebElement element: trailsWebElement){
            String link = element.getAttribute("href");

            Link linkEntity = Link.builder()
                    .link(link)
                    .status("UNDONE")
                    .build();

            linkService.saveEntity(linkEntity);
            savedEntities = savedEntities + 1;
        }

        log.info(savedEntities + " Found and Saved");

    }

    public void userLogin(ChromeDriver driver) throws InterruptedException {
        driver.get("https://www.alltrails.com/login?ref=header");
        Thread.sleep(15000);
        driver.findElementByXPath("//input[@name='userEmail']").sendKeys(CredentialsSingleton.getInstance().getEmail());
        Thread.sleep(2000);
        driver.findElementByXPath("//input[@name='userPassword']").sendKeys(CredentialsSingleton.getInstance().getPassword());
        Thread.sleep(2000);
        driver.findElementByXPath("//input[@value='Log in']").sendKeys(Keys.ENTER);
    }

    public List<String> initializeSeeds(){

        List<String> allTrailsSeeds = new LinkedList<>();
        allTrailsSeeds.add("https://www.alltrails.com/us/arizona");

        return allTrailsSeeds;

    }

}
