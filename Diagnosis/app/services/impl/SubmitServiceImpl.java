package services.impl;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.AttachedFile;
import models.Diagnosis;
import services.SubmitService;
import stores.DiagnosisStore;

import com.avaje.ebean.annotation.Transactional;

@Service
@Transactional
public class SubmitServiceImpl implements SubmitService {
	//
	@Autowired
	private DiagnosisStore diagnosisStore;
	
	@Override
	public void submit(Diagnosis diagnosis, File file, String fileName, String contentType) {
		//
		diagnosis.setDateCreated(new Date());
		AttachedFile captureFile = new AttachedFile(file.getPath(), fileName, contentType);
		diagnosis.setExcelFile(captureFile);
		
		diagnosisStore.create(diagnosis);
	}
	@Override
	public void overwriteSubmit(Diagnosis diagnosis, File file, String fileName, String contentType) {
		//
		diagnosis.setDateUpdated(new Date());
		Diagnosis before = diagnosisStore.retrieveByCompanyId(diagnosis.getPlanId(), diagnosis.getCompanyId());
		diagnosis.setId(before.getId());
		AttachedFile captureFile = new AttachedFile(file.getPath(), fileName, contentType);
		diagnosis.setExcelFile(captureFile);
		
		diagnosisStore.update(diagnosis);
	}
}
