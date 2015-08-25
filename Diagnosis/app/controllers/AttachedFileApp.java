package controllers;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import models.AttachedFile;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import services.AttachedFileService;

@Authenticated(Secured.class)
public class AttachedFileApp extends Controller {
	//
	@Inject
	private AttachedFileService attachedFileService;
	
	public Result download(String id) throws UnsupportedEncodingException {
    	//
		AttachedFile attachedFile = attachedFileService.find(id);
		response().setContentType(attachedFile.getContentType());  
		String fileName = new String(attachedFile.getFileName().getBytes("UTF-8"), "ISO-8859-1");
		//String fileName = new String(attachedFile.getFileName().getBytes("EUC-KR"), "ISO-8859-1");
		response().setHeader("Content-disposition","attachment; filename=" + fileName);
		
		return ok(new File(attachedFile.getPath()));
    }
	
}
