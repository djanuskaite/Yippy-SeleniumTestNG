import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Selenium {
    //browser pasideklaruojam kaip globalu kintamaji
    private static WebDriver browser;
    public static final int WAIT_FOR_TIME = 2;
    public static final String BING_SEARCH_BUTTON_XPATH = "//*[@id=\"search\"]/fieldset/input[2]";

    public static void main(String args[]){
        //atspausdinam su kokiu irankiu dirbam pirmiausia
        System.out.println("Selenium");

        setUp();

        searchByKeyword("Bocelli");

        compareResultsNumber();

        close();
    }
    //cia susikuriam atskiras funkcijas konkretiems veiksmams
    public static void setUp (){
        System.setProperty("webdriver.chrome.driver","src/drivers/chromedriver.exe");

        browser = new ChromeDriver();
        browser.get("https://www.yippy.com/");
    }

    //siuo atveju keyword - bet koks paieskos zodis - galima ieskoti pagal id, name, xpath - search laukeliui kok priskirtas html'e
    public static void searchByKeyword (String keyword){
        waitForVisibilityOfElementLocated("//*[@id=\"input-query\"]");
        WebElement searchField = browser.findElement(By.xpath("//*[@id=\"input-query\"]"));
        searchField.sendKeys(keyword);
//        searchField.sendKeys(Keys.ENTER); //cia simuliuojam enter paspaudima

        WebElement searchBtn = browser.findElement(By.xpath(BING_SEARCH_BUTTON_XPATH));

        //pirmas veikianti budas
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) browser; //castinimas cia vaziuoja :)
        javascriptExecutor.executeScript("arguments[0].click()",searchBtn);
    }

    public static String compareResultString(){
        WebElement countResults = browser.findElement(By.xpath("*[@id=\"search-details\"]"));

        //kaip viena eilute reikia rasyti
        String resultsStrWithoutNumbers = countResults.getText()
                .replaceAll("[0-9]", "")
                .replaceAll("[ ,]","");

        return resultsStrWithoutNumbers;
    }

    public static int compareResultsNumber(){
        WebElement countResults = browser.findElement(By.className("query"));
        System.out.println(countResults.getText());

        //kaip viena eilute reikia rasyti
        String results = countResults.getText()
                .replaceAll("[A-Za-z]", "")
                .replaceAll("[.  ,]","");

        //cia konvertuojam is string in integer
        int results2 = Integer.parseInt(results);

        if (results2>100){
            System.out.println("Atlikejas yra populiarus!");
        }else {
            System.out.println("Altikejas nelabai populiarus");
        }
        return results2;
    }

    public static void close (){
        browser.close();
    }

    public static void waitForVisibilityOfElementLocated(String xPath){
        WebDriverWait wait = new WebDriverWait(browser, WAIT_FOR_TIME);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
    }

    public static void waitForElementToBeClickable(String xPath){
        WebDriverWait wait = new WebDriverWait(browser, WAIT_FOR_TIME);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPath)));
    }

    public static void waitForTimeByID(String element){
        WebDriverWait wait = new WebDriverWait(browser, WAIT_FOR_TIME);
        wait.until(ExpectedConditions.elementToBeClickable(By.id(element)));
    }

};