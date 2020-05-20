import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestYandex {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(TestYandex.class);


    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup(); //Поднимаем драйвер
        driver = new ChromeDriver();
        driver.manage().window().maximize(); //Разворачиваем окно браузера на весь экран
        logger.info("Драйвер поднят");


    }

    @Test
    public void yandexMarketPage() {
        driver.get("https://market.yandex.ru"); //Открываем странцу в разделе мобильных телефонов
        logger.info("Перешли на Яндекс.Маркет");
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 10L, 125);
        wait.until(driver -> driver.findElement(By.xpath("//*[@class='popup2__content']")).isDisplayed() == false); //Применяем явное ожидание для пропадания поп-апа
        WebElement wbElectronika = driver.findElement(By.xpath("//*[@href='/catalog--elektronika/54440']")); //Находим элемент
        wbElectronika.click(); //Кликаем по по кнопке "Электроника"
        logger.info("Перешли во вкладку Электроника");

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS); // Применяем неявное ожидание для загрузки страницы
        WebElement wbMobilnye = driver.findElement(By.xpath("//*[@href='/catalog--mobilnye-telefony/54726/list?hid=91491']")); //Находим элемент
        wbMobilnye.click();
        logger.info("Перешли во вкладку Мобильные телефоны");

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS); // Применяем неявное ожидание для загрузки страницы
        WebElement wbShowAll = driver.findElement(By.xpath("//*[@class='_2XviVqx9xN']/button[@data-tid='dc1632ca 1badb8e1']"));
        wbShowAll.click();
        WebElement input = driver.findElement(By.xpath("//*[@name='Поле поиска']"));
        input.sendKeys("Meizu"); //Пишем в поле поиска Meizu
        WebElement checkBoxMeizu = driver.findElement(By.xpath("//*[@for='7893318_963630']/div[@class='LhMupC0dLR']"));
        checkBoxMeizu.click(); //Кликаем по чек-боксу
        logger.info("Выбираем Meizu");
        input.click();
        input.clear(); //Очищаем поле ввода
        input.sendKeys("Xiaomi"); //Пишем в поле вводе Xiaomi
        WebElement checkBoxXiaomi = driver.findElement(By.xpath("//*[@for='7893318_7701962']/div[@class='LhMupC0dLR']"));
        checkBoxXiaomi.click(); //Кликаем по чек-боксу
        logger.info("Выбираем Xiaomi");
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS); //Ждем пока загрузятся смартфоны по фильтрам
        WebElement checkBoxSmartphone = driver.findElement(By.xpath("//*[@for='16816262_16816264']/div[@class='LhMupC0dLR']"));
        checkBoxSmartphone.click(); //Кликаем по чек-боксу смартфоны
        logger.info("Ставим чек-бокс Смартфоны");

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        WebElement byPrice = driver.findElement(By.xpath("//*[@data-bem='{\"n-filter-sorter\":{\"options\":[{\"id\":\"aprice\",\"type\":\"asc\"},{\"id\":\"dprice\",\"type\":\"desc\"}],\"place\":\"\",\"showOnboarding\":\"\"}}']/a[@class='link link_theme_major n-filter-sorter__link i-bem link_js_inited']"));
        byPrice.click(); //Фильтруем по цене
        logger.info("Фильтруем по цене");
        wait.until(driver -> driver.findElements(By.xpath("//*[contains(@class, 'n-snippet-cell2 ')]/a[contains(@title, 'Xiaomi')]")).get(0).isDisplayed() == true); //Явное ожидание пока элемент не будет виден
        List <WebElement> imageXiaomiList = driver.findElements(By.xpath("//*[contains(@class, 'n-snippet-cell2 ')]/a[contains(@title, 'Xiaomi')]")); //Получаем List элементов картинок Xiaomi
        Actions actions = new Actions(driver);
        actions.moveToElement(imageXiaomiList.get(0)).build().perform(); //Наводим на первый в списке Xiaomi элемент
        wait.until(driver -> driver.findElements(By.xpath("//*[contains(@class, 'n-snippet-cell2 ')]/div/div/div/div[contains(@data-bem,'Xiaomi')]")).get(0).isDisplayed() == true);//Явное ожидание пока элемент не будет виден
        List <WebElement> xiaomiList = driver.findElements(By.xpath("//*[contains(@class, 'n-snippet-cell2 ')]/div/div/div/div[contains(@data-bem,'Xiaomi')]"));//Получаем List элементов кнопка "Добавить в сравнение" Xiaomi
        xiaomiList.get(0).click(); //Кликаем добавить в сравнение по первому элементу Xiaomi
        wait.until(driver -> driver.findElement(By.xpath("//*[@class='popup-informer__title']")).isDisplayed() == true);
        WebElement popupXiaomi = driver.findElement(By.xpath("//*[@class='popup-informer__title']"));
        Assert.assertTrue(popupXiaomi.isDisplayed()); //Проверяем что отобразился поп-ап
        logger.info("Добавили первый в списке Xiaomi в сравнение, проверили что отобразился поп-ап");

        wait.until(driver -> driver.findElements(By.xpath("//*[contains(@class, 'n-snippet-cell2 ')]/a[contains(@title, 'Meizu')]")).get(0).isDisplayed() == true);//Явное ожидание пока элемент не будет виден
        List <WebElement> imageMeizuList = driver.findElements(By.xpath("//*[contains(@class, 'n-snippet-cell2 ')]/a[contains(@title, 'Meizu')]")); //Получаем List элементов картинок Meizu
        actions.moveToElement(imageMeizuList.get(0)).build().perform(); //Наводим на первый в списке Meizu элемент
        wait.until(driver -> driver.findElements(By.xpath("//*[contains(@class, 'n-snippet-cell2 ')]/div/div/div/div[contains(@data-bem,'Meizu')]")).get(0).isDisplayed() == true); //Явное ожидание пока элемент не будет виден
        List <WebElement> meizuList = driver.findElements(By.xpath("//*[contains(@class, 'n-snippet-cell2 ')]/div/div/div/div[contains(@data-bem,'Meizu')]")); //Получаем List элементов кнопка "Добавить в сравнение" Meizu
        meizuList.get(0).click(); //Кликаем добавить в сравнение по первому элементу Meizu
        wait.until(driver -> driver.findElement(By.xpath("//*[@class='popup-informer__title']")).isDisplayed() == true);
        WebElement popupMeizu = driver.findElement(By.xpath("//*[@class='popup-informer__title']"));
        Assert.assertTrue(popupMeizu.isDisplayed()); //Проверяем что отобразился поп-ап
        logger.info("Добавили первый в списке Meizu в сравнение, проверили что отобразился поп-ап");

        WebElement popUpCompare = driver.findElement(By.xpath("//*[@class='button button_size_m button_theme_action i-bem button_js_inited']"));
        popUpCompare.click(); //Кликаем на Сравнить на появившемся поп-апе
        logger.info("Перешли на страницу Сравнение");

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS); //Ожидаем загрузки страницы
        List <WebElement> allCompare = driver.findElements(By.xpath("//*[@class='n-compare-head__name link']"));
        Assert.assertTrue(allCompare.size() == 2); //Проверям что на странице только 2 товара
        logger.info("Проверили что в сравнении 2 позиции");

        WebElement allCharacteristic = driver.findElement(By.xpath("//*[@class='link n-compare-show-controls__all']"));
        allCharacteristic.click(); //Кликаем на конпку Все характериситики
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS); //Ожидаем загрузки
        logger.info("Перешли во вкладку Все характеристики");

        ((JavascriptExecutor)driver).executeScript("window.scrollBy(" + 0 + "," + 200 + ");"); //Прокручиваем до строки Операционная система
        wait.until(driver -> driver.findElement(By.xpath("//*[text()[contains(.,'Операционная система')]]")).isDisplayed() == true); //Явное ожидание пока элемент не будет виден
        WebElement os = driver.findElement(By.xpath("//*[text()[contains(.,'Операционная система')]]"));
        Assert.assertTrue(os.isDisplayed()); //Проверили что отображается пункт оОперационная система
        logger.info("Проверили что отображается пункт Операционная система");

        WebElement differentCharacteristic = driver.findElement(By.xpath("//*[@class='link n-compare-show-controls__diff']"));
        differentCharacteristic.click(); //Кликаем по кнопке Различающиеся характеристики
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS); //Ожидаем загрузки
        logger.info("Перешли во вкладку Различающиеся характеристики");


        WebElement os1 = driver.findElement(By.xpath("//*[text()[contains(.,'Операционная система')]]"));
        Assert.assertFalse(os1.isDisplayed()); //Проверили что пункт Операционная система не отображается
        logger.info("Проверили что не отображается пункт Операционная система");
    }
    @After
    public void quit() {
        driver.quit();
    }
}

