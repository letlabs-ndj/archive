package com.unesco.archive.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unesco.archive.model.ArchiveCategory;
import com.unesco.archive.model.ArchiveCategoryList;
import com.unesco.archive.repo.ArchiveCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchiveCategoryService {
    @Autowired
    private final ArchiveCategoryRepo archiveCategoryRepo;

    public ArchiveCategoryService(ArchiveCategoryRepo archiveCategoryRepo){
        this.archiveCategoryRepo = archiveCategoryRepo;
    }

    public ArchiveCategory saveArchiveCategory(ArchiveCategory archiveCategory){
        return archiveCategoryRepo.save(archiveCategory);
    }

    public List<ArchiveCategory> getAllArchivesCategories(){
        return archiveCategoryRepo.findAll();
    }

    public ArchiveCategory getArchiveCategoryById(Long id) {
        return archiveCategoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No archive with id = " + id + " found"));
    }

    public ArchiveCategoryList mapToArchiveCategories(String data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ArchiveCategoryList categoryList = mapper.readValue(data, ArchiveCategoryList.class);
            return categoryList;
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
        return null;
    }
}
