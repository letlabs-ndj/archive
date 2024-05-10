package com.unesco.archive.model;

import jakarta.persistence.*;
import lombok.*;

@Table
@Entity
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
public class ArchiveCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String category;
}
