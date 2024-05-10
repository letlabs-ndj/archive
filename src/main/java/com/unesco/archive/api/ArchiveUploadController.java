package com.unesco.archive.api;

import com.unesco.archive.exceptions.StorageFileNotFoundException;
import com.unesco.archive.model.Archive;
import com.unesco.archive.model.ArchiveCategory;
import com.unesco.archive.model.ArchiveCategoryList;
import com.unesco.archive.model.enums.FileType;
import com.unesco.archive.service.ArchiveCategoryService;
import com.unesco.archive.service.ArchiveService;
import com.unesco.archive.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/archive")
public class ArchiveUploadController {

	private final StorageService storageService;
	private ArchiveService archiveService;
	private ArchiveCategoryService archiveCategoryService;

	@Autowired
	public ArchiveUploadController(StorageService storageService,
								   ArchiveService archiveService,
								   ArchiveCategoryService archiveCategoryService) {
		this.storageService = storageService;
		this.archiveService = archiveService;
		this.archiveCategoryService = archiveCategoryService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<Archive>> getAllArchives() {
		return new ResponseEntity<>(archiveService.getAllArchives(), HttpStatus.OK);
	}

	@GetMapping("/cat/{cat}")
	public ResponseEntity<List<Archive>> getArchiveByCategory(@PathVariable("cat") String cat) {
		return new ResponseEntity<>(archiveService.getArchiveByCategory(cat), HttpStatus.OK);
	}

	@GetMapping("/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);

		if (file == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/upload-file")
    public ResponseEntity<Archive> handleFileUploadWithJson(
            @RequestParam("file") MultipartFile file,
            @RequestParam("data") String jsonData) throws IOException {
			
				String url = MvcUriComponentsBuilder
				.fromMethodName(ArchiveUploadController.class, "serveFile", file.getOriginalFilename())
				.build().toString();

				ArchiveCategoryList categoryList = archiveCategoryService.mapToArchiveCategories(jsonData);
				
				Archive archive = new Archive();
				archive.setArchiveCategories(categoryList.getArchiveCategories());
				archive.setFilePath(url);
				archive.setContentType(file.getContentType());
				archive.setName(file.getOriginalFilename());

				storageService.store(file);
				return new ResponseEntity<>(archiveService.saveArchive(archive),HttpStatus.CREATED);
    }

	@DeleteMapping("/delete/{id}")
	public void deleteArchive(@PathVariable("id") Long id){
		archiveService.deleteArchive(id);
		Archive archive = archiveService.getArchiveById(id);
		storageService.deleteFile(archive.getName());
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}
