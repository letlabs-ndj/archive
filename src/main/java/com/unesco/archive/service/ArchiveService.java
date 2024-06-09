package com.unesco.archive.service;

import com.unesco.archive.model.Archive;
import com.unesco.archive.model.enums.FileType;
import com.unesco.archive.repo.ArchiveRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class ArchiveService {
    private final ArchiveRepo archiveRepo;
    @Autowired
    public ArchiveService(ArchiveRepo archiveRepo){
        this.archiveRepo=archiveRepo;
    }

    public Archive saveArchive(Archive archive){
        return archiveRepo.save(archive);
    }

    public List<Archive> getAllArchives(){
        return archiveRepo.findAll();
    }
    public Archive getArchiveById(Long id){
        return archiveRepo.findById(id)
                .orElseThrow(()->new RuntimeException("No archive with id = "+id+" found"));
    }

    public List<Archive> getArchiveByCategory(String cat){
        return archiveRepo.findAllArchivesByCategories(cat);
    }

    public Archive updateArchive(Archive archive){
        return archiveRepo.save(archive);
    }
    public void deleteArchive(Long id){
        archiveRepo.deleteById(id);
    }
}
