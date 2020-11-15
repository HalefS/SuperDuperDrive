package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


/**
 * Credential controller manages all the routes related to managing the credentials
 * on our database (create, delete, update)
 */
@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    /**
     * Add a new credential to the database
     * @param credential credential object from the view
     * @param redirectAttributes for redirect info
     * @param authentication curent authenticated authority
     * @return result view
     */
    @PostMapping
    public RedirectView addCredential(@ModelAttribute Credential credential, RedirectAttributes redirectAttributes, Authentication authentication) {
        // Credential already exists, because IDs are generate by the database
        if(credential.getCredentialId() != null) {
            updateCredential(credential, redirectAttributes);
            return new RedirectView("/result");
        }

        int userId = userService.getIdFromUsername(authentication.getName());
        String encryptionKey = encryptionService.generateRandomKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encryptionKey);
        credential.setKey(encryptionKey);
        credential.setPassword(encryptedPassword);
        // Perform database operation
        int result = credentialService.add(credential, userId);
        redirectAttributes.addFlashAttribute("success", result >= 1);
        redirectAttributes.addFlashAttribute("section", "#nav-credentials");
        return new RedirectView("/result");
    }

    /**
     * Remove a credential from the database based on the credentials id
     * @param id Credential id
     * @param redirectAttributes redeirect info
     * @param authentication current authenticated user
     * @return result view
     */
    @GetMapping("/{id}/delete")
    @PreAuthorize("@userService.getUsernameFromId(@credentialService.getUserId(#id)).equals(#authentication.getName())")
    public RedirectView deleteCredential(@PathVariable int id, RedirectAttributes redirectAttributes, Authentication authentication) {
        boolean result = credentialService.delete(id);
        redirectAttributes.addFlashAttribute("success", result);
        return new RedirectView("/result");
    }

    /**
     * update a credential on the database, url, username or password
     * @param credential credential object
     * @param redirectAttributes redirect info
     */
    private void updateCredential(Credential credential, RedirectAttributes redirectAttributes) {
        // We need the old key, because it is not shared on the views for security reasons
        String key = credentialService.getById(credential.getCredentialId()).getKey();
        // Wee need to re-encrypt the password, even if the user didnt change it because the value on the field
        // on the view is changed to the decoded one (plain text)
        String newPassword = encryptionService.encryptValue(credential.getPassword(), key);
        credential.setPassword(newPassword);
        credentialService.update(credential);
        redirectAttributes.addFlashAttribute("success", true);
    }


}
