package services;

import java.io.File;

import models.Diagnosis;

public interface SubmitService {
	//
	void submit(Diagnosis diagnosis, File file, String fileName, String contentType);
	void overwriteSubmit(Diagnosis diagnosis, File file, String fileName, String contentType);
	
}
