package services.impl;

import models.AttachedFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaje.ebean.annotation.Transactional;

import services.AttachedFileService;
import stores.AttachedFileStore;


@Service
@Transactional
public class AttachedFileServiceImpl implements AttachedFileService {
	//
	@Autowired
	private AttachedFileStore attachedFileStore;
	
	@Override
	public AttachedFile find(String id) {
		//
		return attachedFileStore.retrieve(id);
	}
	
}
