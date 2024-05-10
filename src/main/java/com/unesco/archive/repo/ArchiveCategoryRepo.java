package com.unesco.archive.repo;

import com.unesco.archive.model.ArchiveCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveCategoryRepo extends JpaRepository<ArchiveCategory,Long> {
}
