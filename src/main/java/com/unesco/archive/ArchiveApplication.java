package com.unesco.archive;

import com.unesco.archive.configs.StorageProperties;
import com.unesco.archive.model.ArchiveCategory;
import com.unesco.archive.service.ArchiveCategoryService;
import com.unesco.archive.service.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ArchiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArchiveApplication.class, args);
	}

	@Autowired
	StorageService storageService;

	@Autowired
	ArchiveCategoryService archiveCategoryService;
	
	@Bean
	CommandLineRunner init() {
		return (args) -> {
			storageService.init();
			archiveCategoryService.saveArchiveCategory(
				new ArchiveCategory(1L,"pol")
			);
			archiveCategoryService.saveArchiveCategory(
				new ArchiveCategory(2L,"math")
			);
		};
	}
}
