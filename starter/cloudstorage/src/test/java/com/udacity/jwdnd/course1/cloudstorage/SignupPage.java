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

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void singup(String username, String password) {
        inputFirstName.sendKeys("user");
        inputLastName.sendKeys("user");
        inputPassword.sendKeys(password);
        inputUsername.sendKeys(username);
        inputLastName.submit();
    }
}
