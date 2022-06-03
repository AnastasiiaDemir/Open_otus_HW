import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static Enums.PersonalInfo.*;

public class FirstTest {
    private final Logger logger = LogManager.getLogger(FirstTest.class);
    private WebDriver driver;
    private final ConfigServer cfg = ConfigFactory.create(ConfigServer.class);

    private final String nameField = "//input[@id = 'id_fname']";
    private final String sNameField = "//input[@id = 'id_lname']";
    private final String lnameField = "//input[@id = 'id_fname_latin']";
    private final String lSNameField = "//input[@id = 'id_lname_latin']";
    private final String bNameField = "//input[@id = 'id_blog_name']";
    private final String dateBField = "//input[@name = 'date_of_birth']";
    private final String countryMenu = "//div[@data-slave-selector='.js-lk-cv-dependent-slave-city']/label/div";
    private final String cityMenu = "//label//input[@name='city']/following-sibling::div";
    private final String levelEnglishMenu = "//label//input[@name='english_level']/following-sibling::div";
    private final String contacts = "//input[@name='contact-%d-value']";

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    @After
    public void close() {
        if (driver != null) driver.close();
        logger.info("драйвер закрыт");
    }
    public void teardown() {
        driver.quit();
    }
    private WebElement elementToBeClickable(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
    }
    private WebElement elementPresenceOfElementLocated(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }
    private void elementVisibilityOf(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    private void elementTextToBe(String locator, String value){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBe(By.xpath(locator), value));
    }
    @Test
    public void openOtus() throws InterruptedException {
        authorization();
        openAboutMe();
        fieldPersonalInfo();
        teardown();
        authorization();
        openAboutMe();
        checkPersonalInfo();
    }

    private void authorization() {
        ChromeOptions option = new ChromeOptions();
        driver = new ChromeDriver(option);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(cfg.otusUrl());
        //нажать на кнопку Вход
        String buttonEntrance = "//button[@data-modal-id='new-log-reg']";
        driver.findElement(By.xpath(buttonEntrance)).click();
        //заполнить поле Электронная почта
        String fieldEmail = "//input[@type='text'][@name = 'email'][@placeholder='Электронная почта']";
        driver.findElement(By.xpath(fieldEmail)).sendKeys(cfg.email());
        //заполнить поле Введите пароль
        String fieldPassword = "//input[@type='password'][@placeholder='Введите пароль']";
        driver.findElement(By.xpath(fieldPassword)).sendKeys(cfg.password());
        //нажать на кнопку Войти
        String buttonToComeIn = "//div[contains(@class,\"new-input-line_last\")]/button[contains(text(), \"Войти\")]";
        driver.findElement(By.xpath(buttonToComeIn)).click();
        logger.info("Успешная авторизация");
    }

    private void openAboutMe() {

        final String fieldMenu = "//div[contains(@class,\"header2-menu__item-wrapper__username\")]";
        final String fieldPersonalArea = "//a[@title ='Личный кабинет']";
        final String fieldAboutMe = "//a[@href='/lk/biography/personal/'][@title='О себе']";
        new Actions(driver).moveToElement(driver.findElement(By.xpath(fieldMenu))).perform();
        elementToBeClickable(fieldPersonalArea).click();
        elementToBeClickable(fieldAboutMe).click();
    }

