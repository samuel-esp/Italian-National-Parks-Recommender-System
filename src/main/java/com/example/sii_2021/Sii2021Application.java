package com.example.sii_2021;

import com.example.sii_2021.services.ScraperService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class Sii2021Application {


    @Autowired
    ScraperService scraperService;


    public static void main(String[] args) {

        SpringApplication.run(Sii2021Application.class, args);

    }

    @PostConstruct
    public void init() throws IOException, InterruptedException {
        ChromeDriver driver = scraperService.scrapeLinks();
        scraperService.extractEntities(driver);
    }

}
