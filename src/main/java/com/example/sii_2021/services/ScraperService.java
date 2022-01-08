package com.example.sii_2021.services;

import com.example.sii_2021.configuration.CredentialsSingleton;
import com.example.sii_2021.entities.Link;
import com.example.sii_2021.entities.Rating;
import com.example.sii_2021.entities.Trail;
import com.example.sii_2021.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
public class ScraperService {

    @Autowired
    private LinkService linkService;

    @Autowired
    private UserService userService;

    @Autowired
    private TrailService trailService;

    @Autowired
    private RatingService ratingService;

    //TODO
    public ChromeDriver extractEntitiesAndReviews(ChromeDriver driver) throws InterruptedException {

        //estraggo informazione da tutti i link presenti nel db  (2)
        Set<Link> linkSet = linkService.getLinksByStatus("UNDONE");
        int savedEntities = 0;
        for(Link link: linkSet){

            //open a new trail in the browser
            driver.get(link.getLink());

            //extract trail info
            String name = null;
            Double lengthKm = null;
            Double elevationM = null;

            //extract route type
            Integer loop = 0;
            Integer outAndBack = 0;
            Integer pointToPoint = 0;

            //extract trail difficulties
            Integer easy = 0;
            Integer medium = 0;
            Integer hard = 0;

            //extract activity tags
            Integer backpacking = 0;
            Integer bikeTouring = 0;
            Integer birdWatching = 0;
            Integer camping = 0;
            Integer crossCountrySkiing = 0;
            Integer fishing = 0;
            Integer hiking = 0;
            Integer horseBackRiding = 0;
            Integer mountainBiking = 0;
            Integer natureTrips = 0;
            Integer ohvOffRoadDriving = 0;
            Integer paddleSports = 0;
            Integer roadBiking = 0;
            Integer rockClimbing = 0;
            Integer scenicDriving = 0;
            Integer skiing = 0;
            Integer snowshoeing = 0;
            Integer running = 0;
            Integer viaFerrata = 0;
            Integer walking = 0;

            //extract attraction tags
            Integer beach = 0;
            Integer cave = 0;
            Integer cityWalk = 0;
            Integer event = 0;
            Integer forest = 0;
            Integer historicSite = 0;
            Integer hotSprings = 0;
            Integer lake = 0;
            Integer pubWalk = 0;
            Integer railsTrails = 0;
            Integer river = 0;
            Integer views = 0;
            Integer waterfalls = 0;
            Integer wildflowers = 0;
            Integer wildlife = 0;

            //extract suitability tags
            Integer dogFriendly = 0;
            Integer paved = 0;
            Integer kidFriendly = 0;
            Integer partiallyPaved = 0;
            Integer wheelchairFriendly = 0;
            Integer strollerFriendly = 0;

            //extract trail name
            try {
                name = driver.findElementByXPath("//h1[@itemprop='name']").getText();
            } catch (org.openqa.selenium.NoSuchElementException e) {

            }

            //extract trail info + route type
            try {
                List<WebElement> stats = driver.findElementsByXPath("//span[@class='styles-module__detailData___fxmwv']");
                String lengthKmString;
                String elevationMString;
                try {
                    lengthKmString = stats.get(0).getText();
                }catch (IndexOutOfBoundsException e){
                    lengthKmString = "0";
                }
                lengthKm = Double.parseDouble(lengthKmString.replaceAll("\\D+", ""));
                try {
                    elevationMString = stats.get(1).getText();
                }catch (IndexOutOfBoundsException e){
                    elevationMString = "0";
                }
                elevationM = Double.parseDouble(elevationMString.replaceAll("\\D+", ""));
                try {
                    if (stats.get(2).getText().equals("Out & back")) {
                        outAndBack = 1;
                    }
                    if (stats.get(2).getText().equals("Point to point")) {
                        pointToPoint = 1;
                    } else {
                        loop = 1;
                    }
                }catch (IndexOutOfBoundsException e){
                    pointToPoint = 1;
                }
            } catch (org.openqa.selenium.NoSuchElementException e) {

            }

            //extract trail difficulty
            try {
                String difficulty = driver.findElementByXPath("//div[@class='xlate-none styles-module__card___nwoZI styles-module__clickable___VTekh']//span[@class='styles-module__diff___uab6o styles-module__moderate____rxpe styles-module__selected___KQT0h']").getText();
                if(difficulty.equals("moderate")){
                    medium = 1;
                }if(difficulty.equals("hard")){
                    hard = 1;
                }else{
                    easy = 1;
                }
            } catch (org.openqa.selenium.NoSuchElementException e) {

            }

            //extract trail tags
            try {
                List<WebElement> tagList = driver.findElementsByXPath("//span[@class='tag']");
                for(WebElement element: tagList){

                    //extract activity tags
                    if(element.getText().equals("Backpacking")){
                        backpacking = 1;
                    }
                    if(element.getText().equals("Bike touring")){
                        bikeTouring = 1;
                    }
                    if(element.getText().equals("Bike touring")){
                        bikeTouring = 1;
                    }
                    if(element.getText().equals("Bird watching")){
                        birdWatching = 1;
                    }
                    if(element.getText().equals("Camping")){
                        camping = 1;
                    }
                    if(element.getText().equals("Hiking")){
                        hiking = 1;
                    }
                    if(element.getText().equals("Cross-country skiing")){
                        crossCountrySkiing = 1;
                    }
                    if(element.getText().equals("Fishing")){
                        fishing = 1;
                    }
                    if(element.getText().equals("Horseback riding")){
                        horseBackRiding = 1;
                    }
                    if(element.getText().equals("Mountain biking")){
                        mountainBiking = 1;
                    }
                    if(element.getText().equals("Nature trips")){
                        natureTrips = 1;
                    }
                    if(element.getText().equals("OHV/Off-road driving")){
                        ohvOffRoadDriving = 1;
                    }
                    if(element.getText().equals("Paddle sports")){
                        paddleSports = 1;
                    }
                    if(element.getText().equals("Road biking")){
                        roadBiking = 1;
                    }
                    if(element.getText().equals("Rock climbing")){
                        rockClimbing = 1;
                    }
                    if(element.getText().equals("Scenic driving")){
                        scenicDriving = 1;
                    }
                    if(element.getText().equals("Skiing")){
                        skiing = 1;
                    }
                    if(element.getText().equals("Snowshoeing")){
                        snowshoeing = 1;
                    }
                    if(element.getText().equals("Running")){
                        running = 1;
                    }
                    if(element.getText().equals("Via ferrata")){
                        viaFerrata = 1;
                    }
                    if(element.getText().equals("Walking")){
                        walking = 1;
                    }

                    //extract suitability tags
                    if(element.getText().equals("Dog friendly")){
                        dogFriendly = 1;
                    }
                    if(element.getText().equals("Paved")){
                        paved = 1;
                    }
                    if(element.getText().equals("Kid friendly")){
                        kidFriendly = 1;
                    }
                    if(element.getText().equals("Partially paved")){
                        partiallyPaved = 1;
                    }
                    if(element.getText().equals("Wheelchair friendly")){
                        wheelchairFriendly = 1;
                    }
                    if(element.getText().equals("Stroller friendly")){
                        strollerFriendly = 1;
                    }

                    //extract attraction tags
                    if(element.getText().equals("Beach")){
                        beach = 1;
                    }
                    if(element.getText().equals("Cave")){
                        cave = 1;
                    }
                    if(element.getText().equals("City walk")){
                        cityWalk = 1;
                    }
                    if(element.getText().equals("Event")){
                        event = 1;
                    }
                    if(element.getText().equals("Forest")){
                        forest = 1;
                    }
                    if(element.getText().equals("Historic site")){
                        historicSite = 1;
                    }
                    if(element.getText().equals("Hot springs")){
                        hotSprings = 1;
                    }
                    if(element.getText().equals("Lake")){
                        lake = 1;
                    }
                    if(element.getText().equals("Pub walk")){
                        pubWalk = 1;
                    }
                    if(element.getText().equals("Rails trails")){
                        railsTrails = 1;
                    }
                    if(element.getText().equals("River")){
                        river = 1;
                    }
                    if(element.getText().equals("Views")){
                        views = 1;
                    }
                    if(element.getText().equals("Waterfall")){
                        waterfalls = 1;
                    }
                    if(element.getText().equals("Wildflowers")){
                        wildflowers = 1;
                    }
                    if(element.getText().equals("Wildlife")){
                        wildlife = 1;
                    }else {

                    }
                }
            } catch (org.openqa.selenium.NoSuchElementException e) {

            }

            //Ora processiamo le recensioni degli utenti
            // prima ci assicuriamo di essere arrivati a fondo pagina cliccando sul pulsante "show more"

            log.info("Currently Processing Trail: " + link.getLink());
            log.info(driver.findElementsByXPath("//div[@class='styles-module__container___SMbPv xlate-none']//button[@title='Show more reviews']").size() + "");
            if(driver.findElementsByXPath("//div[@class='styles-module__container___SMbPv xlate-none']//button[@title='Show more reviews']").size()!=0) {
                while (driver.findElementsByXPath("//div[@class='styles-module__container___SMbPv xlate-none']//button[@title='Show more reviews']").size() == 0) {
                    driver.findElementByXPath("//div[@class='styles-module__container___SMbPv xlate-none']//button[@title='Show more reviews']").sendKeys(Keys.ENTER);
                }
            }

            //costruisco un trail
            Trail t = Trail.builder()
                    .name(name)
                    .lengthKm(lengthKm)
                    .elevationM(elevationM)
                    .loop(loop)
                    .outAndBack(outAndBack)
                    .pointToPoint(pointToPoint)
                    .easy(easy)
                    .medium(medium)
                    .hard(hard)
                    .backpacking(backpacking)
                    .bikeTouring(bikeTouring)
                    .birdWatching(birdWatching)
                    .camping(camping)
                    .crossCountrySkiing(crossCountrySkiing)
                    .fishing(fishing)
                    .hiking(hiking)
                    .horseBackRiding(horseBackRiding)
                    .mountainBiking(mountainBiking)
                    .natureTrips(natureTrips)
                    .ohvOffRoadDriving(ohvOffRoadDriving)
                    .paddleSports(paddleSports)
                    .roadBiking(roadBiking)
                    .rockClimbing(rockClimbing)
                    .scenicDriving(scenicDriving)
                    .skiing(skiing)
                    .snowshoeing(snowshoeing)
                    .running(running)
                    .viaFerrata(viaFerrata)
                    .walking(walking)
                    .beach(beach)
                    .cave(cave)
                    .cityWalk(cityWalk)
                    .event(event)
                    .forest(forest)
                    .historicSite(historicSite)
                    .hotSprings(hotSprings)
                    .lake(lake)
                    .pubWalk(pubWalk)
                    .railsTrails(railsTrails)
                    .river(river)
                    .views(views)
                    .waterfalls(waterfalls)
                    .wildflowers(wildflowers)
                    .wildlife(wildlife)
                    .dogFriendly(dogFriendly)
                    .paved(paved)
                    .kidFriendly(kidFriendly)
                    .partiallyPaved(partiallyPaved)
                    .wheelchairFriendly(wheelchairFriendly)
                    .strollerFriendly(strollerFriendly)
                    .ratingsSet(new HashSet<>())
                    .build();


            Thread.sleep(2000);
            List<WebElement> reviewCards = driver.findElementsByXPath("//div[@class='styles-module__content___u3Ojr styles-module__content___O4ebJ']");
            log.info(reviewCards.size() + "");
            log.info(reviewCards.toString());
            Set<User> userSet = new HashSet<>();
            Set<Rating> ratingSet = new HashSet<>();
            int i = 1;
            for(WebElement card: reviewCards){
                String userName = driver.findElementByXPath("(//div[@class='styles-module__nameTrailDetails___rTHi3'])" + "[" + i + "]").getText();
                String userLink = driver.findElementByXPath("(//a[@class='clickable styles-module__link48___B_oJ1 xlate-none styles-module__inlineBlock___uyWzl'])"  + "[" + i + "]").getAttribute("href");
                String userRatingString = driver.findElementByXPath("(//span[@class='MuiRating-root default-module__rating___LhvGE MuiRating-sizeLarge MuiRating-readOnly'])"  + "[" + i + "]").getAttribute("aria-label");
                Double userRating;
                if(userRatingString.equals("NaN Stars")){
                    userRating = 0.0;
                }else {
                    userRating = Double.parseDouble(userRatingString.replaceAll("[^0-9.]", ""));
                }
                log.info(userLink);
                log.info(userName);
                log.info(userRatingString);
                User u;
                Rating r;
                if(userService.getByUserLink(userLink)==null){

                    u = new User(userName, userLink, new HashSet<Rating>());

                    log.info(u.toString());
                    r = Rating.builder()
                            .user(u)
                            .userRating(userRating)
                            .trail(t)
                            .build();
                    log.info(r.toString());


                }else {

                    u = userService.getByUserLink(userLink);
                    r = Rating.builder()
                            .user(u)
                            .userRating(userRating)
                            .trail(t)
                            .build();
                    log.info(u.toString());
                    log.info(r.toString());
                }

                i = i +1;
                ratingSet.add(r);
                userSet.add(u);
            }

            saveToDb(userSet, t, link, ratingSet);
            savedEntities = savedEntities + 1;
            log.info(savedEntities + " Saved Entities");

        }

        return driver;

    }

    @Transactional
    public void saveToDb(Set<User> userSet, Trail t, Link link, Set<Rating> ratingSet) {
        if(t!=null) {
            trailService.saveTrail(t);
        }
        if(ratingSet!=null){
            for(Rating rating: ratingSet){
                userService.saveUser(rating.getUser());
                ratingService.saveRating(rating);
            }
        }/*
        if(userSet!=null) {
            userService.saveUserSet(userSet);
        }*/
        if(link!=null) {
            link.setStatus("DONE");
            linkService.saveEntity(link);
        }
    }

    @Transactional
    public ChromeDriver extractLinks(ChromeDriver driver) throws InterruptedException {

        List<String> seeds = initializeSeeds();
        Collections.shuffle(seeds);

        Thread.sleep(4000);
        driver.get("https://www.alltrails.com/italy/lazio");

        List<WebElement> trailsWebElement = showAllCards(driver);
        saveLinks(trailsWebElement);


        log.info("Extraction Completed");

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

        log.info(savedEntities + " Links Found and Saved");

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
