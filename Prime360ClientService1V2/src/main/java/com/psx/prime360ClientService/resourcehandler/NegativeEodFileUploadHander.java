package com.psx.prime360ClientService.resourcehandler;
 
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;
 
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
 
import com.psx.prime360ClientService.serviceI.NegativeEodFileUploadService;
import com.psx.prime360ClientService.utils.ResponseJson;
 
@RequestMapping("/requestPosting")
@CrossOrigin(maxAge = 3600)
@RestController
@PropertySource({ "classpath:path.properties" })
public class NegativeEodFileUploadHander {
 
//    private final Log logger = LogFactory.getLog(this.getClass());
    private static Logger logger = Logger.getLogger(NegativeEodFileUploadHander.class.getName());
    @Autowired
    private NegativeEodFileUploadService negativeEodService;
    @Autowired
    Environment environment;
 
    @PostMapping(value = "/uploadnegativeeodfile", consumes = { "multipart/form-data" })
    public ResponseEntity<?> primeMatch360RequestProcess(@RequestPart("file") MultipartFile file) {
 
        logger.info("Entered into primeMatch360Request");
        // String dataSource = "LEGAL";
 
        ResponseEntity<?> responseEntity = null;
 
        try {
 
            ResponseJson<HttpStatus, ?> responseJson = negativeEodService.negativeEodFileUpload(file);
            responseEntity = new ResponseEntity<>(responseJson, HttpStatus.OK);
 
        } catch (Exception ce) {
 
            ResponseJson<HttpStatus, Map<String, String>> responseJson1 = new ResponseJson<>();
 
          //  responseJson1.setMessage("unsuccessfull");
            responseJson1.setMessage("unsuccessfull");
            responseEntity = new ResponseEntity<>(responseJson1, HttpStatus.PRECONDITION_FAILED);
 
        }
        return responseEntity;
    }
 
    @GetMapping("/bajaj/BFL-NegativeAreaBase")
    public ResponseEntity<InputStreamResource> getPdf2() {
        logger.info("Downloading...");
 
        String destinationPath = environment.getProperty("negativefileuploaddestinationPath");
        InputStream input = null;
        /*
         * try { File folder = new File(destinationPath); File[] listOfFiles =
         * folder.listFiles();
         * 
         * for (int i = 0; i < listOfFiles.length; i++) { if (listOfFiles[i].isFile()) {
         * System.out.println("File " + listOfFiles[i].getName()); } else if
         * (listnegativeEodServiceOfFiles[i].isDirectory()) {
         * System.out.println("Directory " + listOfFiles[i].getName()); } } input = new
         * FileInputStream(destinationPath+"TO_Upload_negative_remove_dupliate.csv"); }
         * catch (FileNotFoundException e1) { // TODO Auto-generated catch block
         * e1.printStackTrace(); }
         */
        try {
            logger.info("Downloading File from Service....");
            input = negativeEodService.downLoadingFile();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            logger.error(e,e);
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/csv"))
                .body(new InputStreamResource(input));
 
        // return null;
 
    }
}