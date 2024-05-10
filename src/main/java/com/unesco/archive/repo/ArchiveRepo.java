package com.unesco.archive.repo;

import com.unesco.archive.model.Archive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchiveRepo extends JpaRepository<Archive, Long> {
    @Query(value = "SELECT ar FROM Archive ar JOIN ar.archiveCategories ac WHERE ac.category =:category")
    List<Archive> findAllArchivesByCategories(@Param("category") String category);
}
