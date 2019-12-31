package com.psx.prime360ClientService.serviceI;
 
import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
 
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
 
import com.psx.prime360ClientService.utils.ResponseJson;
 
public interface NegativeEodFileUploadService {
 
    
    ResponseJson<HttpStatus, String> negativeEodFileUpload(MultipartFile file)throws Exception;
    public    InputStream downLoadingFile() throws SQLException;
}