package com.unesco.archive.api;

import com.unesco.archive.model.Archive;
import com.unesco.archive.model.ArchiveCategory;
import com.unesco.archive.service.ArchiveCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class ArchiveCategoryController {
    private ArchiveCategoryService archiveCategoryService;
    @Autowired
    public ArchiveCategoryController(ArchiveCategoryService archiveCategoryService) {
        this.archiveCategoryService = archiveCategoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArchiveCategory>> getAllArchives() {
        return new ResponseEntity<>(archiveCategoryService.getAllArchivesCategories(), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<ArchiveCategory> saveArchiveCategory(@RequestBody ArchiveCategory archiveCategory) {
        return new ResponseEntity<>(archiveCategoryService.saveArchiveCategory(archiveCategory), HttpStatus.CREATED);
    }
}
