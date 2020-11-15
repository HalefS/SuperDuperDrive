package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.transform.Result;

public class ResultPage {

    @FindBy(id = "backSuccess")
    WebElement backToLoginButton;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver,  this);
    }

    public void OK() {
        backToLoginButton.sendKeys(Keys.ENTER);
    }
}
