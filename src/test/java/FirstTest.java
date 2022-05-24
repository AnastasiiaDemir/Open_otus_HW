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
import java.util.List;

public class FirstTest {

    private Logger logger = LogManager.getLogger(FirstTest.class);
    WebDriver driver;
    private ConfigServer cfg = ConfigFactory.create(ConfigServer.class);

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    @After
    public void close() {
        if (driver != null) driver.close();
        logger.info("драйвер закрыт");
    }

    String buttonEntrance = "//button[@data-modal-id='new-log-reg']";
    String fieldEmail = "//div[@class = 'new-input-line new-input-line_slim new-input-line_relative']/input[@type='text'][@name = 'email']";
    String fieldPassword = "//div[@class = 'new-input-line new-input-line_slim new-input-line_relative']/input[@type='password']";
    String buttonToComeIn = "//button[@class='new-button new-button_full new-button_blue new-button_md']";

    String name = "Настя";
    String sName = "Демирджи";
    String lName = "Anastasiia";
    String lSName = "Demirdzhi";
    String bName = "Anastasiia";
    String bDate = "15.01.1989";

    String communicationMethodSkype = "mySkype";
    String communicationMethodTelegram = "myTelegram";
    String companyName = "Рд-Лаб";
    String positionName = "Бизнес-аналитик";

    String nameField = "//input[@id = 'id_fname']";
    String sNameField = "//input[@id = 'id_lname']";
    String lnameField = "//input[@id = 'id_fname_latin']";
    String lSNameField = "//input[@id = 'id_lname_latin']";
    String bNameField = "//input[@id = 'id_blog_name']";
    String dateBField = "//input[@name = 'date_of_birth']";

    String countryMenu = "//div[@data-slave-selector='.js-lk-cv-dependent-slave-city']/label/div";
    String getCountry = "//button[@title='Россия']";
    String cityMenu = "//label//input[@name='city']/following-sibling::div";
    String getCity = "//button[@data-value='317']";
    String levelEnglishMenu = "//label//input[@name='english_level']/following-sibling::div";
    String getLevelEnglish = "//div[@class='lk-cv-block__select-scroll  js-custom-select-options']//button[@data-value=3]";
    String willingToRelocate = "//span[@class='radio__label'][text()='Да']";
    String skypeButton = "//div[@class='lk-cv-block__select-options lk-cv-block__select-options_left js-custom-select-options-container']//div//button[@data-value='skype']";
    String telegramButton = "//div[@class='lk-cv-block__select-options lk-cv-block__select-options_left js-custom-select-options-container']//div//button[@data-value='telegram']";
    String addButton = "//button[@class='lk-cv-block__action lk-cv-block__action_md-no-spacing js-formset-add js-lk-cv-custom-select-add']";

    String CommunicationMethodFirst = "//input[@name='contact-0-service']//following-sibling::div";
    String CommunicationMethodSecond = "//input[@name='contact-1-service']//following-sibling::div";
    String ContactFirst = "//input[@name='contact-0-value'][@class='input input_straight-top-left input_straight-bottom-left lk-cv-block__input lk-cv-block__input_9 lk-cv-block__input_md-8']";
    String ContactSecond ="//input[@name='contact-1-value'][@class='input input_straight-top-left input_straight-bottom-left lk-cv-block__input lk-cv-block__input_9 lk-cv-block__input_md-8']";

    String gender = "//select[@name='gender']";
    String getGender = "//select[@name='gender']//option[@value = 'f']";
    String company = "//input[@name='company']";
    String position = "//input[@name='work']";
    String saveButton = "//button[@name='continue']";

    String workFormatFullDay = "//input[@title='Полный день']";
    String workFormatFlexible = "//input[@title='Гибкий график']";
    String workFormatDistant = "//input[@title='Удаленно']";
    String checkboxes = "//div[@class='container__col container__col_9 container__col_md-8 container__col_middle']//input[@type='checkbox']";

    @Test
    public void openOtus() throws InterruptedException {
        authorization();
        openAboutMe();
        fieldPersonalInfo();
        authorization();
        openAboutMe();
        checkPersonalInfo();
    }

    public void authorization(){
        ChromeOptions option = new ChromeOptions();
        driver = new ChromeDriver(option);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(cfg.otusUrl());
        //нажать на кнопку Вход
        driver.findElement(By.xpath(buttonEntrance)).click();
        //заполнить поле Электронная почта
        driver.findElement(By.xpath(fieldEmail)).sendKeys(cfg.email());
        //заполнить поле Введите пароль
        driver.findElement(By.xpath(fieldPassword)).sendKeys(cfg.password());
        //нажать на кнопку Войти
        driver.findElement(By.xpath(buttonToComeIn)).click();
        logger.info("Успешная авторизация");
    }

