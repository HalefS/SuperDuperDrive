package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    CredentialService credentialService;
    UserService userService;
    EncryptionService encryptionService;

    @PostMapping
    public RedirectView addCredential(@ModelAttribute Credential credential, RedirectAttributes redirectAttributes, Authentication authentication) {
        // Credential already exists, because IDs are generate by the database
        if(credential.getCredentialId() != null) {
            // We need the old key, because it is not shared on the views for security reasons
            String key = credentialService.getById(credential.getCredentialId()).getKey();
            // Wee need to re-encrypt the password, even if the user didnt change it because the value on the field
            // on the view is changed to the decoded one (plain text)
            String newPassword = encryptionService.encryptValue(credential.getPassword(), key);
            credential.setPassword(newPassword);
            credentialService.update(credential);
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("section", "#nav-credentials");
            return new RedirectView("/result");
        }
        int userId = userService.getIdFromUsername(authentication.getName());
        String encryptionKey = encryptionService.generateRandomKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encryptionKey);
        credential.setKey(encryptionKey);
        credential.setPassword(encryptedPassword);
        // Perform database operation
        int result = credentialService.add(credential, userId);
        if(result >= 1)
            redirectAttributes.addFlashAttribute("success", true);
        else
            redirectAttributes.addFlashAttribute("success", false);
        redirectAttributes.addFlashAttribute("section", "#nav-credentials");
        return new RedirectView("/result");
    }

    @GetMapping("/{id}/delete")
    public RedirectView deleteCredential(@PathVariable int id, RedirectAttributes redirectAttributes) {
        boolean result = credentialService.delete(id);
        redirectAttributes.addFlashAttribute("success", result);
        redirectAttributes.addFlashAttribute("section", "#nav-credentials");
        return new RedirectView("/result");
    }


}
