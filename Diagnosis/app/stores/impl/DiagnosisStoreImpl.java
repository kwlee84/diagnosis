package stores.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import models.Diagnosis;
import stores.DiagnosisStore;

import com.avaje.ebean.Model.Finder;
import com.avaje.ebean.annotation.Transactional;

@Repository
@Transactional
public class DiagnosisStoreImpl implements DiagnosisStore {
	//
	private static Finder<String, Diagnosis> finder = new Finder<String, Diagnosis>(Diagnosis.class);

	@Override
	public void deleteByCompanyId(String id) {
		//
		List<Diagnosis> diagnosises = finder.where().eq("companyId", id).findList();
		for (Diagnosis diagnosis : diagnosises) {
			diagnosis.delete();
		}
	}

	@Override
	public Diagnosis retrieveByCompanyId(String planId, String companyId) {
		//
		return finder.where().eq("planId", planId).eq("companyId", companyId).findUnique();
	}

	@Override
	public List<Diagnosis> retrieveByPlanId(String planId) {
		//
		return finder.where().eq("planId", planId).findList();
	}

	@Override
	public void deleteByPlanId(String planId) {
		//
		List<Diagnosis> diagnosises = finder.where().eq("planId", planId).findList();
		for (Diagnosis diagnosis : diagnosises) {
			diagnosis.delete();
		}
	}

	@Override
	public void create(Diagnosis diagnosis) {
		//
		diagnosis.insert();
	}

	@Override
	public void update(Diagnosis diagnosis) {
		//
		diagnosis.update();
	}
}
