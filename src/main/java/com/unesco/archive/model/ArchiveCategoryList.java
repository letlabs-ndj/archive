package com.unesco.archive.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter @Setter
@NoArgsConstructor
public class ArchiveCategoryList {
    List<ArchiveCategory> archiveCategories;

    public ArchiveCategoryList(List<ArchiveCategory> archiveCategories) {
        this.archiveCategories = archiveCategories;
    }
}
