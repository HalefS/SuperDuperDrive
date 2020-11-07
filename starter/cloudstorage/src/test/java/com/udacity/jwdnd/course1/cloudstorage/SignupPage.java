package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void singup(String firstName, String lastName) {
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputLastName.submit();
    }
}
