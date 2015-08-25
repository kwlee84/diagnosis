package services;

import java.io.File;

import models.Employee;
import util.SearchKey;

import com.avaje.ebean.PagedList;

public interface ManagementService {
	//
	PagedList<Employee> findEmployeeWithDiagnosis(String planId, int page, SearchKey searchKey);
	int findCountSubmitted(String planId);
	File findAllFiles(String planId);
	
}
