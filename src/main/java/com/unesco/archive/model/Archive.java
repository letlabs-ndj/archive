package com.unesco.archive.model;

import com.unesco.archive.model.enums.FileType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table
@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Archive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String filePath;
    @ManyToMany
    private List<ArchiveCategory> archiveCategories;
    @Column
    private String contentType;

}