    private void fieldPersonalInfo() throws InterruptedException {
        //Заполняем блок Персональные данные
        driver.findElement(By.xpath(nameField)).clear();
        driver.findElement(By.xpath(sNameField)).clear();
        driver.findElement(By.xpath(lnameField)).clear();
        driver.findElement(By.xpath(lSNameField)).clear();
        driver.findElement(By.xpath(bNameField)).clear();
        driver.findElement(By.xpath(dateBField)).clear();
        driver.findElement(By.xpath(nameField)).sendKeys(NAME.getInfo());
        driver.findElement(By.xpath(sNameField)).sendKeys(SNAME.getInfo());
        driver.findElement(By.xpath(lnameField)).sendKeys(LNAME.getInfo());
        driver.findElement(By.xpath(lSNameField)).sendKeys(LSNAME.getInfo());
        driver.findElement(By.xpath(bNameField)).sendKeys(BNAME.getInfo());
        driver.findElement(By.xpath(dateBField)).sendKeys(BDATE.getInfo());

        //Заполним блок Основная информация

        WebElement getLevelEnglish = driver.findElement(By.xpath("//button[contains(text(), \"Ниже среднего\")] "));
        WebElement dropDownList = driver.findElement(By.xpath("//button[contains(text(),\"Средний (Intermediate)\")]/parent::div"));
        driver.findElement(By.xpath(levelEnglishMenu)).click();//кликнуть по полю выпадающего меню
       //через ожидание проверить, что выпадающее меню открыто (нет класса hide на элементе)
            elementVisibilityOf(dropDownList);//проверить, что список с кнопками выбора отображается на странице (через ожидание)
            getLevelEnglish.click();//кликнуть по кнопке
       // проверить, что список закрылся (наличие hide на элементе)
        elementTextToBe(levelEnglishMenu, "Ниже среднего (Pre-Intermediate)");// в поле поля отображается выбранное значение (через ожидание)
        driver.findElement(By.xpath(countryMenu)).click();
        String getCountry = "//button[@title='Россия']";
        elementToBeClickable(getCountry).click();
        Thread.sleep(1000);
        elementToBeClickable(cityMenu).click();
        Thread.sleep(1000);
        String getCity = "//button[@data-value='317']";
        elementToBeClickable(getCity).click();
        String willingToRelocate = "//span[@class='radio__label'][text()='Да']";
        driver.findElement(By.xpath(willingToRelocate)).click();

        //Заполним блок Контактная информация
        driver.findElement(By.xpath(String.format(contacts, 0))).clear();
        String communicationMethodTemplateLocation = "//input[@name='contact-%d-service']//following-sibling::div";
        driver.findElement(By.xpath(String.format(communicationMethodTemplateLocation, 0))).click();
        String skypeButton = "//button[@data-value='skype']";
        driver.findElement(By.xpath(skypeButton)).click();
        driver.findElement(By.xpath(String.format(contacts, 0))).sendKeys(COMMUNICATIONMETHODSKYPE.getInfo());
        String addButton = "//button[contains(text(), \"Добавить\")]";
        driver.findElement(By.xpath(addButton)).click();
        driver.findElement(By.xpath(String.format(contacts, 1))).clear();
        driver.findElement(By.xpath(String.format(communicationMethodTemplateLocation, 1))).click();
        String telegramButton = "//div[not(contains(@class,\"hide\"))]/div/button[@data-value='telegram']";
        driver.findElement(By.xpath(telegramButton)).click();
        driver.findElement(By.xpath(String.format(contacts, 1))).sendKeys(COMMUNICATIONMETHODTELEGRAM.getInfo());

        //Заполним Блок Другое
        String company = "//input[@name='company']";
        driver.findElement(By.xpath(company)).clear();
        String position = "//input[@name='work']";
        driver.findElement(By.xpath(position)).clear();
        String gender = "//select[@name='gender']";
        driver.findElement(By.xpath(gender)).click();
        String getGender = "//select[@name='gender']//option[@value = 'f']";
        driver.findElement(By.xpath(getGender)).click();
        driver.findElement(By.xpath(company)).sendKeys(COMPANYNAME.getInfo());
        driver.findElement(By.xpath(position)).sendKeys(POSITIONNAME.getInfo());
        String saveButton = "//button[@name='continue']";
        driver.findElement(By.xpath(saveButton)).click();
    }

    private void checkPersonalInfo() {
        String Name = elementPresenceOfElementLocated(nameField).getAttribute("value");
        Assert.assertTrue(Name.contains(NAME.getInfo()));
        logger.info("Имя " + Name);
        String SName = elementPresenceOfElementLocated(sNameField).getAttribute("value");
        Assert.assertTrue(SName.contains(SNAME.getInfo()));
        logger.info("Фамилия " + SName);
        String LName = elementPresenceOfElementLocated(lnameField).getAttribute("value");
        Assert.assertTrue(LName.contains(LNAME.getInfo()));
        logger.info("Имя латиницей " + LName);
        String LSName = elementPresenceOfElementLocated(lSNameField).getAttribute("value");
        Assert.assertTrue(LSName.contains(LSNAME.getInfo()));
        logger.info("Фамилия латиницей " + LSName);
        String BName = elementPresenceOfElementLocated(bNameField).getAttribute("value");
        Assert.assertTrue(BName.contains(BNAME.getInfo()));
        logger.info("Имя в блоге " + BName);
        String BDate = elementPresenceOfElementLocated(dateBField).getAttribute("value");
        Assert.assertTrue(BDate.contains(BDATE.getInfo()));
        logger.info("Дата " + BDate);
        String Country = elementPresenceOfElementLocated(countryMenu).getAttribute("textContent");
        Assert.assertTrue(Country.contains("Россия"));
        logger.info("Страна " + Country);
        String City = elementPresenceOfElementLocated(cityMenu).getAttribute("textContent");
        Assert.assertTrue(City.contains("Москва"));
        logger.info("Город " + City);
        String EnglishLevel = elementPresenceOfElementLocated(levelEnglishMenu).getAttribute("textContent");
        Assert.assertTrue(EnglishLevel.contains("Ниже среднего"));
        logger.info("Уровень английского " + EnglishLevel);
        String ContFirst = elementPresenceOfElementLocated(String.format(contacts, 0)).getAttribute("value");
        Assert.assertTrue(ContFirst.contains("mySkype"));
        logger.info("Контакт первый " + ContFirst);
        String ContSecond = elementPresenceOfElementLocated(String.format(contacts, 1)).getAttribute("value");
        Assert.assertTrue(ContSecond.contains("myTelegram"));
        logger.info("Контакт второй " + ContSecond);
    }
}



















