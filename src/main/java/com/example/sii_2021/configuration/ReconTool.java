package com.example.sii_2021.configuration;

import com.example.sii_2021.services.ScraperService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
@Slf4j
public class ReconTool implements CommandLineRunner {

    @Autowired
    private ScraperService scraperService;

    @Override
    public void run(String... args) throws Exception {

        ChromeOptions options = new ChromeOptions();
        String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.517 Safari/537.36";
        options.addArguments("window-size=1000,1200");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("general.useragent.override", userAgent);
        log.info("Chrome Driver Options Init");

        ChromeDriver driver = new ChromeDriver(options);
        driver.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
        log.info(driver.executeScript("return navigator.userAgent;").toString());

        System.out.println("\nTYPE 1 TO SCRAPE SCRAPE NEW LINKS ...\n");
        System.out.println("TYPE 2 TO EXTRACT ENTITIES ...\n");
        System.out.println("TYPE 3 TO DO BOTH ...\n");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();

        if (Integer.parseInt(str) == 1) {

            driver = scraperService.userLogin(driver);
            driver = scraperService.extractLinks(driver);
            driver.quit();


        } else if (Integer.parseInt(str) == 2) {

            driver = scraperService.userLogin(driver);
            driver = scraperService.extractEntitiesAndReviews(driver);
            driver.quit();

        }else {

            driver = scraperService.userLogin(driver);
            driver = scraperService.extractLinks(driver);
            driver = scraperService.extractEntitiesAndReviews(driver);
            driver.quit();

        }

    }

}
