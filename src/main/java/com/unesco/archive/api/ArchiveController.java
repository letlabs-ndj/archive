package com.unesco.archive.api;

import com.unesco.archive.model.Archive;
import com.unesco.archive.model.ArchiveCategory;
import com.unesco.archive.service.ArchiveCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ArchiveController {
    @Autowired
    private ArchiveCategoryService archiveCategoryService;

    public ArchiveController(ArchiveCategoryService archiveCategoryService) {
        this.archiveCategoryService = archiveCategoryService;
    }

    
}
