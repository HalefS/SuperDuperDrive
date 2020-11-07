package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/")
public class HomeController {
    
    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    
    public HomeController(FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String homeView(Model model) {
        model.addAttribute("noteForm", new Note());
        model.addAttribute("credentialForm", new Credential());
        model.addAttribute("file", new File());
        return "home";
    }

    @GetMapping("/result")
    public String result() {
        return "result";
    }

    @ModelAttribute("credentials")
    public List<Credential> credentialsTable(Authentication authentication) {
        int userId = userService.getIdFromUsername(authentication.getName());
        return credentialService.getAllCredentialsForUser(userId);

    }

    @ModelAttribute("notes")
    public List<Note> notesTable(Authentication authentication) {
        int userId = userService.getIdFromUsername(authentication.getName());
        return noteService.getAllNotesForUser(userId);
    }

    @ModelAttribute("files")
    public List<File> filesTable(Authentication authentication) {
        int userId = userService.getIdFromUsername(authentication.getName());
        return fileService.getAllFilesForUser(userId);
    }

}
