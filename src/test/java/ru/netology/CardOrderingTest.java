package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardOrderingTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @Test
    void test() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Иван Петров");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79110000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[role='button']")).click();
        String actual = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expected, actual.trim());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }
}
