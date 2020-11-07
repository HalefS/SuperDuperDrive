package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public RedirectView addNote(@ModelAttribute("noteForm") Note note, RedirectAttributes redirectAttributes, Authentication authentication) {
        // note already exists (update)
        if (note.getNoteId() != null) {
            noteService.update(note);
            redirectAttributes.addFlashAttribute("success", true);
            redirectAttributes.addFlashAttribute("section", "#nav-notes");
            return new RedirectView("/result");
        }

        int userId = userService.getIdFromUsername(authentication.getName());
        int result = noteService.add(note, userId);
        if(result >= 1)
            redirectAttributes.addFlashAttribute("success", true);
        else
            redirectAttributes.addFlashAttribute("success", false);

        redirectAttributes.addFlashAttribute("section", "#nav-notes");
        return new RedirectView("/result");
    }

    @GetMapping("/{id}/delete")
    public RedirectView deleteNote(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Note note = noteService.get(id);
        if(note != null) {
            noteService.delete(id);
            redirectAttributes.addFlashAttribute("success", true);
        }
        else {
            redirectAttributes.addFlashAttribute("success", false);
        }
        redirectAttributes.addFlashAttribute("section", "#nav-notes");
        return new RedirectView("/result");
    }


}
