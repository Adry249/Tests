package org.example.pom;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.example.utils.Utils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.time.Duration;

public class FormPom {

    private WebDriver driver;
    public JavascriptExecutor js;
    private static final int DEFAULT_WAIT = 30;

    @FindBy(xpath = "//*[text()='Forms']")
    WebElement form;

    @FindBy(xpath = "//*[text()='Practice Form']")
    WebElement practiceForm;

    @FindBy(id = "firstName")
    WebElement firstName;

    @FindBy(id = "lastName")
    WebElement lastName;

    @FindBy(id = "userEmail")
    WebElement userEmail;

    @FindBy(id = "userNumber")
    WebElement userNumber;

    @FindBy(id = "subjectsInput")
    WebElement subjectsInput;

    @FindBy(id = "dateOfBirthInput")
    WebElement dateOfBirthInput;

    @FindBy(id = "state")
    WebElement state;

    @FindBy(id = "city")
    WebElement city;

    @FindBy(id = "submit")
    WebElement submit;

    public FormPom(WebDriver driverParam) {
        driver = driverParam;
        js = (JavascriptExecutor) driver;
        PageFactory.initElements(driverParam, this);
    }

    @Step("Click submit")
    public void clickSubmit() {
        takeScreenshot("Before submit");
        // Scroll to submit and remove any overlays before clicking
        closeAdvert();
        scrollToElement(submit);
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT))
                .until(ExpectedConditions.elementToBeClickable(submit));
        submit.click();
        takeScreenshot("After submit");
    }

    public String getFinalData(String labelParam) {
        Utils.explicitWait(driver, ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//table//*[text()='" + labelParam + "']/../*[2]")), DEFAULT_WAIT);
        WebElement name = driver.findElement(
                By.xpath("//table//*[text()='" + labelParam + "']/../*[2]"));
        return name.getText();
    }

    @Step("Set state")
    public void setState(String stateParam) {
        takeScreenshot("Before state");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT));

        scrollToElement(state);
        wait.until(ExpectedConditions.elementToBeClickable(state));
        state.click();

        WebElement dropState = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class,'menu')]//*[text()='" + stateParam + "']")
                )
        );

        scrollToElement(dropState);
        dropState.click();
        takeScreenshot("After state");
    }

    private void takeScreenshot(String stepName) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(stepName, "image/png", new ByteArrayInputStream(screenshot), ".png");
        } catch (Exception e) {
            Allure.addAttachment("Screenshot Error", e.toString());
        }
    }

    @Step("Set city")
    public void setCity(String cityParam) {
        takeScreenshot("Before set city");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT));
        wait.until(ExpectedConditions.elementToBeClickable(city));
        city.click();
        WebElement dropCity = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[text()='" + cityParam + "']")
                )
        );
        dropCity.click();
        takeScreenshot("After set city");
    }

    @Step("Set hobby")
    public void setHobby(String hobbyParam) {
        takeScreenshot("Before hobby");
        WebElement hobby = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id='hobbiesWrapper']//label[text()='" + hobbyParam + "']")
                ));
        hobby.click();
        takeScreenshot("After hobby");
    }

    @Step("Set user number")
    public void setUserNumber(String numberParam) {
        takeScreenshot("Before user number");
        userNumber.clear();
        userNumber.sendKeys(numberParam);
        takeScreenshot("After user number");
    }

    @Step("Set subject")
    public void setSubject(String subjectParam) {
        takeScreenshot("Before setSubject");
        subjectsInput.clear();
        subjectsInput.sendKeys(subjectParam);
        subjectsInput.sendKeys(Keys.ENTER);
        takeScreenshot("After setSubject");
    }

    @Step("Set date")
    public void setDate(String dateParam) {
        takeScreenshot("Before setDate");
        dateOfBirthInput.sendKeys(Keys.CONTROL, "a");
        dateOfBirthInput.sendKeys(dateParam);
        dateOfBirthInput.sendKeys(Keys.ENTER);
        takeScreenshot("After setDate");
    }

    @Step("Set gender")
    public void setGender(String genderParam) {
        takeScreenshot("Before setGender");
        WebElement gender = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id='genterWrapper']//label[text()='" + genderParam + "']")
                ));
        gender.click();
        takeScreenshot("After setGender");
    }

    @Step("Set first name")
    public void setFirstName(String firstNameParam) {
        takeScreenshot("Before set first name");
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT))
                .until(ExpectedConditions.elementToBeClickable(firstName));
        firstName.clear();
        firstName.sendKeys(firstNameParam);
        takeScreenshot("After set first name");
    }

    @Step("Set Last Name")
    public void setLastName(String lastNameParam) {
        takeScreenshot("Before set last name");
        lastName.clear();
        lastName.sendKeys(lastNameParam);
        takeScreenshot("After set last name");
    }

    @Step("Set email")
    public void setUserEmail(String userEmailParam) {
        takeScreenshot("Before set email");
        userEmail.clear();
        userEmail.sendKeys(userEmailParam);
        takeScreenshot("After set email");
    }

    public void clickForms() {
        js.executeScript("document.querySelector('.ad-container')?.remove();");
        js.executeScript("arguments[0].scrollIntoView(true);", form);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT));
        wait.until(ExpectedConditions.elementToBeClickable(form));
        form.click();
    }

    public void clickPracticeForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT));
        wait.until(ExpectedConditions.elementToBeClickable(practiceForm));
        practiceForm.click();
    }

    public void pause(int msParam) throws InterruptedException {
        Thread.sleep(msParam);
    }

    public void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void closeAdvert() {
        try {
            js.executeScript(
                    "['#adplus-anchor', '.ad-container', '#Ad.Plus-Anchor-0', " +
                            "'#Ad.Plus-Anchor-1', 'iframe[id*=\"google\"]', 'footer'].forEach(sel => {" +
                            "  document.querySelectorAll(sel).forEach(el => el.remove());" +
                            "});"
            );
        } catch (Exception ignored) {}

        // Also remove any fixed/sticky overlays that block clicks
        try {
            js.executeScript(
                    "document.querySelectorAll('*').forEach(el => {" +
                            "  const style = window.getComputedStyle(el);" +
                            "  if ((style.position === 'fixed' || style.position === 'sticky') " +
                            "      && el.tagName !== 'NAV' && el.id !== 'app') {" +
                            "    el.remove();" +
                            "  }" +
                            "});"
            );
        } catch (Exception ignored) {}
    }
}