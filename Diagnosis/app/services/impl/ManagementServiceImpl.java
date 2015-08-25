package services.impl;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.AttachedFile;
import models.Diagnosis;
import models.Employee;
import services.ManagementService;
import stores.DiagnosisStore;
import stores.EmployeeStore;
import util.FileUtil;
import util.SearchKey;

import com.avaje.ebean.PagedList;
import com.avaje.ebean.annotation.Transactional;

import exception.DiagnosisException;

@Service
@Transactional
public class ManagementServiceImpl implements ManagementService {
	//
	@Autowired
	private EmployeeStore employeeStore;
	
	@Autowired
	private DiagnosisStore diagnosisStore;
	
	@Override
	public PagedList<Employee> findEmployeeWithDiagnosis(String planId, int page, SearchKey searchKey) {
		//
		PagedList<Employee> employees = employeeStore.retrieve(planId, page, searchKey);
		
		if(employees != null && employees.getList() != null) {
			for (Employee employee : employees.getList()) {
				if(employee != null) {
					Diagnosis diagnosis = diagnosisStore.retrieveByCompanyId(planId, employee.getCompanyId());
					employee.setDiagnosis(diagnosis);
				}
			}
		}
		return employees;
	}

	@Override
	public int findCountSubmitted(String planId) {
		//
		int countSubmitted = 0;
		List<Employee> employees = employeeStore.retrieveAll();
    	for (Employee employee : employees) {
    		Diagnosis diagnosis = diagnosisStore.retrieveByCompanyId(planId, employee.getCompanyId());
    		if(diagnosis != null && diagnosis.getExcelFile() != null) {
    			countSubmitted++;
    		}
		}
    	
		return countSubmitted;
	}

	@Override
	public File findAllFiles(String planId) {
		//
		List<Diagnosis> diagnosises = diagnosisStore.retrieveByPlanId(planId);
    	List<AttachedFile> attachedFiles = new ArrayList<AttachedFile>();
    	for (Diagnosis diagnosis : diagnosises) {
    		attachedFiles.add(diagnosis.getExcelFile());
		}
    	try {
			return FileUtil.getZipFile(attachedFiles);
		} catch (IOException e) {
			throw new DiagnosisException("Failed to create zip file.");
		}
	}
}
