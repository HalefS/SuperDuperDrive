package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {

    @FindBy(id = "nav-notes-tab")
    WebElement notesNavTab;

    @FindBy(id = "addNote")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "notesModalSubmit")
    private WebElement noteSubmit;

    @FindBy(id = "notesTable")
    private WebElement notesTable;

    @FindBy(id = "logout-btn")
    private WebElement logoutButton;


    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void createNote(String title, String description, WebDriver driver) {
        notesNavTab.click();
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOf(addNoteButton));
        addNoteButton.click();
        wait.until(ExpectedConditions.visibilityOf(noteDescriptionField));
        noteTitleField.sendKeys(title);
        noteDescriptionField.sendKeys(description);
        noteSubmit.click();
    }

    public void editNote(WebDriver driver, String property, String originalValue, String newValue) {
        notesNavTab.click();
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOf(notesTable));
        WebElement noteEditButton = notesTable.findElement(By.id(originalValue + "edit"));
        noteEditButton.click();
        wait.until(ExpectedConditions.visibilityOf(noteTitleField));
        editNoteProperty(property, newValue);
        noteSubmit.click();
    }

    public boolean noteExists(String title, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOf(notesNavTab));
        notesNavTab.click();
        return notesTable.findElement(By.id(title)) != null;
    }

    public void logout() {
        logoutButton.click();
    }

    private void editNoteProperty(String property, String newValue) {
        switch (property.toLowerCase()) {
            case "title":
                noteTitleField.clear();
                noteTitleField.sendKeys(newValue);
                break;
            case "description":
                noteDescriptionField.clear();
                noteDescriptionField.sendKeys(newValue);
                break;
            default:
                break;
        }
    }
}
