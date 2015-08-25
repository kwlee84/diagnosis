package stores.impl;



import org.springframework.stereotype.Repository;

import models.AttachedFile;
import stores.AttachedFileStore;

import com.avaje.ebean.Model.Finder;
import com.avaje.ebean.annotation.Transactional;

@Repository
@Transactional
public class AttachedFileStoreImpl implements AttachedFileStore {
	//
	private static Finder<String, AttachedFile> finder = new Finder<String, AttachedFile>(AttachedFile.class);

	@Override
	public AttachedFile retrieve(String id) {
		//
		return finder.byId(id);
	}

}
