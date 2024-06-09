package com.unesco.archive.api;

import com.unesco.archive.exceptions.StorageFileNotFoundException;
import com.unesco.archive.model.Archive;
import com.unesco.archive.model.ArchiveCategory;
import com.unesco.archive.model.ArchiveCategoryList;
import com.unesco.archive.service.ArchiveCategoryService;
import com.unesco.archive.service.ArchiveService;
import com.unesco.archive.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@Controller
public class ArchiveUploadController {

	@Autowired
	private final StorageService storageService;

	@Autowired
	private ArchiveService archiveService;

	@Autowired
	private ArchiveCategoryService archiveCategoryService;

	public ArchiveUploadController(StorageService storageService,
								   ArchiveService archiveService,
								   ArchiveCategoryService archiveCategoryService) {
		this.storageService = storageService;
		this.archiveService = archiveService;
		this.archiveCategoryService = archiveCategoryService;
	}

	@GetMapping("/login")
	public String login(){
		return "login";
	}

	@GetMapping("/admin")
    public String getAllArchiveCategories(Model model) {
        model.addAttribute("categories", archiveCategoryService.getAllArchivesCategories());
        return "admin";
    }

    @PostMapping("/categories/add")
    public ResponseEntity<ArchiveCategory> saveArchiveCategory(@RequestBody ArchiveCategory archiveCategory) {
        return new ResponseEntity<>(archiveCategoryService.saveArchiveCategory(archiveCategory), HttpStatus.CREATED);
    }

	@GetMapping("/archive/all")
	public ResponseEntity<List<Archive>> getAllArchives() {
		return new ResponseEntity<>(archiveService.getAllArchives(), HttpStatus.OK);
	}

	@GetMapping("/archive/{cat}")
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

	@PostMapping("/archive/upload-file")
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

	@PutMapping("/archive/update/{id}")
	public ResponseEntity<Archive> updateArchive(
			@RequestBody Archive updatedArchive,
			@PathVariable("id") Long id){

		String oldFileName = archiveService.getArchiveById(id).getName();
		storageService.renameFile(oldFileName, updatedArchive.getName());

		String url = MvcUriComponentsBuilder
				.fromMethodName(ArchiveUploadController.class, "serveFile", updatedArchive.getName())
				.build().toString();

		System.out.println(url);
		updatedArchive.setFilePath(url);

		return new ResponseEntity<>(
				archiveService.updateArchive(updatedArchive),
				HttpStatus.ACCEPTED
		);
	}

	@DeleteMapping("/archive/delete/{id}")
	public ResponseEntity<?> deleteArchive(@PathVariable("id") Long id){
		storageService.deleteFile(archiveService.getArchiveById(id).getName());
		archiveService.deleteArchive(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
