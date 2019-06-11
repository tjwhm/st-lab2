package wang.tjwhm.blog;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class WebTest {
    private WebDriver driver;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() {
        String driverPath = System.getProperty("user.dir") + "/src/chromedriver";
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
    }

    @Test
    public void test() {
        try {
            XSSFSheet sheet1 = new XSSFWorkbook(new FileInputStream(System.getProperty("user.dir") + "/src/list.xlsx")).getSheet("Sheet1");
            int rowIndex = 2;
            XSSFRow row = sheet1.getRow(rowIndex);

            while (row.getCell(0) != null && !row.getCell(0).getRawValue().equals("")) {

                driver.get("http://121.193.130.195:8800/login");
                driver.findElement(By.name("id")).click();
                driver.findElement(By.name("id")).clear();
                driver.findElement(By.name("id")).sendKeys(row.getCell(1).getRawValue());
                driver.findElement(By.name("password")).click();
                driver.findElement(By.name("password")).clear();
                driver.findElement(By.name("password")).sendKeys(row.getCell(1).getRawValue().substring(4, 10));
                driver.findElement(By.id("btn_login")).click();
                assertEquals(driver.findElement(By.id("student-git")).getText(), row.getCell(3).getStringCellValue());
                driver.findElement(By.id("btn_logout")).click();
                driver.findElement(By.id("btn_return")).click();
                System.out.println(row.getCell(2).getStringCellValue() + "'s test case clear.");
                row = sheet1.getRow(++rowIndex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

}