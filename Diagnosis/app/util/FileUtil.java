package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import models.AttachedFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import exception.DiagnosisException;
import play.Play;

public class FileUtil {
	//
	public static final String rootPath = Play.application().path().getPath() + File.separatorChar + "..";
	private static final String uploadPath = Play.application().configuration().getString("fileUploadPath") + File.separatorChar;
	
	public static File saveFile(File fromFile) {
		//
		String path = uploadPath + DateFormatUtils.format(new Date(),"yyyyMMdd");
		File toFile = new File(path, String.valueOf(System.currentTimeMillis()));
		try {
			FileUtils.moveFile(fromFile, toFile);
		} catch (IOException e) {
			throw new DiagnosisException("Failed to save file.");
		}
		
		return toFile;
	}
	
	public static File getZipFile(List<AttachedFile> fileInfos) throws IOException {
		//
		String zipFileName = uploadPath + "submit.zip";
		
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFileName));
		
		byte[] buf = new byte[4096];
		
		for(AttachedFile fileInfo : fileInfos){
			if(fileInfo != null) {
				FileInputStream in = new FileInputStream(fileInfo.getPath());
				
				ZipEntry zip = new ZipEntry(fileInfo.getFileName());
				zipOut.putNextEntry(zip);
				
				int len = 0;
				while((len = in.read(buf)) > 0){
					zipOut.write(buf, 0, len);
				}
				
				zipOut.closeEntry();
				in.close();
			}
		}
		
		zipOut.close();
		
		return new File(zipFileName);
	}
	
	public static String getFileExtension(String fileName) {
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
	
}
