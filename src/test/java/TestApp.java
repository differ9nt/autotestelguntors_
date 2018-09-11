import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.support.ui.Select;


import java.util.concurrent.TimeUnit;

public class TestApp {
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        ChromeOptions options = new ChromeOptions();
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        options.addArguments("user-data-dir=/your_path/");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        DesiredCapabilities cap = new DesiredCapabilities();
        System.setProperty("webdriver.chrome.driver","C:\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://elguntors-stg.salesfloor.net/reggie");
    }
    @Test
    public void test() throws InterruptedException {
        System.out.println("Chat Status is: " + driver.findElement(By.xpath("//div[@id='AtChatStatus']")).getText());
        String colorUnavailable = By.xpath("//span[text()='Unavailable']").findElement(driver).getCssValue("color").trim();
        String[] color_hexUnavailable = colorUnavailable.replace("rgba(", "").split(",");
        String actual_hexUnavailable = String.format("#%02x%02x%02x", Integer.parseInt(color_hexUnavailable[0].trim()), Integer.parseInt(color_hexUnavailable[1].trim()), Integer.parseInt(color_hexUnavailable[2].trim()));
        Assert.assertEquals("actual_hex should equal to: ", "#ee0c0c", actual_hexUnavailable);

        String colorAvailable = By.xpath("//span[text()='Available']").findElement(driver).getCssValue("color").trim();
        String[] color_hexAvailable = colorAvailable.replace("rgba(", "").split(",");
        String actual_hexAvailable = String.format("#%02x%02x%02x", Integer.parseInt(color_hexAvailable[0].trim()), Integer.parseInt(color_hexAvailable[1].trim()), Integer.parseInt(color_hexAvailable[2].trim()));
        Assert.assertEquals("actual_hex should equal to: ", "#46a629", actual_hexAvailable);

        driver.findElement(By.xpath("//a[@class='jumbotron__social-list__link js-service-link']")).click();
        driver.switchTo().frame(driver.findElement(By.id("inscription")));
        driver.findElement(By.id("newsletterInscEmail")).sendKeys("name@gmail.com");
        driver.findElement(By.id("inscName")).sendKeys("nastya");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebElement textSuccessWeb = driver.findElement(By.className("global-services__validation__title"));
        String textSuccessStr = textSuccessWeb.getText();
        Assert.assertEquals("not success: ","Thank you!",textSuccessStr);
        driver.switchTo().defaultContent();
        driver.findElement(By.id("inscriptionModal")).findElement(By.tagName("button")).click();

        driver.findElement(By.xpath("//a[@id='AtAppointmentLink']")).click();
        Thread.sleep(3000);
        driver.switchTo().frame(driver.findElement(By.id("bookAnAppointment")));
        driver.findElement(By.xpath("//label[@for='phoneService']")).click();
        driver.findElement(By.name("choosenDatePlaceholder")).click();
        driver.findElement(By.xpath("//a[text()='26']")).click();
        Select oSelectTime = new Select(driver.findElement(By.id("choosenTime")));
        oSelectTime.selectByValue("10:00 PM");
        driver.findElement(By.id("name")).sendKeys("Nastya");
        driver.findElement(By.id("email")).sendKeys("d@gmail.com");
        driver.findElement(By.id("phone")).sendKeys("06 12345678");
        driver.findElement(By.id("extraInfo")).sendKeys("comment");
        driver.findElement(By.id("autoSubscribe")).click();
        Thread.sleep(3000);
        driver.findElement(By.tagName("button")).click();
        driver.switchTo().defaultContent();

    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }


}
