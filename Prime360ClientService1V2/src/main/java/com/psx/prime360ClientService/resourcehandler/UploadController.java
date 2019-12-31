package com.psx.prime360ClientService.resourcehandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.psx.prime360ClientService.entity.BaseMetaInfo;
import com.psx.prime360ClientService.entity.BulkPreviewWrapper;
import com.psx.prime360ClientService.entity.HeaderColumnWrapper;
import com.psx.prime360ClientService.entity.InputMetaInfo;
import com.psx.prime360ClientService.serviceImpl.StorageService;

/**
 * @author Rahul
 *
 */

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/Upload")
public class UploadController {

	@Autowired
	StorageService storageService;

	public static String targetFolder;
	List<String> files = new ArrayList<String>();

	@PostMapping("/post")
	public ResponseEntity<BulkPreviewWrapper> handleFileUpload(@RequestParam("file") MultipartFile file)
			throws IOException, URISyntaxException {
		// List<Requestmapping>
		System.out.println("UploadController handleFileUpload(-)");
		BulkPreviewWrapper list = storageService.store(file);
		files.add(file.getOriginalFilename());
		return new ResponseEntity<BulkPreviewWrapper>(list, HttpStatus.OK);
	}

	@PostMapping("/getDataBasedOnCsvString")
	public InputMetaInfo getDataBasedOnCsvString(@RequestBody BaseMetaInfo baseMetaInfo) {
		System.out.println("UploadController getDataBasedOnCsvString(-) " + baseMetaInfo);
		InputMetaInfo metaInfo = storageService.buildData(baseMetaInfo.getCsvString());
		return metaInfo;
	}

	@GetMapping("/getCsv/{file}")
	public ResponseEntity<HeaderColumnWrapper> getAll(@PathVariable("file") String file) throws IOException {
		System.out.println("UploadController getAll(-) " + file);
		HeaderColumnWrapper headerColumnWrapper = storageService.getData(file);
		System.out.println("UploadController getAll(-) HeaderColumnWrapper : " + headerColumnWrapper);
		return new ResponseEntity<HeaderColumnWrapper>(headerColumnWrapper, HttpStatus.OK);
	}

	@GetMapping("/getLastestRecord")
	public BaseMetaInfo getDataBasedOnSrcSystemName() {
		return storageService.getLastestRecord();
	}
}