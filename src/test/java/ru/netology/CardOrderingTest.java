package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    void shouldTestPositive() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Иван Петров");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79110000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[role='button']")).click();
        String actual = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expected, actual.trim());
    }

    @Test
    void shouldTestNegativeNameSymbol() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Иван!");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79110000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[role='button']")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals(expected, actual.trim());
    }

    @Test
    void shouldTestNegativeNameLanguage() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Hanna");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79110000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[role='button']")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals(expected, actual.trim());
    }

    @Test
    void shouldTestNegativeNameEmpty() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79110000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[role='button']")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText();
        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, actual.trim());
    }

    @Test
    void shouldTestNegativePhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Иван Петров");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("89110000000");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[role='button']")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual.trim());
    }

    @Test
    void shouldTestNegativePhoneEmpty() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Иван Петров");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button[role='button']")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText();
        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, actual.trim());
    }

    @Test
    void shouldTesNegativeCheckbox() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("span[data-test-id='name'] input")).sendKeys("Иван Петров");
        driver.findElement(By.cssSelector("span[data-test-id='phone'] input")).sendKeys("+79110000000");
        driver.findElement(By.cssSelector("button[role='button']")).click();

        assertNotNull(driver.findElement(By.className("input_invalid")));
    }

    @Test
    void shouldTestNegativeEmptyForm() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("button[role='button']")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText();
        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, actual.trim());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }
}
