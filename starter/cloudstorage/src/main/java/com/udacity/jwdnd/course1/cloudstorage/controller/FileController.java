package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/{id}/view")
    @PreAuthorize("@userService.getUsernameFromId(@fileService.getUserId(#id)).equals(#authentication.getName())")
    public ResponseEntity<ByteArrayResource> viewFile(@PathVariable int id, Authentication authentication) {
        File file = fileService.getById(id);
        if (file != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION)
                    .contentType(MediaType.valueOf(file.getContentType()))
                    .contentLength(file.getData().length)
                    .body(new ByteArrayResource(file.getData()));
        }

        return null;
    }

    /**
     * Deletes specific file using pathvariable id
     * uses @PreAuthorize annotation to make sure the user
     * has ownership of the mentioned resource
     * @param id resource id
     * @param redirectAttributes for flash messages
     * @param authentication authentication
     * @return resulting view
     */
    @GetMapping("/{id}/delete")
    @PreAuthorize("@userService.getUsernameFromId(@fileService.getUserId(#id)).equals(#authentication.getName())")
    public RedirectView deleteFile(@PathVariable int id, RedirectAttributes redirectAttributes, Authentication authentication) {
        boolean result = fileService.delete(id);
        redirectAttributes.addFlashAttribute("success", result);
        return new RedirectView("/result");
    }

    @PostMapping
    public RedirectView uploadFile(@RequestParam MultipartFile fileUpload, RedirectAttributes redirectAttributes, Authentication authentication) {
        File file = new File();
        int userId = userService.getIdFromUsername(authentication.getName());
        int result;
        try {
            result  = fileService.add(file, userId, fileUpload);
        } catch (IOException e) {
            result = -1;
        }

        if (result >= 1)
            redirectAttributes.addFlashAttribute("success", true);
        else
            redirectAttributes.addFlashAttribute("success", false);
        redirectAttributes.addFlashAttribute("section", "#nav-files");
        return new RedirectView("/result");
    }
}
