package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;


    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password) {
        inputPassword.sendKeys(password);
        inputUsername.sendKeys(username);
        inputUsername.submit();
    }

    public boolean loginFailed(WebDriver driver) {
        WebElement errorBanner = driver.findElement(By.id("login-error"));
        return errorBanner.getText().equals("Invalid username or password");
    }
}
