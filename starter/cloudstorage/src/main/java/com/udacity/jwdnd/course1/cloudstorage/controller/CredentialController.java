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

    @GetMapping("/{id}/delete")
    @PreAuthorize("@userService.getUsernameFromId(@credentialService.getUserId(#id)).equals(#authentication.getName())")
    public RedirectView deleteCredential(@PathVariable int id, RedirectAttributes redirectAttributes, Authentication authentication) {
        boolean result = credentialService.delete(id);
        redirectAttributes.addFlashAttribute("success", result);
        return new RedirectView("/result");
    }

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
