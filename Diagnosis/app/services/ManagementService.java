package services;

import java.io.File;
import java.util.List;

import models.Employee;
import util.SearchKey;

import com.avaje.ebean.PagedList;

public interface ManagementService {
	//
	PagedList<Employee> findEmployeeWithDiagnosis(String planId, int page, SearchKey searchKey);
	int findCountSubmitted(String planId);
	File findAllFiles(String planId);
	List<Employee> findEmployeeWithDiagnosis(String planId, SearchKey searchKey);
	
}
