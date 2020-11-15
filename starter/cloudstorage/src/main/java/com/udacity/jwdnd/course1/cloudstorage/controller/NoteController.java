package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * Creates and add new note to the database
     * @param note note object from the view
     * @param redirectAttributes redirect information
     * @param authentication current authenticated user
     * @return result view
     */
    @PostMapping
    public RedirectView addNote(@ModelAttribute("noteForm") Note note, RedirectAttributes redirectAttributes, Authentication authentication) {
        // note already exists (update)
        if (note.getNoteId() != null) {
            noteService.update(note);
            redirectAttributes.addFlashAttribute("success", true);
            return new RedirectView("/result");
        }

        int userId = userService.getIdFromUsername(authentication.getName());
        int result = noteService.add(note, userId);
        if(result >= 1)
            redirectAttributes.addFlashAttribute("success", true);
        else
            redirectAttributes.addFlashAttribute("success", false);
        return new RedirectView("/result");
    }

    /**
     * Delete a note from the database given the id
     * @param id note id
     * @param redirectAttributes redirect info
     * @param authentication current authenticated user
     * @return result view
     */
    @GetMapping("/{id}/delete")
    @PreAuthorize("@userService.getUsernameFromId(@noteService.getUserId(#id)).equals(#authentication.getName())")
    public RedirectView deleteNote(@PathVariable int id, RedirectAttributes redirectAttributes, Authentication authentication) {
        Note note = noteService.get(id);
        if(note != null) {
            noteService.delete(id);
            redirectAttributes.addFlashAttribute("success", true);
        }
        else {
            redirectAttributes.addFlashAttribute("success", false);
        }
        return new RedirectView("/result");
    }

}
