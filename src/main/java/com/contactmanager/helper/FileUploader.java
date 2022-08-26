package com.contactmanager.helper;

import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUploader {

	public FileUploader() {
	}
	
	public static boolean uploadFile(MultipartFile file,String path)
	{
		try {
			byte[] data = file.getBytes();
			FileOutputStream fos=new FileOutputStream(path);
			fos.write(data);
			fos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}

