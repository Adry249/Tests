package org.example.testng;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.pom.FormPom;
import org.example.utils.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.time.Duration;

public class FormTest {

    private static final Logger logger = LogManager.getLogger(FormTest.class);

    public static WebDriver driver;
    // Navigate directly to form page — avoids ad interception on homepage
    public static String Url = "https://demoqa.com/automation-practice-form";
    public static String FirstName = "Avram";
    public static String LastName = "Adrian";
    public static String Email = "avram@gmail.com";
    public static String Gender = "Male";
    public static String NUMBER = "0123456666";
    public static String DATE = "23 Oct 2005";
    public static String SUBJECT = "Maths";
    public static String HOBBY = "Sports";
    public static String STATE = "Japan";
    public static String CITY = "Tokyo";

    @BeforeMethod
    public void beforeMethod() throws MalformedURLException {
        logger.info("Start Before Method");
//        driver = Driver.getAutoLocalDriver();
        driver = Driver.getRemoteDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void studentFormTest() throws InterruptedException {
        logger.info("Start Test");

        driver.get(Url);
        logger.info("Navigated to: " + driver.getCurrentUrl());

        FormPom formPom = new FormPom(driver);

        // Remove ads before interacting
        formPom.closeAdvert();
        formPom.pause(1000);

        // Wait for form to be ready
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#firstName")));
        logger.info("Form is visible, starting to fill");

        formPom.setFirstName(FirstName);
        logger.info("First Name set");

        formPom.setLastName(LastName);
        formPom.setUserEmail(Email);
        formPom.setGender(Gender);
        formPom.setUserNumber(NUMBER);
        formPom.setDate(DATE);
        formPom.setSubject(SUBJECT);
        formPom.setHobby(HOBBY);
        formPom.pause(2000);
        formPom.setState(STATE);
        formPom.setCity(CITY);
        formPom.clickSubmit();

        // Assertions
        String actualName = formPom.getFinalData("Student Name");
        Assert.assertEquals(actualName, FirstName + " " + LastName, "Name mismatch");

        String actualEmail = formPom.getFinalData("Student Email");
        Assert.assertEquals(actualEmail, Email, "Email mismatch");

        String actualGender = formPom.getFinalData("Gender");
        Assert.assertEquals(actualGender, Gender, "Gender mismatch");

        String actualPhone = formPom.getFinalData("Mobile");
        Assert.assertEquals(actualPhone, NUMBER, "Phone mismatch");

        String actualDate = formPom.getFinalData("Date of Birth");
        Assert.assertTrue(actualDate.contains("2005"), "Date mismatch");

        String actualSubject = formPom.getFinalData("Subjects");
        Assert.assertTrue(actualSubject.contains(SUBJECT), "Subject mismatch");

        String actualHobby = formPom.getFinalData("Hobbies");
        Assert.assertTrue(actualHobby.contains(HOBBY), "Hobby mismatch");

        String actualStateCity = formPom.getFinalData("State and City");
        Assert.assertEquals(actualStateCity, STATE + " " + CITY, "State and City mismatch");

        logger.info("Finish Test");
    }

    @AfterMethod
    public void afterMethod() {
        logger.info("Start After Method");
        driver.quit();
    }
}