    public void openAboutMe(){
        String fieldMenu = "//div[@class='header2-menu__item-wrapper header2-menu__item-wrapper__username']";
        String fieldPersonalArea = "//a[@class='header2-menu__dropdown-link header2-menu__dropdown-link_no-wrap'][@title ='Личный кабинет']";
        String fieldAboutMe = "//a[@href='/lk/biography/personal/'][@title='О себе']";

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        new Actions(driver).moveToElement(driver.findElement(By.xpath(fieldMenu))).perform();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(fieldPersonalArea))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(fieldAboutMe))).click();
    }

    public void fieldPersonalInfo() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        //Заполняем блок Персональные данные
        driver.findElement(By.xpath(nameField)).clear();
        driver.findElement(By.xpath(sNameField)).clear();
        driver.findElement(By.xpath(lnameField)).clear();
        driver.findElement(By.xpath(lSNameField)).clear();
        driver.findElement(By.xpath(bNameField)).clear();
        driver.findElement(By.xpath(dateBField)).clear();
        driver.findElement(By.xpath(nameField)).sendKeys(name);
        driver.findElement(By.xpath(sNameField)).sendKeys(sName);
        driver.findElement(By.xpath(lnameField)).sendKeys(lName);
        driver.findElement(By.xpath(lSNameField)).sendKeys(lSName);
        driver.findElement(By.xpath(bNameField)).sendKeys(bName);
        driver.findElement(By.xpath(dateBField)).sendKeys(bDate);

        //Заполним блок Основная информация
        driver.findElement(By.xpath(countryMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(getCountry))).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(cityMenu))).click();
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(getCity))).click();
        driver.findElement(By.xpath(levelEnglishMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(getLevelEnglish))).click();
        driver.findElement(By.xpath(willingToRelocate)).click();
        fillCheckboxes();

        //Заполним блок Контактная информация
        driver.findElement(By.xpath(ContactFirst)).clear();
        driver.findElement(By.xpath(CommunicationMethodFirst)).click();
        driver.findElement(By.xpath(skypeButton)).click();
        driver.findElement(By.xpath(ContactFirst)).sendKeys(communicationMethodSkype);
        driver.findElement(By.xpath(addButton)).click();
        driver.findElement(By.xpath(ContactSecond)).clear();
        driver.findElement(By.xpath(CommunicationMethodSecond)).click();
        driver.findElement(By.xpath(telegramButton)).click();
        driver.findElement(By.xpath(ContactSecond)).sendKeys(communicationMethodTelegram);

        //Заполним Блок Другое
        driver.findElement(By.xpath(company)).clear();
        driver.findElement(By.xpath(position)).clear();
        driver.findElement(By.xpath(gender)).click();
        driver.findElement(By.xpath(getGender)).click();
        driver.findElement(By.xpath(company)).sendKeys(companyName);
        driver.findElement(By.xpath(position)).sendKeys(positionName);
        driver.findElement(By.xpath(saveButton)).click();
    }

    private void checkPersonalInfo() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String Name = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(nameField))).getAttribute("value");
        Assert.assertTrue(Name.contains(name));
        logger.info("Имя " + Name);
        String SName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(sNameField))).getAttribute("value");
        Assert.assertTrue(SName.contains(sName));
        logger.info("Фамилия " + SName);
        String LName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(lnameField))).getAttribute("value");
        Assert.assertTrue(LName.contains(lName));
        logger.info("Имя латиницей " + LName);
        String LSName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(lSNameField))).getAttribute("value");
        Assert.assertTrue(LSName.contains(lSName));
        logger.info("Фамилия латиницей " + LSName);
        String BName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(bNameField))).getAttribute("value");
        Assert.assertTrue(BName.contains(bName));
        logger.info("Имя в блоге " + BName);
        String BDate = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(dateBField))).getAttribute("value");
        Assert.assertTrue(BDate.contains(bDate));
        logger.info("Дата " + BDate);
        String Country = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(countryMenu))).getAttribute("textContent");
        Assert.assertTrue(Country.contains("Россия"));
        logger.info("Страна " + Country);
        String City = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(cityMenu))).getAttribute("textContent");
        Assert.assertTrue(City.contains("Москва"));
        logger.info("Город " + City);
        String EnglishLevel = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(levelEnglishMenu))).getAttribute("textContent");
        Assert.assertTrue(EnglishLevel.contains("Ниже среднего"));
        logger.info("Уровень английского " + EnglishLevel);
        String ContFirst = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(ContactFirst))).getAttribute("value");
        Assert.assertTrue(ContFirst.contains("mySkype"));
        logger.info("Контакт первый " + ContFirst);
        String ContSecond = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(ContactSecond))).getAttribute("value");
        Assert.assertTrue(ContSecond.contains("myTelegram"));
        logger.info("Контакт второй " + ContSecond);
    }

    private void fillCheckboxes(){

        List<WebElement> checkboxesList = driver.findElements(By.xpath(checkboxes));

        for (WebElement element : checkboxesList) {
            unhide(driver, element);
            logger.info(element + " сделали видимым чекбокс");
        }
        if (driver.findElement(By.xpath(workFormatDistant)).isSelected()) {
            logger.info("Уже установлен чек-бокс Удаленно");
        } else {
            driver.findElement(By.xpath(workFormatDistant)).click();
            logger.info("Установили чек-бокс Удаленно");
        }
        if (driver.findElement(By.xpath(workFormatFullDay)).isSelected()) {
            logger.info("Уже установлен чек-бокс Полный день");
        } else {
            driver.findElement(By.xpath(workFormatFullDay)).click();
            logger.info("Установили чек-бокс Полный день");
        }
        if (driver.findElement(By.xpath(workFormatFlexible)).isSelected()) {
            logger.info("Установлен чек-бокс Гибкий график");
            driver.findElement(By.xpath(workFormatFlexible)).click();
            logger.info("Убрали чек-бокс Гибкий график");
        } else {
            logger.info("Гибкий график не заполняем");
        }
    }

    private void unhide(WebDriver driver, WebElement element) {
        String script = "arguments[0].style.display='block';" + "return true;";
        ((JavascriptExecutor) driver).executeScript(script, element);
    }
}





















