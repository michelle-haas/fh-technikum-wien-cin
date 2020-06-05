package at.technikumwien.personwebapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.WebDriverManagerException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Tag("e2e-test")
public class IndexTest {
    // E2E Tests
    @LocalServerPort
    private long port; // hier bekommen wir den aktuellen Port
    private WebDriver driver;

    @BeforeAll // wird einmalig vor allen Tests gemacht
    public static void setUpBeforeClass(){
        // es läuft alles über diesen Driver
        WebDriverManager.chromedriver().setup(); // für Chrome
    }

    @BeforeEach
    public void setUp(){
        driver = new ChromeDriver(
                new ChromeOptions().setHeadless(true) // browser Fenster wird nicht geöffnet
        );
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @AfterEach
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void testIndex() throws InterruptedException {
        driver.get("http://localhost:" + port);

        WebElement button = driver.findElement(By.tagName("button"));
        assertEquals("alle Personen anzeigen", button.getText());

        var lis = driver.findElements(By.tagName("li")); // liefert eine Liste von li-Elementen
        assertEquals(1, lis.size());

        button.submit();
        assertEquals("http://localhost:" + port + "/?all=true",
                driver.getCurrentUrl());
    }

    @Test
    public void testIndexWithAll() throws InterruptedException {
        driver.get("http://localhost:" + port + "/?all=true");

        WebElement button = driver.findElement(By.tagName("button"));
        assertEquals("nur aktivierte Personen anzeigen", button.getText());

        var lis = driver.findElements(By.tagName("li")); // liefert eine Liste von li-Elementen
        assertEquals(2, lis.size());

        button.submit();
        assertEquals("http://localhost:" + port + "/?",
                driver.getCurrentUrl());
    }
}
