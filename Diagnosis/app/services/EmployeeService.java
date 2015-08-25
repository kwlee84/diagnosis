package services;

import java.io.File;
import java.util.List;

import models.Employee;
import util.SearchKey;

import com.avaje.ebean.PagedList;

public interface EmployeeService {
	//register find modify remove
	PagedList<Employee> findPaginated(int page, SearchKey searchKey);
	PagedList<Employee> findPaginated(String planId, int page, SearchKey searchKey);
	void registerByExcel(File file);
	void remove(String id);
	void removeAll();
	Employee findByCompanyId(String companyId, String planId);
	List<Employee> findAll();
	boolean isExist(String companyId);
	int findCountAll();
	void register(Employee employee);
}
