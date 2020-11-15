package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;


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

    /**
     * Home page for the application, also prepares the empty models for the view
     * @param model model
     * @return home view
     */
    @GetMapping
    public String homeView(Model model) {
        model.addAttribute("noteForm", new Note());
        model.addAttribute("credentialForm", new Credential());
        model.addAttribute("file", new File());
        return "home";
    }

    /**
     * Result view after any operation on the app
     * @return result view
     */
    @GetMapping("/result")
    public String result() {
        return "result";
    }

    /**
     * Returns a list of all the credentials on our database to be user on the homepage
     * on the credentials table
     * @param authentication current authenticated user
     * @return list of credentials
     */
    @ModelAttribute("credentials")
    public List<Credential> credentialsTable(Authentication authentication) {
        int userId = userService.getIdFromUsername(authentication.getName());
        return credentialService.getAllCredentialsForUser(userId);

    }

    /**
     * Returns a list of all the notes on our database to be user on the homepage
     * on the notes table
     * @param authentication current authenticated user
     * @return list of notes
     */
    @ModelAttribute("notes")
    public List<Note> notesTable(Authentication authentication) {
        int userId = userService.getIdFromUsername(authentication.getName());
        return noteService.getAllNotesForUser(userId);
    }

    /**
     * Returns a list of all the files on our database to be user on the homepage
     * on the files table
     * @param authentication current authenticated user
     * @return list of files
     */
    @ModelAttribute("files")
    public List<File> filesTable(Authentication authentication) {
        int userId = userService.getIdFromUsername(authentication.getName());
        return fileService.getAllFilesForUser(userId);
    }

}
