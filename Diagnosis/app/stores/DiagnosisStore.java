package stores;

import java.util.List;

import models.Diagnosis;


public interface DiagnosisStore {
	//
	void create(Diagnosis diagnosis);
	Diagnosis retrieveByCompanyId(String planId, String companyId);
	List<Diagnosis> retrieveByPlanId(String planId);
	void deleteByCompanyId(String companyId);
	void deleteByPlanId(String planId);
	void update(Diagnosis diagnosis);
}
