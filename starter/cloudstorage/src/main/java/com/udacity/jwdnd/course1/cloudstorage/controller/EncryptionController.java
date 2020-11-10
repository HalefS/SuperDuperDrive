package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.EncryptionService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/decodePassword")
public class EncryptionController {

    private final EncryptionService encryptionService;
    private final CredentialService credentialService;

    public EncryptionController(EncryptionService encryptionService, CredentialService credentialService) {
        this.encryptionService = encryptionService;
        this.credentialService = credentialService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("@userService.getUsernameFromId(@credentialService.getUserId(#id)).equals(#authentication.getName())")
    public Map<String, String> decodePassword(@RequestParam("credentialId") int id, Authentication authentication) {
        Credential credential = credentialService.getById(id);
        Map<String, String> decodedPassword = new HashMap<>();
        decodedPassword.put("password", encryptionService.decryptValue(credential.getPassword(), credential.getKey()));
        return decodedPassword;
    }
}
