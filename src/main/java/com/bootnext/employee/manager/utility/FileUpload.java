package com.bootnext.employee.manager.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUpload {

	private final String CLASS_PATH_RESOURCE = new ClassPathResource("").getFile().getAbsolutePath();
	
	public FileUpload() throws IOException{
		
	}
	
	public boolean upload(MultipartFile file) {
		 boolean value = false;
		 try {
			 Files.copy(file.getInputStream(),Paths.get(CLASS_PATH_RESOURCE+File.separator+file.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
			 value = true;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 return value;
	}
}
