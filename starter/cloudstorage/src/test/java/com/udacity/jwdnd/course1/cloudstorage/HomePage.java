package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage {

    @FindBy(id = "nav-notes-tab")
    WebElement notesNavTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsNavTab;

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

    @FindBy(id = "credentialsTable")
    private WebElement credentialsTable;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;

    @FindBy(id = "credential-save")
    private WebElement credentialSave;

    @FindBy(id = "logout-btn")
    private WebElement logoutButton;

    private WebDriverWait wait;


    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 60);
    }

    public void createNote(String title, String description) {
        notesNavTab.click();
        wait.until(ExpectedConditions.visibilityOf(addNoteButton));
        addNoteButton.click();
        wait.until(ExpectedConditions.visibilityOf(noteDescriptionField));
        noteTitleField.sendKeys(title);
        noteDescriptionField.sendKeys(description);
        noteSubmit.click();
    }

    public void createCredential(String url, String username, String password, WebDriver driver) {
        credentialsNavTab.click();
        WebElement addCredentialBtn = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("addCredentialBtn"))));
        addCredentialBtn.click();
        wait.until(ExpectedConditions.visibilityOf(credentialUrlField));
        credentialUrlField.sendKeys(url);
        credentialUsernameField.sendKeys(username);
        credentialPasswordField.sendKeys(password);
        credentialSave.click();
    }

    public void editNote(String property, String originalValue, String newValue) {
        notesNavTab.click();
        wait.until(ExpectedConditions.visibilityOf(notesTable));
        WebElement noteEditButton = notesTable.findElement(By.id(originalValue + "edit"));
        noteEditButton.click();
        wait.until(ExpectedConditions.visibilityOf(noteTitleField));
        editNoteProperty(property, newValue);
        noteSubmit.click();
    }

    public boolean noteExists(String title) {
        notesNavTab.click();
        wait.until(ExpectedConditions.visibilityOf(notesNavTab));
        return notesTable.findElement(By.id(title)) != null;
    }

    public boolean credentialExists(String url) {
        wait.until(ExpectedConditions.elementToBeClickable(credentialsNavTab));
        credentialsNavTab.click();
        wait.until(ExpectedConditions.visibilityOf(credentialsTable));
        return credentialsTable.findElement(By.id(url)) != null;
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
