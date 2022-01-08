package com.example.sii_2021.services;

import com.example.sii_2021.configuration.CredentialsSingleton;
import com.example.sii_2021.entities.Link;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
public class ScraperService {

    @Autowired
    private LinkService linkService;

    //TODO
    public ChromeDriver extractEntities(ChromeDriver driver){

        //estraggo informazione da tutti i link presenti nel db  (2)
        driver.quit();

        return driver;

    }
    
    public ChromeDriver extractLinks(ChromeDriver driver) throws InterruptedException {

        List<String> seeds = initializeSeeds();
        Collections.shuffle(seeds);

        Thread.sleep(4000);
        driver.get("https://www.alltrails.com/us/arizona");

        List<WebElement> trailsWebElement = showAllCards(driver);
        saveLinks(trailsWebElement);


        log.info("completed");

        return driver;

    }

    private List<WebElement> showAllCards(ChromeDriver driver) {
        int i = 0;
        while(driver.findElementsByXPath("//button[@title='Show more trails']").size()!=0 && i<1000){
            driver.findElementByXPath("//button[@title='Show more trails']").sendKeys(Keys.ENTER);
            i = i + 10;
        }
        List<WebElement> trailsWebElement = driver.findElementsByXPath("//a[@itemprop='url']");

        return trailsWebElement;
    }

    private void saveLinks(List<WebElement> trailsWebElement) {

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

        log.info(savedEntities + "Links Found and Saved");

    }

    public ChromeDriver userLogin(ChromeDriver driver) throws InterruptedException {

        driver.get("https://www.alltrails.com/login?ref=header");
        Thread.sleep(15000);
        driver.findElementByXPath("//input[@name='userEmail']").sendKeys(CredentialsSingleton.getInstance().getEmail());
        Thread.sleep(2000);
        driver.findElementByXPath("//input[@name='userPassword']").sendKeys(CredentialsSingleton.getInstance().getPassword());
        Thread.sleep(2000);
        driver.findElementByXPath("//input[@value='Log in']").sendKeys(Keys.ENTER);

        return driver;
    }

    public List<String> initializeSeeds(){

        List<String> allTrailsSeeds = new LinkedList<>();
        allTrailsSeeds.add("https://www.alltrails.com/us/arizona");

        return allTrailsSeeds;

    }

}
