import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class FirstTests {

    WebDriver driver; //utworzenie pustego pola driver, aby było dostępne we wszystkich metodach
    WebDriverWait wait;

    public void highlightElement(WebElement element){
        JavascriptExecutor js=(JavascriptExecutor)driver;
        js. executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }

    @Before //warunki poczatkowe testow, wykona się przed każdą metodą test
    public void setup(){
        System.setProperty("webdriver.chrome.driver","C:\\chromedriver\\chromedriver.exe");
        //ustawiamy property ze wskazaniem na chromedriver, ktorego uzyjemy w testach
        driver = new ChromeDriver(); //otworzy nam przeglądarkę
        System.out.println(" wykonuję się tutaj! przed metodą testową");
        wait = new WebDriverWait(driver,10);
    }

    @Test //kroki testowe - po prostu test do wykonania
    public void firstTest(){
        driver.get("https://dev.to"); //przejdź na stronę dev.to
        WebElement sideBarVideo = driver.findElement(By.xpath("/html/body/div[9]/div/div/div[1]/aside/nav[1]/div/a[3]")); //znajdz element week
        highlightElement(sideBarVideo);
        //sideBarVideo.click(); //klikamy w weekBtn
    }
    @Test
    public void openFirstVideoPage(){
        driver.get("https://dev.to");
        WebElement sideBarVideo = driver.findElement(By.partialLinkText("Videos"));
        highlightElement(sideBarVideo);
        sideBarVideo.click();
        //przechodzimy na strone z video
        //powinniśmy poczekać na załadowanie nowej strony

        wait.until(ExpectedConditions.urlToBe("https://dev.to/videos"));
        WebElement firstVideo = driver.findElement(By.className("video-image"));
        highlightElement(firstVideo);
        firstVideo.click();
    }
    @Test
    public void highlightFirstVideo(){
        driver.get("https://dev.to/videos");
        WebElement firstVideo = driver.findElement(By.className("video-image"));
        highlightElement(firstVideo);
        firstVideo.click();
    }
    //wejdz na strone dev.to
    //kliknąć w podcasts
    //wybrac pierwszy podcast - pobiorę nazwę pierwszego podcastu z listy
    //sprawdzic czy jestem na odpowieniej stronie - czy tytuł podcastu się zgadza
    //sprawdzic czy mogę nacisnąć play

    @Test
    public void selectFirstPodcast(){
        driver.get("https://dev.to");
        WebElement podcasts = driver.findElement(By.partialLinkText("Podcasts"));
        podcasts.click();
        wait.until(ExpectedConditions.urlToBe("https://dev.to/pod"));

        WebElement firstPodcast = driver.findElement(By.cssSelector(".content > h3:first-child"));
        String podcastTitleFromList = firstPodcast.getText();

        String firstPodcastFromListLink = driver.findElement(By.cssSelector("#substories > a:first-child")).getAttribute("href");
//
//        WebElement linkToPodcast = driver.findElement(By.cssSelector("#substories > a:first-child"));
//        String linkToPodcastHref = linkToPodcast.getAttribute("href");
//

        firstPodcast.click();
        wait.until(ExpectedConditions.urlToBe(firstPodcastFromListLink));
        WebElement podcastTitle = driver.findElement(By.cssSelector(".title > h1:nth-child(2)"));
        String podcastTitleText = podcastTitle.getText();
        assertTrue(podcastTitleFromList.contains(podcastTitleText));

        WebElement record = driver.findElement(By.className("record"));
        record.click();
        WebElement initializing = driver.findElement(By.className("status-message"));
        wait.until(ExpectedConditions.invisibilityOf(initializing));

        WebElement recordWrapper = driver.findElement(By.className("record-wrapper"));
        String classAttribute = recordWrapper.getAttribute("class");
        Boolean isPodcastPlayed = classAttribute.contains("playing");

        assertTrue(isPodcastPlayed);
        //znajdz record
        //kliknij record :)
        //zadanko 2 : napisz na click meetingu, jak sprawdzić czy podcast jest "odpalony" XD
        //div zmienia nazwę klasy na record-wrapper playing
    }


//    @After //czynnosci zamykające testy
//    public void tearDown(){
//        driver.quit();
//        System.out.println("po każdej metodzie testowej");
//    }
}
