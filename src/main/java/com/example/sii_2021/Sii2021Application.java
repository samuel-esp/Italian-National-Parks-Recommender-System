package com.example.sii_2021;

import com.example.sii_2021.services.ScraperService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Sii2021Application {


    public static void main(String[] args) {
        SpringApplication.run(Sii2021Application.class, args);

    }


}

