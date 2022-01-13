package com.example.sii_2021.configuration;

import com.example.sii_2021.services.ScraperService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;

@Component
@Slf4j
public class ReconTool implements CommandLineRunner {

    @Autowired
    private ScraperService scraperService;

    @Override
    public void run(String... args) throws Exception {

        ChromeOptions options = new ChromeOptions();
        //String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.517 Safari/537.36";
        options.addArguments("window-size=980,661");
        options.addArguments("--enable-javascript");
        options.addArguments("--no-sandbox");
        options.addArguments("--incognito");
        options.addArguments("--nogpu");
        options.addArguments("--disable-gpu");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("useAutomationExtension", false);
        //options.addArguments("general.useragent.override", userAgent);
        options.addArguments("user-agent=Mozilla/5.0 (iPad; CPU OS 15_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.2 Mobile/15E148 Safari/604.1");
        log.info("Chrome Driver Options Init");

        ChromeDriver driver = new ChromeDriver(options);
        //ChromeDriver driver = new ChromeDriver();
        driver.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");
        driver = changeViewport(driver);
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

    public ChromeDriver changeViewport(ChromeDriver driver){

        int width = 980;
        int height = 661;

        //Remove the window from fullscreen (optional), if it s in fullscreen the outerHeight is not accurate
        driver.manage().window().setSize(new Dimension(800,800));

        JavascriptExecutor js= (JavascriptExecutor)driver;

        String windowSize = js.executeScript("return (window.outerWidth - window.innerWidth + "+width+") + ',' + (window.outerHeight - window.innerHeight + "+height+"); ").toString();

        //Get the values
        width = Integer.parseInt(windowSize.split(",")[0]);
        height = Integer.parseInt(windowSize.split(",")[1]);

        //Set the window
        driver.manage().window().setSize(new Dimension(width, height));

        return driver;

    }

}
